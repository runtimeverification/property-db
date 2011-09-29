package edu.uiuc.cs.fsl.propertydocs.taglets;

import com.sun.tools.doclets.Taglet;

import com.sun.javadoc.Tag;

import java.util.Map;

import edu.uiuc.cs.fsl.propertydocs.util.GenerateUrls;
import edu.uiuc.cs.fsl.propertydocs.util.PositionWrapper;


/**
* This Taglet allows for the inline specification of links to
* properties
*
* @author Patrick Meredith
*
*/

public class InformalCloseTaglet implements Taglet {
  private static final String NAME = "informal.close";

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
       InformalCloseTaglet tag = new InformalCloseTaglet();
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
      return "</DIV>";
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
