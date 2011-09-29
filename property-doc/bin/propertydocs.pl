#!/usr/bin/perl -w

use File::Basename;
use File::Spec::Functions;
use File::Copy;
use Cwd;

$srcpath = catfile(dirname($0), "..", "build");
$imgsuffix = catfile("images","GBubble");
$jssuffix = "js";
$imgpath = catfile(dirname($0), "..", catfile("resources",$imgsuffix));
$jspath = catfile(dirname($0), "..", catfile("resources",$jssuffix));

$tpackage="edu.uiuc.cs.fsl.propertydocs.taglets";

$upackage="edu.uiuc.cs.fsl.propertydocs.util";


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
$header.="      if(divs[i].getAttribute('name') == 'formal'){";
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

$header.="      else if(divs[i].getAttribute('name') == 'brokenformal'){";
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
$header.="        if(divs[i].getAttribute('visibility') == 'visible'){";
$header.="          divs[i].setAttribute('visibility','hidden');";
$header.="        }";
$header.="        else{";
$header.="          divs[i].setAttribute('visibility','visibile');";
$header.="        }";
$header.="      }";

$header.="      else if(divs[i].getAttribute('name') == 'descriptive'){";
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

$taglets ="-taglet $tpackage.PropertyLinkTaglet    -taglet $tpackage.PropertyShortcutTaglet ";
$taglets.="-taglet $tpackage.CollectTaglet ";
$taglets.="-taglet $tpackage.DescriptiveOpenTaglet -taglet $tpackage.DescriptiveCloseTaglet ";
$taglets.="-taglet $tpackage.NewOpenTaglet    -taglet $tpackage.NewCloseTaglet ";
$taglets.="-taglet $tpackage.FormalOpenTaglet      -taglet $tpackage.FormalCloseTaglet ";
$taglets.="-taglet $tpackage.MopPropertyTaglet ";

$docscmd = "javadoc -header $header -tagletpath $srcpath $taglets";

$flag = 0;
$dir = ".";
foreach(@ARGV){
  if($flag == 1){
    $dir = $_;
    $flag = 0;	
  }
  if(/-d/){
    $flag = 1;
  }
  $docscmd .= " $_";
}

$propertypagecmd = "java -cp $srcpath $upackage.FinishUp ".$dir;

$destjspath = catfile($dir, $jssuffix);
$destimgpath = catfile($dir, $imgsuffix);

$error = system $docscmd;
#$error = 0;
if($error == 0){
  print "...finishing up"."\n";
  mkdir $destjspath;
  #copy(catfile($jspath, "balloon.config.js"), catfile($destjspath, "balloon.config.js"));
   open(BALLOONCONFIG, "<".catfile($jspath, "balloon.config.js"));
   open(OUT, ">".catfile($destjspath, "balloon.config.js"));
   #this is a web url, must be forward slash on windows too
   #so no catfile
   $repl = '\''.getcwd."/$destimgpath/\'"; 
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
}

