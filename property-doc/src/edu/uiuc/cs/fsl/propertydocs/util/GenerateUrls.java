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
 
  private static ClassDoc getTopLevelClassDoc(ClassDoc classDoc) {
    if (classDoc.containingClass() == null) {
      return classDoc;
    } else {
      return getTopLevelClassDoc(classDoc);
    }
  }

  private static ClassDoc getTopLevelClassDoc(Tag tag) {
    Doc holder = tag.holder();
    if (holder instanceof PackageDoc) {
      return null;
    } else if (holder instanceof ClassDoc) {
      return getTopLevelClassDoc((ClassDoc) holder);
    } else if (holder instanceof ProgramElementDoc) {
      return getTopLevelClassDoc(((ProgramElementDoc) holder)
        .containingClass());
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
    ClassDoc topLevelClassDoc = getTopLevelClassDoc(tag);
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
    ret[1] = urlPart.replaceAll("[.]","/") + ".html";
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
