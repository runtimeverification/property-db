package edu.uiuc.cs.fsl.propertydocs.util;

import java.io.*;
import java.util.Map;

public class FinishUp {
    // This is the scale factor for scaling all percentages.  E.g., 1e2f will scale to 2 decimal places
    // 1e3f would scale to 3, etc.
    private final static float FACTOR = 1e2f;
    private static String propertiesDir;
	public static String destDirName = System.getProperty( "outputpath" );
    
    //TODO? should probably really be defined in Util or something instead of in multiple places
    private static final String GLOBAL = "<global>";
    private static final String ALLATTRIBUTES = " <all> ";
    private static String lastColor="altColor";
    private static int  counter=1;
    
    
    private static Map<String, Integer> undecidedDB;
    private static Map<String, Integer> descriptionDB;
    private static Map<String, Integer> newDB;
    private static PropertyMap propertyDB;
    
    public static void main(String[] args){
        destDirName = args[0];
        propertiesDir = destDirName + File.separator + "__properties";
        File propertiesList   = new File(propertiesDir + File.separator + "property-list.html");
        File propertiesStats   = new File(propertiesDir + File.separator + "Statistics.html");
        
        File undecidedStats   = new File(propertiesDir + File.separator + "undecided.stats");
        File descriptionStats = new File(propertiesDir + File.separator + "description.stats");
        File newStats    = new File(propertiesDir + File.separator + "new.stats");
        File propertyStats      = new File(propertiesDir + File.separator + "property.stats");
        
        
        undecidedDB = getDB(undecidedStats);
        descriptionDB = getDB(descriptionStats);
        newDB = getDB(newStats);
        propertyDB = getAttributedDB(propertyStats);
        
        //System.out.println("undecided: " + undecidedDB);
        //System.out.println("description: " + descriptionDB);
        //System.out.println("new: " + newDB);
        //System.out.println("property: " + propertyDB);
        
        StringBuilder table = new StringBuilder();
        table.append("<H2> Coverage Statistics </H2><HR />");
        
        lastColor="altColor";
        makeTableForPackage("Global", GLOBAL, table);
        //this assumes that {@collect.stats} is seen in every package that uses our tags!
        for(String packageName : undecidedDB.keySet()){
            if(packageName.equals(GLOBAL)) continue;
            lastColor="altColor";
            makeTableForPackage(packageName, table);
        }
        
        //generatePropertiesList(table, propertiesDir + File.separator + "html");
        System.out.println("Generating " + propertiesDir + File.separator + "Statistics.html...");
        try {
            FileOutputStream fos = new FileOutputStream(propertiesStats);
            PrintStream ps = new PrintStream(fos);
            ps.println(HTMLHEADER2);
            ps.println("<DIV CLASS=\"contentContainer\">");
            ps.println(table);
            ps.println("</DIV>");
            ps.println(HTMLFOOTER2);
        } catch (java.io.IOException e){
            throw new RuntimeException("Cannot create Statistics.html?");
        }
        
        StringBuilder table2 = new StringBuilder();
        table2.append("<H2> All Properties </H2><HR />");
        table2.append("<P>Below are all the available Java API properties, grouped by package.</P>");
        table2.append("<P><B>Click on a property to see it, or select one or more properties to download ");
        table2.append("or to generate an RV-Monitor-based Java agent.</B></P><HR />");
        table2.append("<INPUT TYPE=\"button\" style=\"font-weight:bold; font-size:15px;width:250px; height:80px;\" onclick=\"checkedAllForms();\" value=\"Select/Unselect all properties\">");
        table2.append("&nbsp;&nbsp;&nbsp;");
        table2.append("<INPUT TYPE=\"button\" style=\"font-weight:bold; font-size:15px;width:250px; height:80px;\" onclick=javascript:sender() value=\"Generate Agent\">");
        table2.append("&nbsp;&nbsp;&nbsp;");
        table2.append("<INPUT TYPE=\"button\" style=\"font-weight:bold; font-size:15px;width:250px; height:80px;\" onclick=javascript:zipproperties() value=\"Download Properties\">");
        generatePropertiesList(table2, propertiesDir + File.separator + "html");
        
        try {
            FileOutputStream fos = new FileOutputStream(propertiesList);
            PrintStream ps = new PrintStream(fos);
            ps.println(HTMLHEADER);
            ps.println("<DIV CLASS=\"contentContainer\">");
            ps.println(table2);
            ps.println("<INPUT TYPE=\"button\" style=\"font-weight:bold; font-size:15px;width:250px; height:80px;\" onclick=\"checkedAllForms();\" value=\"Select/Unselect all properties\">");
            ps.println("&nbsp;&nbsp;&nbsp;");
            ps.println("<INPUT TYPE=\"button\" style=\"font-weight:bold; font-size:15px;width:250px; height:80px;\" onclick=javascript:sender() value=\"Generate Agent\">");
            ps.println("&nbsp;&nbsp;&nbsp;");
            ps.println("<INPUT TYPE=\"button\" style=\"font-weight:bold; font-size:15px;width:250px; height:80px;\" onclick=javascript:zipproperties() value=\"Download Properties\">");
            ps.println("</DIV>");
            ps.println(HTMLFOOTER);
        } catch (java.io.IOException e){
            throw new RuntimeException("Cannot create property-list.html?");
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
        if(prettyName.equals("Global"))
            table.append("<TABLE ID=\"globalstat\" CLASS=\"overviewSummary\" BORDER=\"0\" CELLPADDING=\"3\" CELLSPACING=\"0\" SUMMARY=\"\">");
        else
            table.append("<TABLE CLASS=\"overviewSummary\" BORDER=\"0\" CELLPADDING=\"3\" CELLSPACING=\"0\" SUMMARY=\"\">");
        table.append("<CAPTION><SPAN>"+prettyName +" Coverage Statistics"+"</SPAN><SPAN CLASS=\"tabEnd\">&nbsp;</SPAN></CAPTION>\n<TBODY>");
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
                     
        table.append("</TBODY></TABLE><BR /><BR />");
    }
    
    private static StringBuilder formatStat(String name, int w, float totalW){
        return formatStat(name, w, totalW, "", "Total Text");
    }
    
    private static StringBuilder formatStat(String name, int w, float totalW, String padding, String total){
        StringBuilder ret = new StringBuilder();
        ret.append("<TR CLASS=\""+lastColor+"\">");
        ret.append("<TD WIDTH=\"15%\" CLASS=\"colFirst\">"+name+"</TD>\n");
        ret.append("<TD WIDTH=\"15%\" CLASS=\"colFirst\">"+padding+"Words: "+ w +"</TD>\n");
        ret.append("<TD WIDTH=\"15%\" CLASS=\"colFirst\">"+padding+"Percent of "+ total + ":" + scale(w, totalW) +"</TD>\n");
        ret.append("</TR>\n");
                                         
        switchColor();
                                         
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
         System.out.println("------------" + dir + "-------------" + prefix);
        String linkPrefix = prefix.replaceAll("[.]", "/");
        File dirFile = new File(dir);
        //find all html files at this level
        boolean tableHeadingAdded = false;
        for(String fn :
            dirFile.list(new FilenameFilter() {
            public boolean accept(File dir, String name){
                return name.endsWith(".html") && !(name.equals("property-list.html")) &&!(name.equals("Statistics.html"));
            }
        })
            ){
            if(!tableHeadingAdded){
                table.append("<FORM ACTION=\"\" METHOD=\"post\" NAME=\"form"+counter+
                             "\" ID=\"form"+(counter++)+"\">\n");//F
                table.append("<TABLE CLASS=\"overviewSummary\" BORDER=\"0\" CELLPADDING=\"3\" CELLSPACING=\"0\" SUMMARY=\"\">");
                table.append("<CAPTION><SPAN> Package " + ((prefix.equals(""))? "&lt;Unnamed&gt;":prefix)
                             +"</SPAN><SPAN CLASS=\"tabEnd\">&nbsp;</SPAN></CAPTION>\n<TBODY>");
                

                tableHeadingAdded = true;
                lastColor="altColor";
                
                table.append("<TR CLASS=\"selectionColor\">");//F
                //table.append("<TD WIDTH=\"15%\" CLASS=\"colFirst\">");//F
                table.append("<TD CLASS=\"colFirst\">");//F
                table.append("<INPUT TYPE=\"checkbox\" NAME=\"property\" VALUE=\""+((prefix.equals(""))? "&lt;Unnamed&gt;":prefix)+
                             "\" ID=\""+((prefix.equals(""))? "&lt;Unnamed&gt;":prefix)+
                             "\" checked ONCLICK=\"checkedAll.call(this);\"/><B>&nbsp;Select/Unselect all</B>");//F
                table.append("</TD>");//F
                //table.append("<TD WIDTH=\"15%\" CLASS=\"colSecond\"> </TD>\n");
                //table.append("<TD WIDTH=\"15%\" CLASS=\"colLast\"> </TD>\n");
                //table.append("<TD  CLASS=\"colLast\"> </TD>\n");
                table.append("</TR>");//F
            }
            table.append("<TR CLASS=\""+lastColor+"\">");
            //table.append("<TD WIDTH=\"15%\" CLASS=\"colFirst\">");
            table.append("<TD CLASS=\"colFirst\">");
            String link = "html/" + ((linkPrefix.equals(""))? "" : (linkPrefix + "/")) + fn;
            table.append("<INPUT TYPE=\"CHECKBOX\" NAME=\"property\" VALUE=\""+chop(fn)+"\"checked>");
            table.append("<A HREF='"+link+"'>"+chop(fn)+"</A></TD></TR>\n");
//            table.append("<B>&nbsp;&nbsp;&nbsp;");
  //          table.append("<TD  CLASS=\"colLast\"><A HREF='"+link+"'>"+chop(fn)+"</A></TD></TR>\n");
            //table.append("<TD WIDTH=\"15%\" CLASS=\"colLast\"><A HREF='"+link+"'>"+chop(fn)+"</A></TD></TR>\n");
            //table.append(chop(fn));
    //        table.append("</B></TD>\n");
            //table.append("<TD WIDTH=\"15%\" CLASS=\"colSecond\"> </TD>\n");
            //table.append("<TD WIDTH=\"15%\" CLASS=\"colLast\"><A HREF='"+link+"'>View</A></TD></TR>\n");
            switchColor();
        }
        //if we added a heading, add a footing
        if(tableHeadingAdded) {
            table.append("</TBODY>");
            table.append("</TABLE>");
            table.append("</FORM>");//F
            table.append("<BR /><BR />\n");
        }
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
    
    private static void switchColor(){
        if(lastColor.equals("altColor"))
            lastColor="rowColor";
        else
            lastColor="altColor";
    }
    
    private static StringBuilder generateZipped(){
        //TODO
        return new StringBuilder();
    }
    
    private static StringBuilder generateFormsList(){
        StringBuilder ret = new StringBuilder();
        int count=0;
        File dirFile = new File(destDirName + File.separator + "java");

        System.out.println(dirFile);
        // if (dirFile.isDirectory()) {
        for(String fn :
            dirFile.list(new FilenameFilter() {
                public boolean accept(File f, String name){
                    return dirFile.isDirectory();
            }
        })){
            count++;

        //     for (String fn : dirFile.list()) { count++;
        //     }
        }
        for(int i=1;i<=count;i++){//////////////TO CHANGE
            ret.append(" document.forms[\"form");
            ret.append(i);
            ret.append("\"].submit();\n");
        }
        return ret;
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
    + "<TITLE>Properties</TITLE>\n"
    + "\n"
    + "<META NAME=\"date\" CONTENT=\"2014-12-10\">\n"
    + "\n"
    + "<LINK REL =\"stylesheet\" TYPE=\"text/css\" HREF=\"../stylesheet.css\" TITLE=\"Style\">\n"
    + "</HEAD>\n"
    + "\n"
    + "<BODY>\n"
    + "<SCRIPT type=\"text/javascript\">\n"
    + "var checkflag=\"true\";\n"
    + "function windowTitle()\n"
    + "{\n"
    + "    if (location.href.indexOf('is-external=true') == -1) {\n"
    + "        parent.document.title=\"Properties\";\n"
    + "    }\n"
    + "}\n"
    + "function checkedAll()\n"
    + "{\n"
    + " // this refers to the clicked checkbox \n"
    + " // find all checkboxes inside the checkbox' form \n"
    + " var elements = this.form.getElementsByTagName('input');\n"
    + " // iterate and change status \n"
    + " for (var i = elements.length; i--; ) {\n"
    + "  if (elements[i].type == 'checkbox') {\n"
    + "   elements[i].checked = this.checked;\n"
    + "  }\n"
    + " }\n"
    + "}\n"
    + "function checkedAllForms()\n"
    + "{\n"
    + " var arrMarkMail = document.getElementsByName(\"property\");\n"
    + " if(checkflag==\"false\"){\n"
    + "  for (var i = 0; i < arrMarkMail.length; i++)\n"
    + "   arrMarkMail[i].checked = true;\n"
    + "  checkflag=\"true\";\n"
    + " }else{\n"
    + "  for (var i = 0; i < arrMarkMail.length; i++)\n"
    + "   arrMarkMail[i].checked = false;\n"
    + "  checkflag=\"false\";\n"
    + " }\n"
    + "}\n"
    + "function zipproperties()\n"//FR
    + "{\n"//FR
    + generateZipped()
    + " return true;\n"//FR
    + "}\n"//FR
    + "function sender()\n"//FR
    + "{\n"//FR
    + generateFormsList()
    + " return true;\n"//FR
    + "}\n"//FR
    + "</SCRIPT>\n"
    + "<NOSCRIPT>\n"
    + "<DIV>Javascript is disabled on your browser.</DIV>\n"
    + "</NOSCRIPT>\n"
    + "\n"
    + "<!-- ========= START OF TOP NAVBAR ======= -->\n"
    + "<DIV CLASS=\"topNav\"><A NAME=\"navbar_top\">\n<!— --></A>"
    + " <A HREF=\"#skip-navbar_top\" title=\"Skip navigation links\"></A>"
    + " <A NAME=\"navbar_top_firstrow\">\n<!— -—>\n</A>\n"
    + " <UL CLASS=\"navList\" TITLE=\"Navigation\">\n"
    + "  <LI> <A HREF=\"../overview-summary.html\">Overview</A></LI>\n"
    + "  <LI> Package </LI>\n"
    + "  <LI> Class </LI>\n"
    + "  <LI> Tree </LI>\n"
    + "  <LI> Deprecated </LI>\n"
    + "  <LI> <A HREF=\"../index-all.html\">Index</A> </LI>\n"
    + "  <LI> <A HREF=\"../help-doc.html\">Help</A> </LI>\n"
    + " </UL>\n"

    + " <DIV CLASS=\"aboutLanguage\">"
    + "  <DIV CLASS=\"navBarRV\">"
    + "   <A TARGET=\"_top\" href=\"https://runtimeverification.com\">"
    + "   <IMG SRC=\"../images/favicon.ico\" style=\"vertical-align:middle\" height=\"30\" width=\"30\" >"
    + "   <TD> &nbsp;&nbsp; </TD>"
    + "   <TD BGCOLOR='#FFFFFF'><SPAN CLASS=\"navBarCell1RevNR\">PROPERTIES</SPAN></TD>"
    + "   <TD> &nbsp;&nbsp; </TD>"
    + "   <TD BGCOLOR='#FFFFFF'> <A HREF=\"Statistics.html\">STATISTICS</A></TD>\n"
    + "   <TD> &nbsp;&nbsp; </TD>"
    + "   <TD BGCOLOR='#FFFFFF'> HIGHLIGHTING </TD>"
    + "  </DIV>\n"
    + " </DIV>\n"
    + "</DIV>\n"
    + "<DIV CLASS=\"subNav\">\n"
    + " <UL CLASS=\"navList\">\n"
    + "  <LI><A HREF=\"../index.html?__properties/property-list.html\" target=\"_top\">FRAMES</A> </LI>\n"
    + "  <LI><A HREF=\"property-list.html\" target=\"_top\">NO FRAMES</A>  </LI>\n"
    + " </UL>\n"
    + " <UL CLASS=\"navList\" ID=\"allclasses_navbar_top\" style=\"display: none;\">\n"
    + "  <LI><A HREF=\"../allclasses-noframe.html\">All Classes</A></LI>\n"
    + " </UL>\n"
    + " <DIV>\n"
    + "  <SCRIPT type=\"text/javascript\">\n"
    + "  <!--\n"
    + "  if(window==top) {\n"
    + "    document.writeln('<A HREF=\"../allclasses-noframe.html\"><B>All Classes</B></A>');\n"
    + "  }\n"
    + "  -->\n"
    + "  </SCRIPT>\n"
    + " </DIV>\n"
    + " <A NAME=\"skip-navbar_top\"></A>\n"
    + "</DIV>\n"
    + "<!-- ========= END OF TOP NAVBAR ========= -->\n";

    
    //////////////////////
    // FOOTER
    //
    //////////////////////
 private static final String HTMLFOOTER =
    "<!-- ======= START OF BOTTOM NAVBAR ====== -->\n"
    + "<DIV CLASS=\"bottomNav\"><A NAME=\"navbar_bottom\">\n<!-- -->\n</A>"
    + " <A HREF=\"#skip-navbar_bottom\" title=\"Skip navigation links\"></A>"
    + " <A NAME=\"navbar_bottom_firstrow\">\n"
    + "  <!-- -->\n"
    + " </A>"
    + " <UL CLASS=\"navList\" TITLE=\"Navigation\">\n"
    
    + "  <LI> <A HREF=\"../overview-summary.html\">Overview</A></LI>\n"
    + "  <LI> Package </LI>\n"
    + "  <LI> Class </LI>\n"
    + "  <LI> Tree </LI>\n"
    + "  <LI> Deprecated </LI>\n"
    + "  <LI> <A HREF=\"../index-all.html\">Index</A> </LI>\n"
    + "  <LI> <A HREF=\"../help-doc.html\">Help</A> </LI>\n"
    + " </UL>\n"
    + " <DIV CLASS=\"aboutLanguage\">"
    + "  <DIV CLASS=\"navBarRV\">"
    + "   <A TARGET=\"_top\" href=\"https://runtimeverification.com\">"
    + "   <IMG SRC=\"../images/favicon.ico\" style=\"vertical-align:middle\" height=\"30\" width=\"30\" >"
    + "   <TD> &nbsp;&nbsp; </TD>"
    + "   <TD BGCOLOR='#FFFFFF'><SPAN CLASS=\"navBarCell1RevNR\">PROPERTIES</SPAN></TD>"
    + "   <TD> &nbsp;&nbsp; </TD>"
    + "   <TD BGCOLOR='#FFFFFF'> <A HREF=\"Statistics.html\">STATISTICS</A></TD>\n"
    + "   <TD> &nbsp;&nbsp; </TD>"
    + "   <TD BGCOLOR='#FFFFFF'> HIGHLIGHTING </TD>"
    + "  </DIV>\n"
    + " </DIV>\n"
    + "</DIV>\n"
    + "<DIV CLASS=\"subNav\">\n"
    + " <UL CLASS=\"navList\">\n"
    + "  <LI><A HREF=\"../index.html?__properties/property-list.html\" target=\"_top\">FRAMES</A> </LI>\n"
    + "  <LI><A HREF=\"property-list.html\" target=\"_top\">NO FRAMES</A>  </LI>\n"
    + " </UL>\n"
    + " <UL CLASS=\"navList\" ID=\"allclasses_navbar_bottom\" style=\"display: none;\">\n"
    + "  <LI><A HREF=\"../allclasses-noframe.html\">All Classes</A></LI>\n"
    + " </UL>\n"
    + " <DIV>\n"
    + " <SCRIPT type=\"text/javascript\">\n"
    + "  <!--\n"
    + "  if(window==top) {\n"
    + "    document.writeln('<A HREF=\"../allclasses-noframe.html\"><B>All Classes</B></A>');\n"
    + "  }\n"
    + "  -->\n"
    + " </SCRIPT>\n"
    + " <NOSCRIPT>\n"
    + "  <A HREF=\"../allclasses-noframe.html\"><B>All Classes</B></A>\n"
    + " </NOSCRIPT>\n"
    + " </DIV>\n"
    + "<A NAME=\"skip-navbar_bottom\">\n<!-- -->\n</A>\n"
    + "</DIV>\n"
    + "<!-- ======== END OF BOTTOM NAVBAR ======= -->\n"
    + "\n"
    + "</BODY>\n"
    + "</HTML>\n";
    
    //////////////////////
    // HEADER2
    //
    //////////////////////
    private static final String HTMLHEADER2 =
    "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\n"
    + "<!--NewPage-->\n"
    + "<HTML>\n"
    + "<HEAD>\n"
    + "<!-- Generated by docsp on " + Util.getDate() + " -->\n"
    + "<TITLE>Properties</TITLE>\n"
    + "\n"
    + "<META NAME=\"date\" CONTENT=\"2014-12-10\">\n"
    + "\n"
    + "<LINK REL =\"stylesheet\" TYPE=\"text/css\" HREF=\"../stylesheet.css\" TITLE=\"Style\">\n"
    + "</HEAD>\n"
    + "\n"
    + "<BODY>\n"
    + "<SCRIPT type=\"text/javascript\">\n"
    + "function windowTitle()\n"
    + "{\n"
    + "    if (location.href.indexOf('is-external=true') == -1) {\n"
    + "        parent.document.title=\"Properties\";\n"
    + "    }\n"
    + "}\n"
    + "</SCRIPT>\n"
    + "<NOSCRIPT>\n"
    + "<DIV>Javascript is disabled on your browser.</DIV>\n"
    + "</NOSCRIPT>\n"
    + "\n"
    
    + "<!-- ========= START OF TOP NAVBAR ======= -->\n"
    + "<DIV CLASS=\"topNav\"><A NAME=\"navbar_top\">\n<!— --></A>"
    + " <A HREF=\"#skip-navbar_top\" title=\"Skip navigation links\"></A>"
    + " <A NAME=\"navbar_top_firstrow\">\n<!— -—>\n</A>\n"
    + " <UL CLASS=\"navList\" TITLE=\"Navigation\">\n"
    + "  <LI> <A HREF=\"../overview-summary.html\">Overview</A></LI>\n"
    + "  <LI> Package </LI>\n"
    + "  <LI> Class </LI>\n"
    + "  <LI> Tree </LI>\n"
    + "  <LI> Deprecated </LI>\n"
    + "  <LI> <A HREF=\"../index-all.html\">Index</A> </LI>\n"
    + "  <LI> <A HREF=\"../help-doc.html\">Help</A> </LI>\n"
    + " </UL>\n"
    + " <DIV CLASS=\"aboutLanguage\">"
    + "  <DIV CLASS=\"navBarRV\">"
    + "   <A TARGET=\"_top\" href=\"https://runtimeverification.com\">"
    + "   <IMG SRC=\"../images/favicon.ico\" style=\"vertical-align:middle\" height=\"30\" width=\"30\" >"
    + "   <TD> &nbsp;&nbsp; </TD>"
    + "   <TD BGCOLOR='#FFFFFF'><A HREF=\"property-list.html\">PROPERTIES</A></TD>"
    + "   <TD> &nbsp;&nbsp; </TD>"
    + "   <TD BGCOLOR='#FFFFFF'> <SPAN CLASS=\"navBarCell1RevNR\">STATISTICS</SPAN></TD>\n"
    + "   <TD> &nbsp;&nbsp; </TD>"
    + "   <TD BGCOLOR='#FFFFFF'> HIGHLIGHTING </TD>"
    + "  </DIV>\n"
    + " </DIV>\n"
    + "</DIV>\n"
    + "<DIV CLASS=\"subNav\">\n"
    + " <UL CLASS=\"navList\">\n"
    + "  <LI><A HREF=\"../index.html?__properties/Statistics.html\" target=\"_top\">FRAMES</A> </LI>\n"
    + "  <LI><A HREF=\"Statistics.html\" target=\"_top\">NO FRAMES</A>  </LI>\n"
    + " </UL>\n"
    + " <UL CLASS=\"navList\" ID=\"allclasses_navbar_top\" style=\"display: none;\">\n"
    + "  <LI><A HREF=\"../allclasses-noframe.html\">All Classes</A></LI>\n"
    + " </UL>\n"
    + " <DIV>\n"
    + "  <SCRIPT type=\"text/javascript\">\n"
    + "  <!--\n"
    + "  if(window==top) {\n"
    + "    document.writeln('<A HREF=\"../allclasses-noframe.html\"><B>All Classes</B></A>');\n"
    + "  }\n"
    + "  -->\n"
    + "  </SCRIPT>\n"
    + " </DIV>\n"
    + " <A NAME=\"skip-navbar_top\"></A>\n"
    + "</DIV>\n"
    + "<!-- ========= END OF TOP NAVBAR ========= -->\n";
    
    
    //////////////////////
    // FOOTER2
    //
    //////////////////////
    private static final String HTMLFOOTER2 =
    "<!-- ======= START OF BOTTOM NAVBAR ====== -->\n"
    + "<DIV CLASS=\"bottomNav\"><A NAME=\"navbar_bottom\">\n<!-- -->\n</A>"
    + " <A HREF=\"#skip-navbar_bottom\" title=\"Skip navigation links\"></A>"
    + " <A NAME=\"navbar_bottom_firstrow\">\n"
    + "  <!-- -->\n"
    + " </A>"
    + " <UL CLASS=\"navList\" TITLE=\"Navigation\">\n"
    
    + "  <LI> <A HREF=\"../overview-summary.html\">Overview</A></LI>\n"
    + "  <LI> Package </LI>\n"
    + "  <LI> Class </LI>\n"
    + "  <LI> Tree </LI>\n"
    + "  <LI> Deprecated </LI>\n"
    + "  <LI> <A HREF=\"../index-all.html\">Index</A> </LI>\n"
    + "  <LI> <A HREF=\"../help-doc.html\">Help</A> </LI>\n"
    + " </UL>\n"
    + " <DIV CLASS=\"aboutLanguage\">"
    + "  <DIV CLASS=\"navBarRV\">"
    + "   <A TARGET=\"_top\" href=\"https://runtimeverification.com\">"
    + "   <IMG SRC=\"../images/favicon.ico\" style=\"vertical-align:middle\" height=\"30\" width=\"30\" >"
    + "   <TD> &nbsp;&nbsp; </TD>"
    + "   <TD BGCOLOR='#FFFFFF'><A HREF=\"property-list.html\">PROPERTIES</A></TD>"
    + "   <TD> &nbsp;&nbsp; </TD>"
    + "   <TD BGCOLOR='#FFFFFF'> <SPAN CLASS=\"navBarCell1RevNR\">STATISTICS</SPAN></TD>\n"
    + "   <TD> &nbsp;&nbsp; </TD>"
    + "   <TD BGCOLOR='#FFFFFF'> HIGHLIGHTING </TD>"
    + "  </DIV>\n"
    + " </DIV>\n"
    + "</DIV>\n"
    + "<DIV CLASS=\"subNav\">\n"
    + " <UL CLASS=\"navList\">\n"
    + "  <LI><A HREF=\"../index.html?__properties/Statistics.html\" target=\"_top\">FRAMES</A> </LI>\n"
    + "  <LI><A HREF=\"Statistics.html\" target=\"_top\">NO FRAMES</A>  </LI>\n"
    + " </UL>\n"
    + " <UL CLASS=\"navList\" ID=\"allclasses_navbar_bottom\" style=\"display: none;\">\n"
    + "  <LI><A HREF=\"../allclasses-noframe.html\">All Classes</A></LI>\n"
    + " </UL>\n"
    + " <DIV>\n"
    + " <SCRIPT type=\"text/javascript\">\n"
    + "  <!--\n"
    + "  if(window==top) {\n"
    + "    document.writeln('<A HREF=\"../allclasses-noframe.html\"><B>All Classes</B></A>');\n"
    + "  }\n"
    + "  -->\n"
    + " </SCRIPT>\n"
    + " <NOSCRIPT>\n"
    + "  <A HREF=\"../allclasses-noframe.html\"><B>All Classes</B></A>\n"
    + " </NOSCRIPT>\n"
    + " </DIV>\n"
    + "<A NAME=\"skip-navbar_bottom\">\n<!-- -->\n</A>\n"
    + "</DIV>\n"
    + "<!-- ======== END OF BOTTOM NAVBAR ======= -->\n"
    + "\n"
    + "</BODY>\n"
    + "</HTML>\n";
}


