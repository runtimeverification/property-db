package edu.uiuc.cs.fsl.propertydocs.taglets;

import com.sun.javadoc.Tag;

import com.sun.tools.doclets.Taglet;
import com.sun.tools.doclets.standard.Standard;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import edu.uiuc.cs.fsl.propertydocs.util.GenerateUrls;
import edu.uiuc.cs.fsl.propertydocs.util.PositionWrapper;

/**
* This Taglet allows for specifying the beginning of undecided text
*
* @author Patrick Meredith
*
*/

public class UndecidedOpenTaglet implements Taglet {
    private static final String NAME = "undecided.open";
  	private static final String dir = Standard.htmlDoclet.configuration().destDirName;

    private File stats = new File(dir + File.separator + "__properties" + File.separator + "undecided.stats");

    private Set<PositionWrapper> seenDocs = new HashSet<PositionWrapper>();
    private int chars = 0; //number of undecided chars
    private int words = 0; //number of undecided words
    private int lines = 0; //number of undecided lines

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
       UndecidedOpenTaglet tag = new UndecidedOpenTaglet();
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
     * and collect statistics on <b>all</b> the undecided sections in the 
     * document.
     *
     * Statistics will only be collected if we have not already seen an 
     * undecided.open tag for this document.  We keep track of the document
     * by hashing its SourcePosition.  The PositionWrapper makes these
     * hashable.
     *
     * @param tag he <code>Tag</code> representation of this custom tag.
     */
    public String toString(Tag tag) {
      PositionWrapper p = new PositionWrapper(tag.holder().position());
      if(!(seenDocs.contains(p))){
        seenDocs.add(p);
        handleTags(tag.holder().inlineTags());
      }
      // This part is always executed, but for undecided we simply return the
      // empty String.  For other tags we will output fancy html/javascript
      return "";
    } 

    private void handleTags(Tag[] tags){
      boolean inUndecided = false; 
      int c = 0; //character count
      int w = 0; //word count
      int l = 0; //lines count
      for(Tag tag : tags){
        if(tag.name().equals("@undecided.open")){ 
          inUndecided = true;
        }
        else if(tag.name().equals("@undecided.close")){
          inUndecided = false;
        }
        else if(tag.name().equals("Text") && inUndecided){
          String text = tag.text().trim();
          c += text.length();
          w += text.split("\\s+").length; 
          l += text.split("\\n").length; 
        }
        else if(
            (tag.name().equals("@formal.close")
          || tag.name().equals("@formal.open")
          || tag.name().equals("@informal.close")
          || tag.name().equals("@informal.open")
          || tag.name().equals("@descriptive.close")
          || tag.name().equals("@descriptive.open")
          || tag.name().equals("@property.shortcut")
          || tag.name().equals("@inheritDoc")) && inUndecided){
           System.err.println("ERROR: " + tag.name() + " inside undecided environment in comment near " 
                              + tag.holder().position() + " is not allowed");

           System.exit(1);
        }
      } 
      if(inUndecided) {
         System.err.println("ERROR: @undecided.open tag in comment near " 
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
