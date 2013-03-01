/* Copyright 2011 Patrick Meredith
 * getPackageDoc, getTopLevelClassDoc, findClass, most of buildRelativeUrl Copyright 2010 Chad Retz
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package edu.uiuc.cs.fsl.propertydocs.util;

import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.Doc;
import com.sun.javadoc.PackageDoc;
import com.sun.javadoc.ProgramElementDoc;
import com.sun.javadoc.Tag;
import com.sun.tools.doclets.Taglet;

public class GenerateUrls {
  public static PackageDoc getPackageDoc(Tag tag) {
    Doc holder = tag.holder();
    if (holder instanceof ProgramElementDoc) {
      return ((ProgramElementDoc) holder).containingPackage();
    } else if (holder instanceof PackageDoc) {
      return (PackageDoc) holder;
    } else {
      throw new RuntimeException("Unrecognized holder: " + holder);
    }
  }
 
  private static ClassDoc getTopLevelClassDoc(ClassDoc classDoc, int depth) {
    if (classDoc.containingClass() == null) {
      return classDoc;
    } 
    if (classDoc.containingClass().equals(classDoc)) {
      return classDoc;
    }
    if (depth < 30){ //something is wonky here, not sure why I am getting infinite recursion
                     //my guess is .equals is not properly implemented
      return getTopLevelClassDoc(classDoc, depth + 1);
    }
    else {
      return classDoc;
    }
  }

  private static ClassDoc getTopLevelClassDoc(Tag tag, int depth) {
    Doc holder = tag.holder();
    if (holder instanceof PackageDoc) {
      return null;
    } else if (holder instanceof ClassDoc) {
      return getTopLevelClassDoc((ClassDoc) holder, depth);
    } else if (holder instanceof ProgramElementDoc) {
      return getTopLevelClassDoc(((ProgramElementDoc) holder)
        .containingClass(), depth);
    } else {
      throw new RuntimeException("Unrecognized holder: " + holder);
    }
  }
 
  private static ClassDoc findClass(String className, ClassDoc[] classImports) {
    for (ClassDoc classDoc : classImports) {
      if (classDoc.name().equals(className)) {
        return classDoc;
      }
    }
    return null;
  }
 
  private static ClassDoc findClass(String className, PackageDoc... packageImports) {
    for (PackageDoc packageDoc : packageImports) {
      for (ClassDoc found : packageDoc.allClasses(true)) {
        if (found.name().equals(className)) {
          return found;
        }
      }
    }
    return null;
  }
 
  public static String buildRelativeUrl(Tag tag){
    PackageDoc packageDoc = getPackageDoc(tag);
    ClassDoc topLevelClassDoc = getTopLevelClassDoc(tag,0);
    StringBuilder href = new StringBuilder();
    //here we are attempting to find the root directory of the
    //generated documents so that we can add the proper number of ".."
    //to any generated links
    int dotIndex = packageDoc.name().indexOf('.');
    while (dotIndex != -1) {
      href.append("../");
      dotIndex = packageDoc.name().indexOf('.', dotIndex + 1);
    }
    //package name is empty when it is the root package
    if (!(packageDoc.name().length() == 0)) {
      href.append("../");
    }  
      return href.toString();
  }

  //Anything that gets its own webpage in javadoc
  private static String classNameToUrl(String name){
    return name.replaceAll("[.]","/") + ".html";
  }

  //this is for turning a method to a url.
  //this will generate a url that points to the middle of a webpage somewhere
  private static String methodNameToUrl(String name){
    String[] toplevel = name.split("[(]");
    String[] pieces = toplevel[0].split("[.]"); 
    StringBuilder ret = new StringBuilder();
    { int i = 0;
      for(; i < pieces.length - 2; ++i){
        ret.append(pieces[i]);
        ret.append("/");
      }
      ret.append(pieces[i]);
    }
    ret.append(".html#");
    ret.append(pieces[pieces.length - 1]);
    ret.append("(");
    ret.append(toplevel[1]);
    return ret.toString();
  }

  //this is for class elements that are not methods.
  //these are easier to handle because they do not have possible
  //arguments, as arguments to methods do NOT have their "."'s changed
  //to "/".  Everything is convoluted here, /sigh
  private static String elementNameToUrl(String name){
    String[] pieces = name.split("[.]");
    StringBuilder ret = new StringBuilder();
    for(int i = 0; i < pieces.length - 2; ++i){
      ret.append(pieces[i]);
      ret.append("/");
    }
    ret.append(pieces[pieces.length - 2]);
    ret.append(".html#");
    ret.append(pieces[pieces.length - 1]);
    return ret.toString();
  }

  public static String getUrl(Tag tag){
    Doc doc = tag.holder();
    String name = doc.toString();
    //remove anything between nested < and >
    { 
      StringBuilder nameFixer = new StringBuilder();
      int nestedDepth = 0;
      for(int i = 0; i < name.length(); ++i){
        char c = name.charAt(i);
        if(c == '<'){
          ++nestedDepth;
        }
        else if(c == '>'){
          --nestedDepth;
        }
        else if(nestedDepth == 0){
          nameFixer.append(c);
        }
      }
      name = nameFixer.toString();
    }
    //If the document is a class, interface, or annotation it has its
    //own file, and the url to the file is constructed by classNameToUrl
    if(doc.isClass() || doc.isInterface() || doc.isAnnotationType() 
      || doc.isError() || doc.isException()){
      return classNameToUrl(name);
    }
    //Methods must be handled specially
    if(doc.isMethod() || doc.isConstructor() || doc.isAnnotationTypeElement()){
      return methodNameToUrl(name); 
    }
    //Non-method elements, such as fields, are handled with a simpler method:
    //elementNameToUrl
    if(doc.isField() || doc.isEnumConstant()){
      return elementNameToUrl(name);
    } 
    throw new RuntimeException("Type of tag: " + tag + " could not be handled!");
  }

  private static String[] getUrlAndName(Tag tag){
    String text = tag.text().trim();
//I'm tired of java and it's uninitialized ERROR.  Make it a warning.  Total dumbassery
    String urlPart, namePart = null;  
    String[] args = text.split("\\s+");
    urlPart = args[0]; 
    if(args.length == 1) namePart = text;
    else if(args.length > 1) {
      StringBuilder nameBuilder = new StringBuilder();
      for(int i = 1; i < args.length; ++i){  
          nameBuilder.append(args[i] + " "); 
      }
      namePart = nameBuilder.toString().trim();
    }
    String[] ret = new String[3];
    ret[0] = buildRelativeUrl(tag);
    ret[1] = classNameToUrl(urlPart);
    ret[2] = namePart;
    return ret;
  }

  public static String processLinkTag(Tag tag){
   if(!tag.name().equals("@link")) throw new RuntimeException("Not a link tag: " + tag.name());
   String[] urlAndName = getUrlAndName(tag);
   return "<A HREF=\"" + urlAndName[0] + urlAndName[1] + "\"><CODE>" + urlAndName[2] + "</CODE></A>"; 
  }

  public static String processLinkPlainTag(Tag tag){
   if(!tag.name().equals("@linkplain")) throw new RuntimeException("Not a linkplain tag" + tag.name());
   String[] urlAndName = getUrlAndName(tag);
   return "<A HREF=\"" + urlAndName[0] + urlAndName[1] + "\">" + urlAndName[2] + "</A>"; 
  }

  public static String processPropertyLinkTag(Tag tag){
   if(!tag.name().equals("@property.link")) 
      throw new RuntimeException("Not a property.link tag" + tag.name());
   String[] urlAndName = getUrlAndName(tag);
   return "<A HREF=\"" + urlAndName[0] + "__properties/" + urlAndName[1] + "\">" + urlAndName[2] + "</A>"; 
  }

  public static String processPropertyShortcutTag(Tag tag){
   if(!tag.name().equals("@property.shortcut")) 
      throw new RuntimeException("Not a property.shortcut tag" + tag.name());
   String[] urlAndName = getUrlAndName(tag);
   return "<A HREF=\\'" + urlAndName[0] + "__properties/" + urlAndName[1] + "\\'>" + urlAndName[2] + "</A>"; 
  }
}
