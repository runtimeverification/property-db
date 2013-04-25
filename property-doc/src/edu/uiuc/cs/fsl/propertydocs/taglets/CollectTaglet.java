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

import edu.uiuc.cs.fsl.propertydocs.util.DefaultMap;
import edu.uiuc.cs.fsl.propertydocs.util.FinishUp;
import edu.uiuc.cs.fsl.propertydocs.util.GenerateUrls;
import edu.uiuc.cs.fsl.propertydocs.util.PositionWrapper;

/**
* This Taglet allows for specifying the beginning of undecided text
*
* @author Patrick Meredith
*
*/

public class CollectTaglet implements Taglet {
    private static final String NAME = "collect.stats";
  	private static final String dir = System.getProperty( "outputpath" );

    private File stats = new File(dir + File.separator + "__properties" + File.separator + "undecided.stats");

    private Set<PositionWrapper> seenDocs = new HashSet<PositionWrapper>();
  //  private int chars = 0; //number of undecided chars
  //  private int words = 0; //number of undecided words
  //  private int lines = 0; //number of undecided lines
      
    private final String GLOBAL = "<global>";

    private Map<String, Integer> statsDB = new DefaultMap<String, Integer>(0); 

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
    @SuppressWarnings("unchecked")
    public static void register(Map tagletMap) {
       CollectTaglet tag = new CollectTaglet();
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
        handleTags(tag);
      }
      // This part is always executed, but for undecided we simply return the
      // empty String.  For other tags we will output fancy html/javascript
      return "";
    } 

    private void handleTags(Tag t){
      Tag[] tags = t.holder().inlineTags();
      boolean inUndecided = true; 
      //not sure if trim() is necessary.  I doubt it is, but speed isn't
      //an issue here
      String packageName = GenerateUrls.getPackageDoc(t).toString().trim();
   //   int c = 0; //character count
      int w = 0; //word count
   //   int l = 0; //lines count
      for(Tag tag : tags){
        if(
            (tag.name().equals("@property.open")
          || tag.name().equals("@description.open")) ){
          inUndecided = false; 
        }
        else if(
            (tag.name().equals("@property.close")
          || tag.name().equals("@description.close")) ){
          inUndecided = true;
        }
        else if(tag.name().equals("Text") && inUndecided){
          String text = tag.text().trim();
          if(text.length() == 0) continue;
          w += text.split("\\s+").length; 
          String printUnd = System.getenv("PRINT_UNDECIDED");
          if(printUnd != null && printUnd.equals("TRUE")){
            System.out.println("UNDECIDED TEXT={" + text + "}");
          }   
        }
      } 
      Integer globalWords = statsDB.get(GLOBAL);
      Integer packageWords = statsDB.get(packageName);
      statsDB.put(GLOBAL, globalWords + w);
      statsDB.put(packageName, packageWords + w);
      try {
        FileOutputStream fos = new FileOutputStream(stats);
        ObjectOutputStream ps = new ObjectOutputStream(fos);
      //  ps.writeInt(chars);
        ps.writeObject(statsDB);
      //  ps.writeInt(lines);
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
