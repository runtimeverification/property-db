package edu.uiuc.cs.fsl.propertydocs.util;

import java.io.File;
import java.io.FilenameFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.PrintStream;

import java.util.Map;
import java.util.HashMap;

public class FinishUp {

   // This is the scale factor for scaling all percentages.  E.g., 1e2f will scale to 2 decimal places
   // 1e3f would scale to 3, etc.
   private final static float FACTOR = 1e2f;
   private static String propertiesDir;

   //TODO? should probably really be defined in Util or something instead of in multiple places
   private static final String GLOBAL = "<global>";
   private static final String ALLATTRIBUTES = " <all> ";

   private static Map<String, Integer> undecidedDB;
   private static Map<String, Integer> descriptionDB; 
   private static Map<String, Integer> newDB;
   private static PropertyMap propertyDB; 

   public static void main(String[] args){
     propertiesDir = args[0] + File.separator + "__properties";
     File propertiesList   = new File(propertiesDir + File.separator + "property-list.html");

     File undecidedStats   = new File(propertiesDir + File.separator + "undecided.stats"); 
     File descriptionStats = new File(propertiesDir + File.separator + "description.stats"); 
     File newStats    = new File(propertiesDir + File.separator + "new.stats"); 
     File propertyStats      = new File(propertiesDir + File.separator + "property.stats"); 

     try {
       (new PrintStream(new FileOutputStream(new File(args[0] + File.separator + "stylesheet.css"), true)))
         .println( ".Red { background-color:#EE0000; color:#FFFFFF }" 
                 + "\n.HLon  { color:#0000E2 }"
                 + "\n.HLoff { color:#450082 }"
                 + "\np { color:inherit;background-color:inherit }");
     } catch (java.io.IOException e){
        throw new RuntimeException(e);
     }

     undecidedDB = getDB(undecidedStats);
     descriptionDB = getDB(descriptionStats);
     newDB = getDB(newStats);  
     propertyDB = getAttributedDB(propertyStats);

     //System.out.println("undecided: " + undecidedDB);
     //System.out.println("description: " + descriptionDB);
     //System.out.println("new: " + newDB);
     //System.out.println("property: " + propertyDB);

     StringBuilder table 
       = new StringBuilder();
     table.append("<H2> MOP Coverage Statistics and Property Links</H2><HR />");

     makeTableForPackage("Global", GLOBAL, table);
     //this assumes that {@collect.stats} is seen in every package that uses our tags!
     for(String packageName : undecidedDB.keySet()){
       if(packageName.equals(GLOBAL)) continue;
       makeTableForPackage(packageName, table);
     }

     generatePropertiesList(table, propertiesDir + File.separator + "html");

     try {
       FileOutputStream fos = new FileOutputStream(propertiesList);
       PrintStream ps = new PrintStream(fos);
       ps.println(HTMLHEADER);
       ps.println(table);
       ps.println(HTMLFOOTER); 
     } catch (java.io.IOException e){
       throw new RuntimeException("Cannot create properties-list.html?");
     }
   }

   @SuppressWarnings("unchecked")
   private static Map<String, Integer> getDB(File file){
     Map<String, Integer> ret = null;
     try {
       FileInputStream fis = new FileInputStream(file);
       ObjectInputStream ois = new ObjectInputStream(fis);
       try {
         ret = (Map<String, Integer>)ois.readObject();
       } catch (ClassNotFoundException cnfe) {
         throw new RuntimeException(cnfe);
       }
       return ret;
     } catch (java.io.IOException e){
       //if there is an IOException we assume that the file isn't there which means the tag wasn't seen
       return new DefaultMap<String, Integer>(0);
     }
   }

   @SuppressWarnings("unchecked")
   private static PropertyMap getAttributedDB(File file){
     PropertyMap ret = null;
     try {
       FileInputStream fis = new FileInputStream(file);
       ObjectInputStream ois = new ObjectInputStream(fis);
       try {
         ret = (PropertyMap)ois.readObject();
       } catch (ClassNotFoundException cnfe) {
         throw new RuntimeException(cnfe);
       }
       return ret;
     } catch (java.io.IOException e){
       //if there is an IOException we assume that the file isn't there which means the tag wasn't seen
       return new PropertyMap(0);
     }
   }

   private static void makeTableForPackage(String name, StringBuilder table){
     makeTableForPackage(name, name, table);
   }

   private static void makeTableForPackage(String prettyName, String packageName, StringBuilder table){
     table.append("<TABLE BORDER=\"1\" WIDTH=\"100%\" CELLPADDING=\"3\" CELLSPACING=\"0\" SUMMARY=\"\">");
     table.append("<TR BGCOLOR=\"#CCCCFF\" CLASS=\"TableHeadingColor\">");
     table.append("<TH ALIGN=\"left\" COLSPAN=\"4\"><FONT SIZE=\"+2\">");
     table.append("<B>" + prettyName + " MOP Coverage Statistics </B></FONT></TH></TR>");

     int undecidedW   = undecidedDB.get(packageName);
     int descriptionW = descriptionDB.get(packageName);
     int newW         = newDB.get(packageName);
     int propertyW    = propertyDB.get(packageName).get(ALLATTRIBUTES); 
     int totalW       = undecidedW + descriptionW + newW + propertyW; 

     table.append(formatStat("Undecided Text",  
                              undecidedW  ,         totalW));

     table.append(formatStat("Description Text",
                              descriptionW,         totalW)); 

     table.append(formatStat("New Text",   
                              newW        ,         totalW)); 

     table.append(formatAttributedStat("Property Text",
                              propertyW,            totalW,      "Property Text",
                              propertyDB.get(packageName)));
     
     table.append("</TABLE><BR /><BR />");
   }

   private static StringBuilder formatStat(String name, int w, float totalW){
     return formatStat(name, w, totalW, "", "Total Text");
   }

   private static StringBuilder formatStat(String name, int w, float totalW, String padding, String total){
     StringBuilder ret = new StringBuilder();
     ret.append("<TR BGCOLOR=\"white\" CLASS=\"TableRowColor\">");
     ret.append("<TD WIDTH=\"15%\">");
     ret.append(name);
     ret.append("</TD>");
     ret.append("<TD WIDTH=\"15%\">" + padding + "Words: ");
     ret.append(w);
     ret.append("</TD><TD WIDTH=\"15%\">" + padding + "Percent of " + total + ": ");
     ret.append(scale(w, totalW));
     ret.append("%</TD>");
     ret.append("</TR>");
     return ret;
  }

   private static StringBuilder formatAttributedStat(String name, int w, float totalW, 
       String total, Map<String, Integer> attributeMap){
     StringBuilder ret = new StringBuilder();
     ret.append(formatStat(name, w, totalW));
     for(String key : attributeMap.keySet()){
       if(key.equals(ALLATTRIBUTES)) continue;
       ret.append(formatStat("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" 
             + key, attributeMap.get(key), w, "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;", total)); 
     }
     return ret;
  }

  private static float scale(int num, float den){
    return Math.round(num / den * 100f * FACTOR) / FACTOR;
  }

   private static String chop(String name){
     StringBuilder ret = new StringBuilder();
     String[] parts = name.split("[.]");
     for(int i = 0; i < parts.length - 1; ++i){
       ret.append(parts[i]);
     }
     return ret.toString();
   }

   private static void generatePropertiesList(StringBuilder table, final String dir){
     System.out.println("Generating " + propertiesDir + File.separator + "property-list.html..."); 
     generatePropertiesList(table, dir, "");
   }

   private static void generatePropertiesList(StringBuilder table, final String dir, String prefix){
    // System.out.println("------------" + dir + "-------------" + prefix);
     String linkPrefix = prefix.replaceAll("[.]", "/");
     File dirFile = new File(dir);
     //find all html files at this level
     boolean tableHeadingAdded = false;
     for(String fn : 
          dirFile.list(new FilenameFilter() {
                                      public boolean accept(File dir, String name){
                                         return name.endsWith(".html") && !(name.equals("property-list.html"));
                                      } 
                                    })
         ){
         if(!tableHeadingAdded){
           table.append("<TABLE BORDER=\"1\" WIDTH=\"100%\" CELLPADDING=\"3\" CELLSPACING=\"0\" SUMMARY=\"\">");
           table.append("<TR BGCOLOR=\"#CCCCFF\" CLASS=\"TableHeadingColor\">");
           table.append("<TH ALIGN=\"left\" COLSPAN=\"1\"><FONT SIZE=\"+2\">");
           table.append("<B>MOP Property Links for the " + ((prefix.equals(""))? "&lt;Unnamed&gt;":prefix) 
                                                         + " Package </B></FONT></TH></TR>");
           tableHeadingAdded = true;
         }
         table.append("<TR BGCOLOR=\"white\" CLASS=\"TableRowColor\"><TD WIDTH=\"15%\">");
         table.append("<B><A HREF='");
         String link = "html/" + ((linkPrefix.equals(""))? "" : (linkPrefix + "/")) + fn; 
       //  System.out.println("link: " + link);
         table.append(link);
         table.append("'>");
         table.append(chop(fn)); 
         table.append("</A></B></TD></TR>"); 
         table.append("<BR /><BR />");
     }
      //if we added a heading, add a footing 
     if(tableHeadingAdded) table.append("</TABLE>");
     
     //recurse to subdirectories
     for(String fn :
         dirFile.list(new FilenameFilter() {
                                     public boolean accept(File dir, String name) {
                                        String test = dir + File.separator + name;
                                        return (new File(test)).isDirectory();
                                     }
                                   })
         ){
           String subDir = dir + File.separator + fn;
           generatePropertiesList(table, subDir, (prefix.equals(""))? fn : (prefix + "." + fn)); 
         }
   }

 //////////////////////
 // HEADER
 //
 //////////////////////

   private static final String HTMLHEADER = 
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
 + "<TABLE BORDER=\"0\" WIDTH=\"100%\" CELLPADDING=\"1\" CELLSPACING=\"0\" SUMMARY=\"\">\n"
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
 + "<TD BGCOLOR=#FFFFFF CLASS=\"NavBarCell1Rev\">  <A HREF='property-list.html'><FONT CLASS=NavBarFont1Rev><B>Properties</B></FONT></EM>\n"
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

 //////////////////////
 // FOOTER
 //
 //////////////////////
 private static final String HTMLFOOTER = 
   "<!-- ======= START OF BOTTOM NAVBAR ====== -->\n"
 + "<A NAME=\"navbar_bottom\"><!-- --></A>\n"
 + "<A HREF=\"#skip-navbar_bottom\" title=\"Skip navigation links\"></A>\n"
 + "<TABLE BORDER=\"0\" WIDTH=\"100%\" CELLPADDING=\"1\" CELLSPACING=\"0\" SUMMARY=\"\">\n"
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
 + "<TD BGCOLOR=#FFFFFF CLASS=\"NavBarCell1Rev\">  <A HREF='property-list.html'><FONT CLASS=NavBarFont1Rev><B>Properties</B></FONT></EM>\n"
 + "</TD>\n"
 + "</TR>\n"
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
 + "<A NAME=\"skip-navbar_bottom\"></A>\n"
 + "<!-- ======== END OF BOTTOM NAVBAR ======= -->\n"
 + "\n"
 + "<HR>\n"
 + "\n"
 + "</BODY>\n"
 + "</HTML>\n";
}
