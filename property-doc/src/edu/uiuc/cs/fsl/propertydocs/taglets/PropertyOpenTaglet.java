package edu.uiuc.cs.fsl.propertydocs.taglets;

import com.sun.javadoc.Tag;

import com.sun.tools.doclets.Taglet;
import com.sun.tools.doclets.standard.Standard;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import edu.uiuc.cs.fsl.propertydocs.util.GenerateUrls;
import edu.uiuc.cs.fsl.propertydocs.util.PositionWrapper;

/**
* This Taglet allows for opening a property property section in
* a javadoc comment
*
* @author Patrick Meredith
*
*/

public class PropertyOpenTaglet implements Taglet {
    private static final String NAME = "property.open";
  	private static final String dir = Standard.htmlDoclet.configuration().destDirName;

    private File stats = new File(dir + File.separator + "__properties" + File.separator + "property.stats");

    private Set<PositionWrapper> seenDocs = new HashSet<PositionWrapper>();

    private Map<String, Integer> argumentStats = new HashMap<String, Integer>();

    private int chars = 0; //number of property chars
    private int words = 0; //number of property words
    private int lines = 0; //number of property lines

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
    
    /**this IS an inline tag*/ 
    public boolean isInlineTag()   { return true; }
    
    /**
     * Register this Taglet.
     * @param tagletMap  the map to register this tag to.
     */
    public static void register(Map tagletMap) {
       PropertyOpenTaglet tag = new PropertyOpenTaglet();
       Taglet t = (Taglet) tagletMap.get(tag.getName());
       if (t != null) {
           tagletMap.remove(tag.getName());
       }
       tagletMap.put(tag.getName(), tag);
    }

     /**
     * Given the <code>Tag</code> representation of this custom
     * tag, return its string representation.
     *
     * Here we have to do some annoying garbage because you there is no
     * way short of file io to share info between Taglets.  Instead
     * I am going to parse all the inline tags if the whole document
     * and collect statistics on <b>all</b> the property sections in the 
     * document.
     *
     * Statistics will only be collected if we have not already seen an 
     * property.open tag for this document.  We keep track of the document
     * by hashing its SourcePosition.  The PositionWrapper makes these
     * hashable.
     *
     * @param tag he <code>Tag</code> representation of this custom tag.
     */
    public String toString(Tag tag) {
      //System.out.println(tag.position());
      PositionWrapper p = new PositionWrapper(tag.holder().position());
      if(!(seenDocs.contains(p))){
        seenDocs.add(p);
        handleTags(tag.holder().inlineTags());
      }
      String[] arguments = tag.text().trim().split("\\s+");
      int num = numLinks(arguments);
      String links = handleLinks(arguments, p, tag);
      if(num == 0){
        return "<DIV CLASS=\"Red\" NAME=\"brokenproperty\""
        + " ONMOUSEOVER=\"balloon.showTooltip(event, 'This is a property with no "
        + "formalizations.')\"" 
        + " STYLE=\"display:inline\">";
      }
      else if(num == 1){
        return "<DIV CLASS=\"NavBarCell1Rev\" NAME=\"property\" ONMOUSEOVER="
           + "\"balloon.showTooltip(event,'This describes a formalized property.  "
           + "The property may be viewed by clicking on the link below:<br/>" + links +  " ',1)\""
           + " STYLE=\"display:inline\">";
      }
      return "<DIV CLASS=\"NavBarCell1Rev\" NAME=\"property\" ONMOUSEOVER="
       + "\"balloon.showTooltip(event,'This describes several formalized properties.  "
       + "The properties may be viewed by clicking on the links below:<br/>" + links +  " ',1)\""
       + " STYLE=\"display:inline\">";
    } 

    private int numLinks(String[] args){
      int i = 0;
      for(String arg : args){
        if(arg.startsWith("Property:")) ++i;
        if(i > 1) return i;
      }
      return i;
    }

    private String handleLinks(String[] args, PositionWrapper p, Tag tag){
      String ret = "";
      for(String arg : args){
        if(arg.startsWith("Property:")) {
          String[] parts = arg.split(":");
          if(parts.length != 2) throw new RuntimeException("too many ':''s in Property specification in comment at " + p); 
          ret += "<A HREF=\\'" + GenerateUrls.buildRelativeUrl(tag) + "__properties/html/" + parts[1].replaceAll("[.]","/") + ".html\\'>" + parts[1] + "</A> <BR />";
        }
      }
      //System.out.println("***********" + ret);
      return ret;
    }


    private void handleTags(Tag[] tags){
      boolean inProperty = false; 
      boolean inParent = false;
      int c = 0; //character count
      int w = 0; //word count
      int l = 0; //line count
      String[] arguments = null;
      for(Tag tag: tags){
        if(tag.name().equals("@property.open")){ 
          arguments = tag.text().trim().split("\\s+");
          inProperty = true;
        }
        else if(tag.name().equals("@property.close")){
          inProperty = false;
        }
        else if(tag.name().equals("Text") && inProperty){
          String text = tag.text().trim();
          int localW = text.split("\\s+").length;
          c += text.length();
          w += localW; 
          l += text.split("\\n").length; 
          for(String arg : arguments){
            if(arg.startsWith("Property:")) continue;
            if(arg.equals("")) continue;
            Integer currentStat = argumentStats.get(arg);
            if(currentStat == null) argumentStats.put(arg, localW);
            else                     argumentStats.put(arg, currentStat + localW);
          }
        }
        else if(
            (tag.name().equals("@description.close")
          || tag.name().equals("@description.open")
          || tag.name().equals("@inheritDoc")) && inProperty){
           System.err.println("ERROR: " + tag.name() + " inside property environment in comment near " 
                              + tag.holder().position() + " is not allowed");

           System.exit(1);
        }
      } 
      if(inProperty) {
         System.err.println("ERROR: @property.open tag in comment near " 
                          + tags[0].holder().position() + " was not closed");
         System.exit(1);
      }
      chars += c;
      words += w;
      lines += l;
      try {
        FileOutputStream fos = new FileOutputStream(stats);
        ObjectOutputStream ps = new ObjectOutputStream(fos);
        ps.writeInt(chars);
        ps.writeInt(words);
        ps.writeInt(lines);
        ps.writeObject(argumentStats);
        ps.close();
      } catch (java.io.IOException e){
        throw new RuntimeException(e);
      }   
    }

    /**
     * This method should not be called since arrays of inline tags do not
     * exist.  Method {@link #tostring(Tag)} should be used to convert this
     * inline tag to a string.
     * @param tags the array of <code>Tag</code>s representing of this custom tag.
     */
    public String toString(Tag[] tags) {
      return null;
    }
}
