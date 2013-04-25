package edu.uiuc.cs.fsl.propertydocs.util;

import com.sun.javadoc.Tag;

import java.io.*;
import java.util.HashSet;

public class CreatePropertyFile {

  private static final String dir = System.getProperty( "outputpath" );

  private static HashSet<String> seenPositions
    = new HashSet<String> (); //this is to ensure that we don't link back
                           //to the same location multiple times

  private static HashSet<String> seenNames
    = new HashSet<String>(); //since we modify property files in place, we need this to
                             //ensure that we delete the files on a fresh run

  public static final String PROPDIR = "propertypath";
	private static final String propDir = System.getProperty(PROPDIR);

	static {
    File propertyDir = new File(dir + "/" + "__properties");
    if(!propertyDir.exists()) propertyDir.mkdir();
    File htmlDir = new File(dir + "/" + "__properties" + "/" + "html");
    if(!htmlDir.exists()) htmlDir.mkdir();
    File rvmDir = new File(dir + "/" + "__properties" + "/" + "rvm");
    if(!rvmDir.exists()) rvmDir.mkdir();
  }

	public static void forceInit() { /* call this method to force this class to be initialized */ }

  //Name is the name of the property
  //tag is the PropertyOpen Tag referencing the property
  //depth is the depth of the Property File, e.g., the depth of java.io.UnsafeIterator.rvm
  //  is 4 (3 + 1, + 1 because of __properties dir)
  public static void createOrModifyPropertyFile(String name, PositionWrapper p, Tag tag, int depth){
      if(seenPositions.contains(p.toString() + name)) return;
      seenPositions.add(p.toString() + name);
      String pathifiedName =  name.replaceAll("[.]",("/".equals("/"))?"/":"\\");
      StringBuilder relativeUrlPrefix = new StringBuilder();
      for(int i = 0; i < depth; ++i){
        relativeUrlPrefix.append("../");
      }
      String linkBack =  relativeUrlPrefix.append(GenerateUrls.getUrl(tag)).toString();
      String nameBack = tag.holder().toString().replaceAll("<","&lt;").replaceAll(">","&gt;");
      String htmlOutName = dir + "/" + "__properties" + "/" 
            + "html" +  "/" + pathifiedName + ".html";
      try {
        //copy specified rvm file
		  final String inName = propDir + File.separator + pathifiedName + "" +
				  ".rvm";
		  File in = new File(inName);
        File htmlOut = new File(htmlOutName);
        //create the HTML file that will link to the given rvm file
        populate(htmlOutName);
        String outName = dir + File.separator + "__properties" + File.separator
            + "rvm" +  File.separator + pathifiedName + ".rvm";
//		System.out.println("inName: " + inName);
//		System.out.println("outName: " + outName);
        populate(outName);
        File out = new File(outName);
        if(!seenNames.contains(name)){
          out.delete();
          htmlOut.delete();
          seenNames.add(name);
        }
        if(out.exists()) {
          modifyPropertyFile(htmlOutName, linkBack, nameBack);
          return;
        }
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

        FileOutputStream htmlfos = new FileOutputStream(htmlOut);
        PrintStream htmlps = new PrintStream(htmlfos);
        htmlps.print(getHtmlHeader(pathifiedName));
        htmlps.print("<P><IFRAME SRC='" + buildRelativeUrlFromName(pathifiedName)  
            + "/rvm/" + pathifiedName + ".rvm' WIDTH='100%' HEIGHT='600'></IFRAME></P>\n");
        htmlps.print("<P>This property is referenced in the following locations:</P>\n");
        htmlps.print("<UL>\n<LI><A HREF='" + linkBack +"'>" + nameBack + "</A></LI></UL>\n");
        htmlps.print(getHtmlFooter(pathifiedName));
        htmlps.close();
      }
      catch (Exception e){
        throw new RuntimeException(e);
      }
  }

  //This is for adding a link on an already existing HTML file.  Because I don't want to
  //deal with collecting a list and generating the HTML property file at the end... because
  //such would require using a finalizer or some such thanks to how JavaDoc works (sigh),
  //I, instead, modify the HTML files as each new link is found.  This is sort of ugly,
  //but it's better than messing with finalizers that may or may not get called
  private static void modifyPropertyFile(String htmlOutName, String linkBack, String nameBack) 
    throws java.io.FileNotFoundException, java.io.IOException{
    FileReader htmlReader = new FileReader(htmlOutName);
    StringBuilder htmlText = new StringBuilder();
    int b;
    while((b = htmlReader.read()) != -1){
      htmlText.append((char) b);
    } 
    String[] parts = htmlText.toString().split("</UL>"); 
    FileOutputStream htmlfos = new FileOutputStream(htmlOutName);
    PrintStream htmlps = new PrintStream(htmlfos);
    htmlps.print(parts[0]);
    htmlps.print(linkBackItem(linkBack, nameBack));
    htmlps.print("</UL>"); 
    htmlps.print(parts[1]);
  }

  //Simple helper method, see usage points in this file
  private static String linkBackItem(String linkBack, String nameBack){
    return "<LI><A HREF='" + linkBack + "'>" + nameBack + "</A></LI>";
  }

  //This method generates the necessary directory structure for a deeply
  //nested file, like java/util/concurrent/Foo.java
  //
  //Why the hell doesn't the standard library do this if you try to create
  //a long path with non-existent intervening directories already?
  public static void populate(String path){
    String[] dirs = path.split("[/]"); 
    String currPath = dirs[0];
    for(int i = 1; i < dirs.length; ++i) {
      File f = new File(currPath);
      if(!f.exists()) f.mkdir();  
      if(!dirs[i].equals("")) currPath += "/" + dirs[i];
    }
  }

  //perhaps put this in GenerateUrls?  Lazy
  public static String buildRelativeUrlFromName(String path){
    String[] dirs = path.split("[/]"); 
    String ret = "..";
    for(int i = 0; i < dirs.length - 1; ++i) {
      ret += "/..";
    }
    return ret;
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
    + "<LINK REL =\"stylesheet\" TYPE=\"text/css\" HREF=\"" +
			  buildRelativeUrlFromName(name)  + "/../" +
			  "stylesheet.css\" TITLE=\"Style\">\n"
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
