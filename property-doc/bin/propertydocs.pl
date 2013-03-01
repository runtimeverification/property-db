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
$imgpath = catfile(dirname($0), "..", catfile("resources",$imgsuffix));
$jspath = catfile(dirname($0), "..", catfile("resources",$jssuffix));
$out_root = "";

$tpackage="edu.uiuc.cs.fsl.propertydocs.taglets";

$upackage="edu.uiuc.cs.fsl.propertydocs.util";

#this is code that should be placed in the header of every file javadoc generates
#it is mostly setting up the properties link and putting in the javascript to 
#do highlighting

#this command line might be too long to run on windows...

$header ="\"<script type='text/javascript' src='{\@docRoot}/js/balloon.config.js'></script>";
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
$header.="      if(highlighting[i].getAttribute('class') == 'HLon'){";
$header.="        highlighting[i].setAttribute('class','HLoff');";
$header.="      }";
$header.="      else{";
$header.="        highlighting[i].setAttribute('class','HLon');";
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
$header.=" <TD BGCOLOR='#FFFFFF' CLASS='NavBarCell'><A HREF='{\@docRoot}__properties/property-list.html'>";
$header.="  <B>Properties</B></A></TD>";
$header.=" <TD BGCOLOR='#FFFFFF' NAME='highlighting' CLASS='HLon'><SPAN ONCLICK='toggleHighlights()'>";
$header.=" <U><B>Highlighting</B></U></FONT></SPAN></TD>\"";

$taglets ="-taglet $tpackage.CollectTaglet ";
$taglets.="-taglet $tpackage.DescriptionOpenTaglet -taglet $tpackage.DescriptionCloseTaglet ";
$taglets.="-taglet $tpackage.NewOpenTaglet    -taglet $tpackage.NewCloseTaglet ";
$taglets.="-taglet $tpackage.PropertyOpenTaglet      -taglet $tpackage.PropertyCloseTaglet ";

$docscmd = "java -Xmx1024m -cp $srcpath/../lib/classes.jar com.sun.tools.javadoc.Main -sourcepath . -header $header -tagletpath $srcpath $taglets";

$dflag = 0;
$pflag = 0;
$rootflag = 0;
$helpflag = 0;
$dir = ".";
$property_path = "properties";

foreach(@ARGV){
  #print "$dflag:$pflag:$rootflag";
  #print "\n";
  #print;
  #print "\n";
  #for some reason rootflag has to be first or this breaks
  #I blame it on perl being the stupidest, most buggy language ever
  #created
  if($rootflag == 1){
    $out_root = $_;
    $rootflag = 0;    
  }
  elsif($dflag == 1){
    $dir = $_;
    $dflag = 0;	
    $docscmd .= " $_";
  }
  elsif(/-d/){
    $dflag = 1;
    $docscmd .= " $_";
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
  elsif(/-outroot/){
    $rootflag = 1;
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
    $docscmd .= " $_";
  }
}

#print "$out_root $dir $property_path\n";

#sicne we can't give command line args to taglets we 
#send this info via environment variables... ugly hack
$ENV{__ANNOTATED_DOC_PROPERTY_PATH__} = $property_path;


$propertypagecmd = "java -cp $srcpath $upackage.FinishUp ".$dir;

$destjspath = catfile($dir, $jssuffix);
$destimgpath = catfile($dir, $imgsuffix);

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
#copy(catfile($jspath, "balloon.config.js"), catfile($destjspath, "balloon.config.js"));
 open(BALLOONCONFIG, "<".catfile($jspath, "balloon.config.js"));
 open(OUT, ">".catfile($destjspath, "balloon.config.js"));
 #this is a web url, must be forward slash on windows too
 #so no catfile
 $repl = '\''.$out_root."/images/GBubble/\'"; 
 #set the image path in the javascript for the popup... this should probably
 #be changed to a command line option because it currently only works for
 #html that is hosted locally (the root is wrong for actual webservers)
 for(<BALLOONCONFIG>){
   s/'images\/GBubble'/$repl/;
   print OUT;
 }  
 close(OUT);
 close(BALLOONCONFIG);
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
system $propertypagecmd;

sub print_help{
  print "the flags specific to properydocs are:\n";
  print "    -d: the output directory for the generated html\n";
  print "    -propertypath:  the root directory for MOP properties\n";
  print "    -outroot: the root directory for the generated html, necessary to make the pop-up baloons work correctly\n";
  print "\nThe following is help info from the base javadocs program, propertydocs passes options it does not know straight to javadoc:\n";  
}

#}

