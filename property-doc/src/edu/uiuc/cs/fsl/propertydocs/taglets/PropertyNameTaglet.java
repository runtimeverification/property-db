package edu.uiuc.cs.fsl.propertydocs.taglets;

import com.sun.tools.doclets.standard.Standard;

import com.sun.tools.doclets.Taglet;

import com.sun.javadoc.Tag;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.FileReader;
import java.io.BufferedReader;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.TimeZone;

import edu.uiuc.cs.fsl.propertydocs.util.Util;
import edu.uiuc.cs.fsl.propertydocs.util.GenerateUrls;

/**
* This Taglet allows for generating MOP property documentation
*
* @author Patrick Meredith
*
*/

public class PropertyNameTaglet implements Taglet {

    private static final String NAME = "property.name";

    private static final String dir = Standard.htmlDoclet.configuration().destDirName;


    static {
      File propertyDir = new File(dir + File.separator + "__properties");
      if(!propertyDir.exists()) propertyDir.mkdir();
      File htmlDir = new File(dir + File.separator + "__properties" + File.separator + "html");
      if(!htmlDir.exists()) htmlDir.mkdir();
      File mopDir = new File(dir + File.separator + "__properties" + File.separator + "mop");
      if(!mopDir.exists()) mopDir.mkdir();
    }

    private static String getDate(){
      Calendar calendar = new GregorianCalendar();
      String timezone = TimeZone.getDefault().getDisplayName();
      String day = (new String[] {"Sun", "Mon", "Tue", "Wed", "Thurs", "Fri", "Sat"}) 
           [calendar.get(Calendar.DAY_OF_WEEK)];
      String month = (new String[] {"Jan", "Feb", "March", 
                                    "April", "May", "Jun", 
                                    "Jul", "Aug", "Sept",
                                    "Oct", "Nov", "Dec"})
           [calendar.get(Calendar.MONTH)];
      int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
      int hour = calendar.get(Calendar.HOUR);
      int minute = calendar.get(Calendar.MINUTE);
      int second = calendar.get(Calendar.SECOND);
      int year = calendar.get(Calendar.YEAR);
      return day + " " + month + " " + dayOfMonth + " " 
             + hour + ":" + minute + ":" + second + " "
             + timezone + " " + year;
    }

    public String getName()        { return NAME; }

    /** can be used in a field comment*/
    public boolean inField()       { return true; }
    /**can be used in a constructor comment*/
    public boolean inConstructor() { return true; }
    /**can be used in a method comment*/
    public boolean inMethod()      { return true; }
    /**can be used in overview comment*/
    public boolean inOverview()    { return true; }
    /**can be used in package comment*/
    public boolean inPackage()     { return true; }
    /**can be used in type comment (classes or interfaces)*/
    public boolean inType()        { return true; }
    
    /**this is NOT an inline tag*/ 
    public boolean isInlineTag()   { return true;}
    
    /**
     * Register this Taglet.
     * @param tagletMap  the map to register this tag to.
     */
    public static void register(Map tagletMap) {
       PropertyNameTaglet tag = new PropertyNameTaglet();
       Taglet t = (Taglet) tagletMap.get(tag.getName());
       if (t != null) {
           tagletMap.remove(tag.getName());
       }
       tagletMap.put(tag.getName(), tag);
    }

     /**
     * Given the <code>Tag</code> representation of this custom
     * tag, return its string representation.
     * @param tag he <code>Tag</code> representation of this custom tag.
     */
    public String toString(Tag tag) {
      String[] args = tag.text().trim().split("\\s+");
      String pathifiedName =  args[1].replaceAll("[.]","/");
      try {
        //copy specified mop file
        File in = new File(args[0] + File.separator + pathifiedName + ".mop");
        String outName = dir + "__properties" + File.separator 
            + "mop" +  File.separator + pathifiedName + ".mop";
        populate(outName);
        File out = new File(outName);
        FileReader fr = new FileReader(in);
        FileOutputStream fos = new FileOutputStream(out);
        BufferedReader br = new BufferedReader(fr);
        PrintStream ps = new PrintStream(fos);
        String s;
        while (true) {
          s = br.readLine();
          if(s == null) break;
          ps.println(s);
        } 
        br.close(); 
        ps.close();

        //create the HTML file that will link to the given mop file
        String htmlOutName = dir + File.separator + "__properties" + File.separator 
            + "html" +  File.separator + pathifiedName + ".html";
        populate(htmlOutName);
        File htmlOut = new File(htmlOutName);
        FileOutputStream htmlfos = new FileOutputStream(htmlOut);
        PrintStream htmlps = new PrintStream(htmlfos);
        htmlps.print(getHtmlHeader(pathifiedName));
        htmlps.print("<P><IFRAME SRC='" + buildRelativeUrlFromName(pathifiedName)  
            + "/mop/" + pathifiedName + ".mop' WIDTH='100%' HEIGHT='300' /></P>");
        htmlps.print(getHtmlFooter(pathifiedName));
        htmlps.close();
      }
      catch (Exception e){
        throw new RuntimeException(e);
      }
      return "";
    }

    //Why the hell doesn't the standard library do this if you try to create
    //a long path with non-existent intervening directories already?
    static void populate(String path){
      String[] dirs = path.split(File.separator.equals("/")?"[/]":"\\\\"); 
      String currPath = dirs[0];
      for(int i = 1; i < dirs.length; ++i) {
        File f = new File(currPath);
        if(!f.exists()) f.mkdir();  
        if(!dirs[i].equals("")) currPath += File.separator + dirs[i];
      }
    }

    static String buildRelativeUrlFromName(String path){
      String[] dirs = path.split(File.separator.equals("/")?"[/]":"\\\\"); 
      String ret = "..";
      for(int i = 0; i < dirs.length - 1; ++i) {
        ret += "/..";
      }
      return ret;
    }
    
    /**
     * This method should not be called since arrays of inline tags do not
     * exist.  Method {@link #tostring(Tag)} should be used to convert this
     * inline tag to a string.
     * @param tags the array of <code>Tag</code>s representing of this custom tag.
     */
    public String toString(Tag[] tags) {
      StringBuilder ret = new StringBuilder();
      for(Tag tag : tags) {
        ret.append(toString(tag));
      } 
      return ret.toString();
    }


    private static String getHtmlHeader(String name) {
     return 
      "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\n"
    + "<!--NewPage-->\n"
    + "<HTML>\n"
    + "<HEAD>\n"
    + "<!-- Generated by docsp on " + Util.getDate() + " -->\n"
    + "<TITLE>\n"
    + "Test\n"
    + "</TITLE>\n"
    + "\n"
    + "<META NAME=\"date\" CONTENT=\"2011-07-13\">\n"
    + "\n"
    + "<LINK REL =\"stylesheet\" TYPE=\"text/css\" HREF=\"stylesheet.css\" TITLE=\"Style\">\n"
    + "\n"
    + "<SCRIPT type=\"text/javascript\">\n"
    + "function windowTitle()\n"
    + "{\n"
    + "    if (location.href.indexOf('is-external=true') == -1) {\n"
    + "        parent.document.title=\"Test\";\n"
    + "    }\n"
    + "}\n"
    + "</SCRIPT>\n"
    + "<NOSCRIPT>\n"
    + "</NOSCRIPT>\n"
    + "\n"
    + "</HEAD>\n"
    + "\n"
    + "<BODY BGCOLOR=\"white\" onload=\"windowTitle();\">\n"
    + "<HR>\n"
    + "\n"
    + "\n"
    + "<!-- ========= START OF TOP NAVBAR ======= -->\n"
    + "<A NAME=\"navbar_top\"><!-- --></A>\n"
    + "<A HREF=\"#skip-navbar_top\" title=\"Skip navigation links\"></A>\n"
    + "<TABLE BORDER=\"0\" WNAMETH=\"100%\" CELLPADDING=\"1\" CELLSPACING=\"0\" SUMMARY=\"\">\n"
    + "<TR>\n"
    + "<TD COLSPAN=2 BGCOLOR=\"#EEEEFF\" CLASS=\"NavBarCell1\">\n"
    + "<A NAME=\"navbar_top_firstrow\"><!-- --></A>\n"
    + "<TABLE BORDER=\"0\" CELLPADDING=\"0\" CELLSPACING=\"3\" SUMMARY=\"\">\n"
    + "  <TR ALIGN=\"center\" VALIGN=\"top\">\n"
    + "  <TD BGCOLOR=\"#EEEEFF\" CLASS=\"NavBarCell1\">    <A HREF=\"package-summary.html\"><FONT CLASS=\"NavBarFont1\"><B>Package</B></FONT></A>&nbsp;</TD>\n"
    + "  <TD BGCOLOR=\"#EEEEFF\" CLASS=\"NavBarCell1Rev\"> &nbsp;<FONT CLASS=\"NavBarFont1Rev\"><B>Class</B></FONT>&nbsp;</TD>\n"
    + "  <TD BGCOLOR=\"#EEEEFF\" CLASS=\"NavBarCell1\">    <A HREF=\"package-tree.html\"><FONT CLASS=\"NavBarFont1\"><B>Tree</B></FONT></A>&nbsp;</TD>\n"
    + "  <TD BGCOLOR=\"#EEEEFF\" CLASS=\"NavBarCell1\">    <A HREF=\"deprecated-list.html\"><FONT CLASS=\"NavBarFont1\"><B>Deprecated</B></FONT></A>&nbsp;</TD>\n"
    + "  <TD BGCOLOR=\"#EEEEFF\" CLASS=\"NavBarCell1\">    <A HREF=\"index-all.html\"><FONT CLASS=\"NavBarFont1\"><B>Index</B></FONT></A>&nbsp;</TD>\n"
    + "  <TD BGCOLOR=\"#EEEEFF\" CLASS=\"NavBarCell1\">    <A HREF=\"help-doc.html\"><FONT CLASS=\"NavBarFont1\"><B>Help</B></FONT></A>&nbsp;</TD>\n"
    + "  </TR>\n"
    + "</TABLE>\n"
    + "</TD>\n"
    + "<TD ALIGN=\"right\" VALIGN=\"top\" ROWSPAN=3><EM>\n"
    + "<TD BGCOLOR=#FFFFFF CLASS=\"NavBarCell\">  <A HREF='" + buildRelativeUrlFromName(name) + "/property-list.html"
    + "'><FONT CLASS=NavBarFont1><B>Properties</B></FONT></EM>\n"
    + "</TR>\n"
    + "\n"
//    + "<TD BGCOLOR=\"white\" CLASS=\"NavBarCell2\"><FONT SIZE=\"-2\">\n"
//    + "  <A HREF=\"index.html?Test.html\" target=\"_top\"><B>FRAMES</B></A>  &nbsp;\n"
//    + "&nbsp;<A HREF=\"Test.html\" target=\"_top\"><B>NO FRAMES</B></A>  &nbsp;\n"
    + "&nbsp;<SCRIPT type=\"text/javascript\">\n"
    + "  <!--\n"
    + "  if(window==top) {\n"
    + "    document.writeln('<A HREF=\"allclasses-noframe.html\"><B>All Classes</B></A>');\n"
    + "  }\n"
    + "  //-->\n"
    + "</SCRIPT>\n"
    + "<NOSCRIPT>\n"
    + "  <A HREF=\"allclasses-noframe.html\"><B>All Classes</B></A>\n"
    + "</NOSCRIPT>\n"
    + "\n"
    + "\n"
    + "</FONT></TD>\n"
    + "</TR>\n"
    + "</TABLE>\n"
    + "<A NAME=\"skip-navbar_top\"></A>\n"
    + "<!-- ========= END OF TOP NAVBAR ========= -->\n"
    + "<HR>\n";
  }


  private static String getHtmlFooter(String name){  
    return
      "<!-- ======= START OF BOTTOM NAVBAR ====== -->\n"
    + "<A NAME=\"navbar_bottom\"><!-- --></A>\n"
    + "<A HREF=\"#skip-navbar_bottom\" title=\"Skip navigation links\"></A>\n"
    + "<TABLE BORDER=\"0\" WNAMETH=\"100%\" CELLPADDING=\"1\" CELLSPACING=\"0\" SUMMARY=\"\">\n"
    + "<TR>\n"
    + "<TD COLSPAN=2 BGCOLOR=\"#EEEEFF\" CLASS=\"NavBarCell1\">\n"
    + "<A NAME=\"navbar_bottom_firstrow\"><!-- --></A>\n"
    + "<TABLE BORDER=\"0\" CELLPADDING=\"0\" CELLSPACING=\"3\" SUMMARY=\"\">\n"
    + "  <TR ALIGN=\"center\" VALIGN=\"top\">\n"
    + "  <TD BGCOLOR=\"#EEEEFF\" CLASS=\"NavBarCell1\">    <A HREF=\"package-summary.html\"><FONT CLASS=\"NavBarFont1\"><B>Package</B></FONT></A>&nbsp;</TD>\n"
    + "  <TD BGCOLOR=\"#EEEEFF\" CLASS=\"NavBarCell1Rev\"> &nbsp;<FONT CLASS=\"NavBarFont1Rev\"><B>Class</B></FONT>&nbsp;</TD>\n"
    + "  <TD BGCOLOR=\"#EEEEFF\" CLASS=\"NavBarCell1\">    <A HREF=\"package-tree.html\"><FONT CLASS=\"NavBarFont1\"><B>Tree</B></FONT></A>&nbsp;</TD>\n"
    + "  <TD BGCOLOR=\"#EEEEFF\" CLASS=\"NavBarCell1\">    <A HREF=\"deprecated-list.html\"><FONT CLASS=\"NavBarFont1\"><B>Deprecated</B></FONT></A>&nbsp;</TD>\n"
    + "  <TD BGCOLOR=\"#EEEEFF\" CLASS=\"NavBarCell1\">    <A HREF=\"index-all.html\"><FONT CLASS=\"NavBarFont1\"><B>Index</B></FONT></A>&nbsp;</TD>\n"
    + "  <TD BGCOLOR=\"#EEEEFF\" CLASS=\"NavBarCell1\">    <A HREF=\"help-doc.html\"><FONT CLASS=\"NavBarFont1\"><B>Help</B></FONT></A>&nbsp;</TD>\n"
    + "  </TR>\n"
    + "</TABLE>\n"
    + "</TD>\n"
    + "<TD ALIGN=\"right\" VALIGN=\"top\" ROWSPAN=3><EM>\n"
    + "<TD BGCOLOR=#FFFFFF CLASS=\"NavBarCell\">  <A HREF='" + buildRelativeUrlFromName(name) + "/property-list.html" +
    "'><FONT CLASS=NavBarFont1><B>Properties</B></FONT></EM>\n"
    + "</TD>\n"
    + "</TR>\n"
    + "&nbsp;<SCRIPT type=\"text/javascript\">\n"
    + "  <!--\n"
    + "  if(window==top) {\n"
    + "    document.writeln('<A HREF=\"allclasses-noframe.html\"><B>All Classes</B></A>');\n"
    + "  }\n"
    + "  //-->\n"
    + "</SCRIPT>\n"
    + "<NOSCRIPT>\n"
    + "  <A HREF=\"allclasses-noframe.html\"><B>All Classes</B></A>\n"
    + "</NOSCRIPT>\n"
    + "\n"
    + "\n"
    + "</FONT></TD>\n"
    + "</TR>\n"
    + "</TABLE>\n"
    + "<A NAME=\"skip-navbar_bottom\"></A>\n"
    + "<!-- ======== END OF BOTTOM NAVBAR ======= -->\n"
    + "\n"
    + "<HR>\n"
    + "\n"
    + "</BODY>\n"
    + "</HTML>\n";
  }
}

