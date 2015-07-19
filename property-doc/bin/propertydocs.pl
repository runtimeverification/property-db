#!/usr/bin/perl -w

use File::Basename;
use File::Spec::Functions;
use File::Copy;
use Cwd;

$srcpath = catfile(dirname($0), "..", "build");
#these are the images for the popup balloons
$imgsuffix = catfile("images","GBubble");
#here we set up the path for the javascript we use to 
#produce the popup balloons
$jssuffix = "js";
$logosuffix = "images";
$imgpath = catfile(dirname($0), "..", catfile("resources",$imgsuffix));
$jspath = catfile(dirname($0), "..", catfile("resources",$jssuffix));
$stylepath = catfile(dirname($0), "..", catfile("resources","stylesheet.css"));
$logopath = catfile(dirname($0), "..", catfile("resources",$logosuffix));

$tpackage="edu.uiuc.cs.fsl.propertydocs.taglets";

$upackage="edu.uiuc.cs.fsl.propertydocs.util";

#this is code that should be placed in the header of every file javadoc generates
#it is mostly setting up the properties link and putting in the javascript to 
#do highlighting

#this command line might be too long to run on windows...

$header ="\" <script type='text/javascript'>";
$header.="   var docRoot = '{\@docRoot}';";
$header.=" </script>";
$header.="<script type='text/javascript' src='{\@docRoot}/js/balloon.config.js'></script>";
$header.=" <script type='text/javascript' src='{\@docRoot}/js/balloon.js'></script>";
$header.=" <script type='text/javascript' src='{\@docRoot}/js/box.js'></script>";
$header.=" <script type='text/javascript' src='{\@docRoot}/js/yahoo-dom-event.js'></script>";
$header.=" <script type='text/javascript'>";
$header.="   var balloon    = new Balloon;";
$header.="   var tooltip  = new Balloon;";
$header.="   BalloonConfig(tooltip,'GPlain');";
$header.="   var fader = new Balloon;";
$header.="   BalloonConfig(fader,'GFade');";
$header.="   var box         = new Box;";
$header.="   BalloonConfig(box,'GBox');";
$header.="   var fadeBox     = new Box;";
$header.="   BalloonConfig(fadeBox,'GBox');";
$header.="   fadeBox.bgColor     = 'black';";
$header.="   fadeBox.fontColor   = 'white';";
$header.="   fadeBox.borderStyle = 'none';";
$header.="   fadeBox.delayTime   = 200;";
$header.="   fadeBox.allowFade   = true;";
$header.="   fadeBox.fadeIn      = 750;";
$header.="   fadeBox.fadeOut     = 200;";
$header.=" </script>";

$header.="<script type='text/javascript'>";
$header.="function toggleHighlights()";
$header.="  {";
$header.="  var highlighting=document.getElementsByName('highlighting');";
$header.="    for(var i = 0; i < highlighting.length; i++){";
$header.="      if(highlighting[i].getAttribute('class') == ''){";
$header.="        highlighting[i].setAttribute('class','navBarCell1RevNR');";
$header.="      }";
$header.="      else{";
$header.="        highlighting[i].setAttribute('class','');";
$header.="      }";
$header.="    }";

$header.="  var divs=document.getElementsByTagName('div');";
$header.="    for(var i = 0; i < divs.length; i++){";
$header.="      if(divs[i].getAttribute('name') == 'property'){";
$header.="        if(divs[i].getAttribute('class') == ''){";
$header.="          divs[i].setAttribute('class','NavBarCell1Rev');";
$header.="          divs[i].setAttribute('onmouseover', divs[i].getAttribute('bak'));";
$header.="        }";
$header.="        else{";
$header.="          divs[i].setAttribute('class','');";
$header.="          divs[i].setAttribute('bak', divs[i].getAttribute('onmouseover'));";
$header.="          divs[i].setAttribute('onmouseover','');";
$header.="        }";
$header.="      }";
$header.="      else if(divs[i].getAttribute('name') == 'brokenproperty'){";
$header.="        if(divs[i].getAttribute('class') == ''){";
$header.="          divs[i].setAttribute('class','Red');";
$header.="          divs[i].setAttribute('onmouseover', divs[i].getAttribute('bak'));";
$header.="        }";
$header.="        else{";
$header.="          divs[i].setAttribute('class','');";
$header.="          divs[i].setAttribute('bak', divs[i].getAttribute('onmouseover'));";
$header.="          divs[i].setAttribute('onmouseover','');";
$header.="        }";
$header.="      }";

$header.="      else if(divs[i].getAttribute('name') == 'new'){";
$header.="        if(divs[i].style.visibility != 'hidden'){";
$header.="          divs[i].style.visibility = 'hidden';";
$header.="        }";
$header.="        else{";
$header.="          divs[i].style.visibility = 'visible';";
$header.="        }";
$header.="      }";

$header.="      else if(divs[i].getAttribute('name') == 'description'){";
$header.="        if(divs[i].getAttribute('class') == ''){";
$header.="          divs[i].setAttribute('class','TableHeadingColor');";
$header.="          divs[i].setAttribute('onmouseover', divs[i].getAttribute('bak'));";
$header.="        }";
$header.="        else{";
$header.="          divs[i].setAttribute('class','');";
$header.="          divs[i].setAttribute('bak', divs[i].getAttribute('onmouseover'));";
$header.="          divs[i].setAttribute('onmouseover','');";
$header.="        }";
$header.="      }";

$header.="    }";
$header.="  }";
$header.="</script>";
$header.="<DIV class=\"navBarRV\">";
$header.=" <A TARGET=\"_top\" href=\"https://runtimeverification.com\">";
$header.=" <IMG style=\"vertical-align:middle\" height=\"30\" width=\"30\" SRC=\"{\@docRoot}images/favicon.ico\">";
$header.=" <TD> &nbsp;&nbsp; </TD>";
$header.=" <TD BGCOLOR='#FFFFFF' ><A HREF='{\@docRoot}__properties/property-list.html'>";
$header.=" PROPERTIES</A></TD>";
$header.=" <TD> &nbsp;&nbsp; </TD>";
$header.=" <TD BGCOLOR='#FFFFFF' >";
$header.=" STATISTICS</A></TD>";
$header.=" <TD> &nbsp;&nbsp; </TD>";
$header.=" <TD BGCOLOR='#FFFFFF' ><SPAN NAME='highlighting' style=\"cursor:pointer\" ONCLICK='toggleHighlights()' CLASS=\"navBarCell1RevNR\">";
$header.=" HIGHLIGHTING</FONT></SPAN></TD></DIV>\"";


$taglets ="-taglet $tpackage.CollectTaglet ";
$taglets.="-taglet $tpackage.DescriptionOpenTaglet -taglet $tpackage.DescriptionCloseTaglet ";
$taglets.="-taglet $tpackage.NewOpenTaglet    -taglet $tpackage.NewCloseTaglet ";
$taglets.="-taglet $tpackage.PropertyOpenTaglet      -taglet $tpackage.PropertyCloseTaglet ";

$docscmdPrefix = "java -Xmx1024m ";
# $docscmdSuffix = " -cp $srcpath/../lib/tools.jar:$srcpath/../lib/classes.jar com.sun.tools.javadoc.Main -sourcepath . -header $header -tagletpath $srcpath $taglets";
$docscmdSuffix = " -cp $srcpath/../lib/tools.jar:$srcpath/../lib/classes.jar com.sun.tools.javadoc.Main -sourcepath . -header $header -tagletpath $srcpath $taglets";

$dflag = 0;
$pflag = 0;
$helpflag = 0;
$dir = ".";
$property_path = "properties";

foreach(@ARGV){
  #print "$dflag:$pflag";
  #print "\n";
  #print;
  #print "\n";
  if($dflag == 1){
    $dir = $_;
    $dflag = 0;	
    $docscmdSuffix .= " $_";
  }
  elsif(/-d/){
    $dflag = 1;
    $docscmdSuffix .= " $_";
  }
  #the next two must not be passed to javadoc
  #so we do not concat them to $docscmd
  elsif($pflag == 1){
    $property_path = $_;
    $pflag = 0;    
  }
  elsif(/-propertypath/){
    $pflag = 1;
  }
  #anything else isn't special and should just be passed
  #to javadoc without alteration
  elsif(/--help/){
    &print_help;
    $helpflag = 1;
  }
  elsif(/-h/){
    &print_help;
    $helpflag = 1;
  }
  else {
    $docscmdSuffix .= " $_";
  }
}

#print "$dir $property_path\n";

#sicne we can't give command line args to taglets we 
#send this info via environment variables... ugly hack
# this is a really really ugly hack!



# USER HAS TO ENTER PROP PACK
$ENV{__ANNOTATED_DOC_PROPERTY_PATH__} = $property_path;

$docscmdPrefix .= " -Doutputpath='$dir' -Dpropertypath='$property_path' ";

$propertypagecmd = $docscmdPrefix . " -cp $srcpath $upackage.FinishUp ".$dir;

$destjspath = catfile($dir, $jssuffix);
$destimgpath = catfile($dir, $imgsuffix);
$destlogopath = catfile($dir, $logosuffix);
$deststylepath = catfile($dir, "stylesheet.css");

$docscmd = $docscmdPrefix .$docscmdSuffix;

system $docscmd;
#here we move the javascript and images to the generated dir
#and call the FinishUp class.  The FinishUp class will modify
#the css with the styles we need, and it will generate 
#the property-list.html file

#if we are printing help, do not call finish up
if($helpflag == 1){
  exit(0);
}
print "...finishing up"."\n";
mkdir $destjspath;
 copy(catfile($jspath, "balloon.config.js"), catfile($destjspath, "balloon.config.js"));
 copy(catfile($jspath, "balloon.js"), catfile($destjspath, "balloon.js"));
 copy(catfile($jspath, "box.js"), catfile($destjspath, "box.js"));
 copy(catfile($jspath, "yahoo-dom-event.js"), catfile($destjspath, "yahoo-dom-event.js"));
mkdir catfile($dir, "images");
mkdir $destimgpath;
 copy(catfile($imgpath, "balloon.png"), catfile($destimgpath, "balloon.png"));
 copy(catfile($imgpath, "balloon_ie.png"), catfile($destimgpath, "balloon_ie.png"));
 copy(catfile($imgpath, "close.png"), catfile($destimgpath, "close.png"));
 copy(catfile($imgpath, "down_left.png"), catfile($destimgpath, "down_left.png"));
 copy(catfile($imgpath, "down_right.png"), catfile($destimgpath, "down_right.png"));
 copy(catfile($imgpath, "up_left.png"), catfile($destimgpath, "up_left.png"));
 copy(catfile($imgpath, "up_right.png"), catfile($destimgpath, "up_right.png"));
 copy(catfile($logopath, "favicon.ico"), catfile($destlogopath, "favicon.ico"));
system $propertypagecmd;

# copy over proper stylesheet from resources directory
copy($stylepath, $deststylepath);

# insert RuntimeVerification header into javadoc-generated index.html
my $filename = catfile($dir, "index.html");
my $index_file;

open(my $fh, '<', $filename) or die "cannot open file $filename";
{
    local $/;
    $index_file = <$fh>;
}
close($fh);

# location in index.html to insert new header reference; quotemeta changes a string into a searchable pattern, qq escapes the string
# remove_if_exists ensures framset is not pasted twice if script is run several times with same $OUTPATH
my $remove_if_exists = quotemeta(qq(<frameset rows="105,100%" title="Header" framespacing=0 frameborder="no" border=0>
# <frame src="\/php\/header_no_dropdowns.php" name="headerFrame" title="Runtime Verification Header" scrolling="no">));

my $search_for = quotemeta(qq(<frameset cols="20%,80%" title="Documentation frame" onload="top.loadFrames()">));

# new header reference --> might paste twice
my $replace_with = qq(<frameset rows="105,100%" title="Header" framespacing=0 frameborder="no" border=0>
<frame src="/php/header_no_dropdowns.php" name="headerFrame" title="Runtime Verification Header" scrolling="no">
<frameset cols="20%,80%" title="Documentation frame" onload="top.loadFrames()">);

# get replacement text and write out to index.html
# $index_file =~ s/$remove_if_exists//;
$index_file =~ s/$search_for/$replace_with/;
open(my $fh_out, '>', $filename) or die "cannot open file $filename";
{
    local $/;
    print $fh_out $index_file;
}
close($fh_out);

# edit overview-frame.html to remove "properties, statistics, highlighting" header (added because of -header $header flag in javadoc command)
# overview-frame.html remains the same despite other frames changing as you navigate the website
my $overview_frame = catfile($dir, "overview-frame.html");
my $replace_overview_text;

open(my $fh_frame, '<', $overview_frame) or die "cannot open file $overview_frame";
{
    local $/;
    $replace_overview_text = <$fh_frame>;
}
close($fh_frame);

# find header tag and replace
my $remove_header = qr/<h1(.*?)<\/h1>/;
my $java8_header = qq(<h1 title="Java&#8482 Platform Platform Standard Ed. 8" class="bar"><strong>Java&#8482 Platform<br>Standard Ed. 8</strong></h1>);
$replace_overview_text =~ s/$remove_header/$java8_header/;

open(my $fh_frame_out, '>', $overview_frame) or die "cannot open file $overview_frame";
{
    local $/;
    print $fh_frame_out $replace_overview_text;
}
close($fh_frame_out);

sub print_help{
  print "the flags specific to properydocs are:\n";
  print "    -propertypath:  the root directory for RVM properties\n";
  print "\nThe following is help info from the base javadocs program, propertydocs passes options it does not know straight to javadoc:\n";  
}

#}

