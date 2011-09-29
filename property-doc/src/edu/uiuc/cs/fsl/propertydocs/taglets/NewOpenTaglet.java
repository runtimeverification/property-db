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
import edu.uiuc.cs.fsl.propertydocs.util.DiskHash;

/**
* This Taglet allows for marking the beginning of an new property specification
*
* @author Patrick Meredith
*
*/

public class NewOpenTaglet implements Taglet {
    private static final String NAME = "new.open";
  	private static final String dir = Standard.htmlDoclet.configuration().destDirName;

    private Set<PositionWrapper> seenDocs = new HashSet<PositionWrapper>();

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
    
    static {
      DiskHash.statsDB.put("new", 0);
    }

    /**
     * Register this Taglet.
     * @param tagletMap  the map to register this tag to.
     */
    @SuppressWarnings("unchecked")
    public static void register(Map tagletMap) {
       NewOpenTaglet tag = new NewOpenTaglet();
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
     * and collect statistics on <b>all</b> the new sections in the 
     * document.
     *
     * Statistics will only be collected if we have not already seen an 
     * new.open tag for this document.  We keep track of the document
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
      // This part is always executed, but for new we simply return the
      // empty String.  For other tags we will output fancy html/javascript
      return "<DIV CLASS=\"NavBarCell1\" NAME=\"new\""
       + " ONMOUSEOVER=\"balloon.showTooltip(event,'This is text that has been added to the API.')\""
       + " STYLE=\"display:inline\">";
    } 

    private void handleTags(Tag[] tags){
      boolean inNew = false; 
      int w = 0; //word count
      for(Tag tag : tags){
        if(tag.name().equals("@new.open")){ 
          inNew = true;
        }
        else if(tag.name().equals("@new.close")){
          inNew = false;
        }
        else if(tag.name().equals("Text") && inNew){
          String text = tag.text().trim();
          w += text.split("\\s+").length; 
        }
      } 
      if(inNew) {
         System.err.println("ERROR: @new.open tag in comment near " 
                          + tags[0].holder().position() + " was not closed");
         System.exit(1);
      }
      Integer words = DiskHash.statsDB.get("new");
      DiskHash.statsDB.put("new", words + w);
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
