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
* This Taglet allows for opening a formal property section in
* a javadoc comment
*
* @author Patrick Meredith
*
*/

public class FormalOpenTaglet implements Taglet {
    private static final String NAME = "formal.open";
  	private static final String dir = Standard.htmlDoclet.configuration().destDirName;

    private File stats = new File(dir + File.separator + "__properties" + File.separator + "formal.stats");

    private Set<PositionWrapper> seenDocs = new HashSet<PositionWrapper>();

    //keep track of which set of formal.open/formal.close (domain from here on) we are in
    private Map<PositionWrapper, Integer> domainNums =
      new HashMap<PositionWrapper, Integer>(); 
    //keep track of the last domain number for a given position
    private Map<PositionWrapper, Integer> maxDomainNums = 
      new HashMap<PositionWrapper, Integer>(); 

    //keep track of the links for each domain at each position
    private Map<PositionWrapper, Map<Integer, StringBuilder>> linksMap =
      new HashMap<PositionWrapper, Map<Integer, StringBuilder>>(); 
    //keep track of the number of links for each domain at each position
    private Map<PositionWrapper, Map<Integer, Integer>> numLinks =
      new HashMap<PositionWrapper, Map<Integer, Integer>>(); 

    private int chars = 0; //number of formal chars
    private int words = 0; //number of formal words
    private int lines = 0; //number of formal lines

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
       FormalOpenTaglet tag = new FormalOpenTaglet();
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
     * and collect statistics on <b>all</b> the formal sections in the 
     * document.
     *
     * Statistics will only be collected if we have not already seen an 
     * formal.open tag for this document.  We keep track of the document
     * by hashing its SourcePosition.  The PositionWrapper makes these
     * hashable.
     *
     * @param tag he <code>Tag</code> representation of this custom tag.
     */
    public String toString(Tag tag) {
      //System.out.println(tag.position());
      PositionWrapper p = new PositionWrapper(tag.holder().position());
      if(!(seenDocs.contains(p))){
        domainNums.put(p, 0);
        seenDocs.add(p);
        Map<Integer, StringBuilder> domainMap = new HashMap<Integer, StringBuilder>();
        Map<Integer, Integer> numLinksMap = new HashMap<Integer, Integer>();
        linksMap.put(p, domainMap);
        numLinks.put(p, numLinksMap);
        handleTags(tag.holder().inlineTags());
        maxDomainNums.put(p, mkLinks(tag, domainMap, numLinksMap));
      }
      Integer domain = domainNums.get(p);
      String links = linksMap.get(p).get(domain).toString();
      Integer num = numLinks.get(p).get(domain);
      domainNums.put(p, (domainNums.get(p) + 1) % maxDomainNums.get(p));
      if(num == 0){
        return "<DIV CLASS=\"Red\" NAME=\"brokenformal\""
        + " ONMOUSEOVER=\"balloon.showTooltip(event, 'This is a formalized property with no "
        + "shortcuts.  Probably user error.')\"" 
        + " STYLE=\"display:inline\">";
      }
      else if(num == 1){
        return "<DIV CLASS=\"NavBarCell1Rev\" NAME=\"formal\" ONMOUSEOVER="
           + "\"balloon.showTooltip(event,'This describes a formalized property.  "
           + "The formalization may be viewed by clicking on the link below:<br/>" + links +  " ',1)\""
           + " STYLE=\"display:inline\">";
      }
      return "<DIV CLASS=\"NavBarCell1Rev\" NAME=\"formal\" ONMOUSEOVER="
       + "\"balloon.showTooltip(event,'This describes several formalized properties.  "
       + "The formalizations may be viewed by clicking on the links below:<br/>" + links +  " ',1)\""
       + " STYLE=\"display:inline\">";
    } 

    //should probably merge mkLinks and handleTags for efficiency.
    //right now I don't care
    private int mkLinks(Tag tag, Map<Integer, StringBuilder> linksMap, Map<Integer, Integer> numLinksMap){
      int domain = -1;
      int numLinks = 0;
      StringBuilder linksBuilder = null;
      for(Tag t : tag.holder().inlineTags()){
        if(t.name().equals("@formal.open")){
          linksBuilder = new StringBuilder();
          numLinksMap.put(++domain, 0);
          linksMap.put(domain, linksBuilder); 
          numLinks = 0;
        }
        else if(t.name().equals("@property.shortcut")){
          linksBuilder.append(GenerateUrls.processPropertyShortcutTag(t) + "<br />"); 
          numLinksMap.put(domain, numLinksMap.get(domain) + 1);
        }
      }
      return domain + 1;
    }

    private void handleTags(Tag[] tags){
      boolean inFormal = false; 
      boolean inParent = false;
      int c = 0; //character count
      int w = 0; //word count
      int l = 0; //line count
      for(Tag tag: tags){
        if(tag.name().equals("@formal.open")){ 
          inFormal = true;
        }
        else if(tag.name().equals("@formal.close")){
          inFormal = false;
        }
        else if(tag.name().equals("Text") && inFormal){
          String text = tag.text().trim();
          c += text.length();
          w += text.split("\\s+").length; 
          l += text.split("\\n").length; 
        }
        else if(
            (tag.name().equals("@undecided.close")
          || tag.name().equals("@undecided.open")
          || tag.name().equals("@informal.close")
          || tag.name().equals("@informal.open")
          || tag.name().equals("@descriptive.close")
          || tag.name().equals("@descriptive.open")
          || tag.name().equals("@inheritDoc")) && inFormal){
           System.err.println("ERROR: " + tag.name() + " inside formal environment in comment near " 
                              + tag.holder().position() + " is not allowed");

           System.exit(1);
        }
      } 
      if(inFormal) {
         System.err.println("ERROR: @formal.open tag in comment near " 
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
