/*
 * Copyright (c) 1997, 2003, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */
package javax.swing.text;

import java.awt.Color;
import java.awt.Component;
import java.awt.Toolkit;
import javax.swing.Icon;

/** {@collect.stats}
 * <p>
 * A collection of <em>well known</em> or common attribute keys
 * and methods to apply to an AttributeSet or MutableAttributeSet
 * to get/set the properties in a typesafe manner.
 * <p>
 * The paragraph attributes form the definition of a paragraph to be rendered.
 * All sizes are specified in points (such as found in postscript), a
 * device independent measure.
 * </p>
 * <p align=center><img src="doc-files/paragraph.gif"
 * alt="Diagram shows SpaceAbove, FirstLineIndent, LeftIndent, RightIndent,
 *      and SpaceBelow a paragraph."></p>
 * <p>
 *
 * @author  Timothy Prinzing
 */
public class StyleConstants {

    /** {@collect.stats}
     * Name of elements used to represent components.
     */
    public static final String ComponentElementName = "component";

    /** {@collect.stats}
     * Name of elements used to represent icons.
     */
    public static final String IconElementName = "icon";

    /** {@collect.stats}
     * Attribute name used to name the collection of
     * attributes.
     */
    public static final Object NameAttribute = new StyleConstants("name");

    /** {@collect.stats}
     * Attribute name used to identifiy the resolving parent
     * set of attributes, if one is defined.
     */
    public static final Object ResolveAttribute = new StyleConstants("resolver");

    /** {@collect.stats}
     * Attribute used to identify the model for embedded
     * objects that have a model view separation.
     */
    public static final Object ModelAttribute = new StyleConstants("model");

    /** {@collect.stats}
     * Returns the string representation.
     *
     * @return the string
     */
    public String toString() {
        return representation;
    }

    // ---- character constants -----------------------------------

    /** {@collect.stats}
     * Bidirectional level of a character as assigned by the Unicode bidi
     * algorithm.
     */
    public static final Object BidiLevel = new CharacterConstants("bidiLevel");

    /** {@collect.stats}
     * Name of the font family.
     */
    public static final Object FontFamily = new FontConstants("family");

    /** {@collect.stats}
     * Name of the font family.
     *
     * @since 1.5
     */
    public static final Object Family = FontFamily;

    /** {@collect.stats}
     * Name of the font size.
     */
    public static final Object FontSize = new FontConstants("size");

    /** {@collect.stats}
     * Name of the font size.
     *
     * @since 1.5
     */
    public static final Object Size = FontSize;

    /** {@collect.stats}
     * Name of the bold attribute.
     */
    public static final Object Bold = new FontConstants("bold");

    /** {@collect.stats}
     * Name of the italic attribute.
     */
    public static final Object Italic = new FontConstants("italic");

    /** {@collect.stats}
     * Name of the underline attribute.
     */
    public static final Object Underline = new CharacterConstants("underline");

    /** {@collect.stats}
     * Name of the Strikethrough attribute.
     */
    public static final Object StrikeThrough = new CharacterConstants("strikethrough");

    /** {@collect.stats}
     * Name of the Superscript attribute.
     */
    public static final Object Superscript = new CharacterConstants("superscript");

    /** {@collect.stats}
     * Name of the Subscript attribute.
     */
    public static final Object Subscript = new CharacterConstants("subscript");

    /** {@collect.stats}
     * Name of the foreground color attribute.
     */
    public static final Object Foreground = new ColorConstants("foreground");

    /** {@collect.stats}
     * Name of the background color attribute.
     */
    public static final Object Background = new ColorConstants("background");

    /** {@collect.stats}
     * Name of the component attribute.
     */
    public static final Object ComponentAttribute = new CharacterConstants("component");

    /** {@collect.stats}
     * Name of the icon attribute.
     */
    public static final Object IconAttribute = new CharacterConstants("icon");

    /** {@collect.stats}
     * Name of the input method composed text attribute. The value of
     * this attribute is an instance of AttributedString which represents
     * the composed text.
     */
    public static final Object ComposedTextAttribute = new StyleConstants("composed text");

    /** {@collect.stats}
     * The amount of space to indent the first
     * line of the paragraph.  This value may be negative
     * to offset in the reverse direction.  The type
     * is Float and specifies the size of the space
     * in points.
     */
    public static final Object FirstLineIndent = new ParagraphConstants("FirstLineIndent");

    /** {@collect.stats}
     * The amount to indent the left side
     * of the paragraph.
     * Type is float and specifies the size in points.
     */
    public static final Object LeftIndent = new ParagraphConstants("LeftIndent");

    /** {@collect.stats}
     * The amount to indent the right side
     * of the paragraph.
     * Type is float and specifies the size in points.
     */
    public static final Object RightIndent = new ParagraphConstants("RightIndent");

    /** {@collect.stats}
     * The amount of space between lines
     * of the paragraph.
     * Type is float and specifies the size as a factor of the line height
     */
    public static final Object LineSpacing = new ParagraphConstants("LineSpacing");

    /** {@collect.stats}
     * The amount of space above the paragraph.
     * Type is float and specifies the size in points.
     */
    public static final Object SpaceAbove = new ParagraphConstants("SpaceAbove");

    /** {@collect.stats}
     * The amount of space below the paragraph.
     * Type is float and specifies the size in points.
     */
    public static final Object SpaceBelow = new ParagraphConstants("SpaceBelow");

    /** {@collect.stats}
     * Alignment for the paragraph.  The type is
     * Integer.  Valid values are:
     * <ul>
     * <li>ALIGN_LEFT
     * <li>ALIGN_RIGHT
     * <li>ALIGN_CENTER
     * <li>ALIGN_JUSTIFED
     * </ul>
     *
     */
    public static final Object Alignment = new ParagraphConstants("Alignment");

    /** {@collect.stats}
     * TabSet for the paragraph, type is a TabSet containing
     * TabStops.
     */
    public static final Object TabSet = new ParagraphConstants("TabSet");

    /** {@collect.stats}
     * Orientation for a paragraph.
     */
    public static final Object Orientation = new ParagraphConstants("Orientation");
    /** {@collect.stats}
     * A possible value for paragraph alignment.  This
     * specifies that the text is aligned to the left
     * indent and extra whitespace should be placed on
     * the right.
     */
    public static final int ALIGN_LEFT = 0;

    /** {@collect.stats}
     * A possible value for paragraph alignment.  This
     * specifies that the text is aligned to the center
     * and extra whitespace should be placed equally on
     * the left and right.
     */
    public static final int ALIGN_CENTER = 1;

    /** {@collect.stats}
     * A possible value for paragraph alignment.  This
     * specifies that the text is aligned to the right
     * indent and extra whitespace should be placed on
     * the left.
     */
    public static final int ALIGN_RIGHT = 2;

    /** {@collect.stats}
     * A possible value for paragraph alignment.  This
     * specifies that extra whitespace should be spread
     * out through the rows of the paragraph with the
     * text lined up with the left and right indent
     * except on the last line which should be aligned
     * to the left.
     */
    public static final int ALIGN_JUSTIFIED = 3;

    // --- character attribute accessors ---------------------------

    /** {@collect.stats}
     * Gets the BidiLevel setting.
     *
     * @param a the attribute set
     * @return the value
     */
    public static int getBidiLevel(AttributeSet a) {
        Integer o = (Integer) a.getAttribute(BidiLevel);
        if (o != null) {
            return o.intValue();
        }
        return 0;  // Level 0 is base level (non-embedded) left-to-right
    }

    /** {@collect.stats}
     * Sets the BidiLevel.
     *
     * @param a the attribute set
     * @param o the bidi level value
     */
    public static void setBidiLevel(MutableAttributeSet a, int o) {
        a.addAttribute(BidiLevel, new Integer(o));
    }

    /** {@collect.stats}
     * Gets the component setting from the attribute list.
     *
     * @param a the attribute set
     * @return the component, null if none
     */
    public static Component getComponent(AttributeSet a) {
        return (Component) a.getAttribute(ComponentAttribute);
    }

    /** {@collect.stats}
     * Sets the component attribute.
     *
     * @param a the attribute set
     * @param c the component
     */
    public static void setComponent(MutableAttributeSet a, Component c) {
        a.addAttribute(AbstractDocument.ElementNameAttribute, ComponentElementName);
        a.addAttribute(ComponentAttribute, c);
    }

    /** {@collect.stats}
     * Gets the icon setting from the attribute list.
     *
     * @param a the attribute set
     * @return the icon, null if none
     */
    public static Icon getIcon(AttributeSet a) {
        return (Icon) a.getAttribute(IconAttribute);
    }

    /** {@collect.stats}
     * Sets the icon attribute.
     *
     * @param a the attribute set
     * @param c the icon
     */
    public static void setIcon(MutableAttributeSet a, Icon c) {
        a.addAttribute(AbstractDocument.ElementNameAttribute, IconElementName);
        a.addAttribute(IconAttribute, c);
    }

    /** {@collect.stats}
     * Gets the font family setting from the attribute list.
     *
     * @param a the attribute set
     * @return the font family, "Monospaced" as the default
     */
    public static String getFontFamily(AttributeSet a) {
        String family = (String) a.getAttribute(FontFamily);
        if (family == null) {
            family = "Monospaced";
        }
        return family;
    }

    /** {@collect.stats}
     * Sets the font attribute.
     *
     * @param a the attribute set
     * @param fam the font
     */
    public static void setFontFamily(MutableAttributeSet a, String fam) {
        a.addAttribute(FontFamily, fam);
    }

    /** {@collect.stats}
     * Gets the font size setting from the attribute list.
     *
     * @param a the attribute set
     * @return the font size, 12 as the default
     */
    public static int getFontSize(AttributeSet a) {
        Integer size = (Integer) a.getAttribute(FontSize);
        if (size != null) {
            return size.intValue();
        }
        return 12;
    }

    /** {@collect.stats}
     * Sets the font size attribute.
     *
     * @param a the attribute set
     * @param s the font size
     */
    public static void setFontSize(MutableAttributeSet a, int s) {
        a.addAttribute(FontSize, new Integer(s));
    }

    /** {@collect.stats}
     * Checks whether the bold attribute is set.
     *
     * @param a the attribute set
     * @return true if set else false
     */
    public static boolean isBold(AttributeSet a) {
        Boolean bold = (Boolean) a.getAttribute(Bold);
        if (bold != null) {
            return bold.booleanValue();
        }
        return false;
    }

    /** {@collect.stats}
     * Sets the bold attribute.
     *
     * @param a the attribute set
     * @param b specifies true/false for setting the attribute
     */
    public static void setBold(MutableAttributeSet a, boolean b) {
        a.addAttribute(Bold, Boolean.valueOf(b));
    }

    /** {@collect.stats}
     * Checks whether the italic attribute is set.
     *
     * @param a the attribute set
     * @return true if set else false
     */
    public static boolean isItalic(AttributeSet a) {
        Boolean italic = (Boolean) a.getAttribute(Italic);
        if (italic != null) {
            return italic.booleanValue();
        }
        return false;
    }

    /** {@collect.stats}
     * Sets the italic attribute.
     *
     * @param a the attribute set
     * @param b specifies true/false for setting the attribute
     */
    public static void setItalic(MutableAttributeSet a, boolean b) {
        a.addAttribute(Italic, Boolean.valueOf(b));
    }

    /** {@collect.stats}
     * Checks whether the underline attribute is set.
     *
     * @param a the attribute set
     * @return true if set else false
     */
    public static boolean isUnderline(AttributeSet a) {
        Boolean underline = (Boolean) a.getAttribute(Underline);
        if (underline != null) {
            return underline.booleanValue();
        }
        return false;
    }

    /** {@collect.stats}
     * Checks whether the strikethrough attribute is set.
     *
     * @param a the attribute set
     * @return true if set else false
     */
    public static boolean isStrikeThrough(AttributeSet a) {
        Boolean strike = (Boolean) a.getAttribute(StrikeThrough);
        if (strike != null) {
            return strike.booleanValue();
        }
        return false;
    }


    /** {@collect.stats}
     * Checks whether the superscript attribute is set.
     *
     * @param a the attribute set
     * @return true if set else false
     */
    public static boolean isSuperscript(AttributeSet a) {
        Boolean superscript = (Boolean) a.getAttribute(Superscript);
        if (superscript != null) {
            return superscript.booleanValue();
        }
        return false;
    }


    /** {@collect.stats}
     * Checks whether the subscript attribute is set.
     *
     * @param a the attribute set
     * @return true if set else false
     */
    public static boolean isSubscript(AttributeSet a) {
        Boolean subscript = (Boolean) a.getAttribute(Subscript);
        if (subscript != null) {
            return subscript.booleanValue();
        }
        return false;
    }


    /** {@collect.stats}
     * Sets the underline attribute.
     *
     * @param a the attribute set
     * @param b specifies true/false for setting the attribute
     */
    public static void setUnderline(MutableAttributeSet a, boolean b) {
        a.addAttribute(Underline, Boolean.valueOf(b));
    }

    /** {@collect.stats}
     * Sets the strikethrough attribute.
     *
     * @param a the attribute set
     * @param b specifies true/false for setting the attribute
     */
    public static void setStrikeThrough(MutableAttributeSet a, boolean b) {
        a.addAttribute(StrikeThrough, Boolean.valueOf(b));
    }

    /** {@collect.stats}
     * Sets the superscript attribute.
     *
     * @param a the attribute set
     * @param b specifies true/false for setting the attribute
     */
    public static void setSuperscript(MutableAttributeSet a, boolean b) {
        a.addAttribute(Superscript, Boolean.valueOf(b));
    }

    /** {@collect.stats}
     * Sets the subscript attribute.
     *
     * @param a the attribute set
     * @param b specifies true/false for setting the attribute
     */
    public static void setSubscript(MutableAttributeSet a, boolean b) {
        a.addAttribute(Subscript, Boolean.valueOf(b));
    }


    /** {@collect.stats}
     * Gets the foreground color setting from the attribute list.
     *
     * @param a the attribute set
     * @return the color, Color.black as the default
     */
    public static Color getForeground(AttributeSet a) {
        Color fg = (Color) a.getAttribute(Foreground);
        if (fg == null) {
            fg = Color.black;
        }
        return fg;
    }

    /** {@collect.stats}
     * Sets the foreground color.
     *
     * @param a the attribute set
     * @param fg the color
     */
    public static void setForeground(MutableAttributeSet a, Color fg) {
        a.addAttribute(Foreground, fg);
    }

    /** {@collect.stats}
     * Gets the background color setting from the attribute list.
     *
     * @param a the attribute set
     * @return the color, Color.black as the default
     */
    public static Color getBackground(AttributeSet a) {
        Color fg = (Color) a.getAttribute(Background);
        if (fg == null) {
            fg = Color.black;
        }
        return fg;
    }

    /** {@collect.stats}
     * Sets the background color.
     *
     * @param a the attribute set
     * @param fg the color
     */
    public static void setBackground(MutableAttributeSet a, Color fg) {
        a.addAttribute(Background, fg);
    }


    // --- paragraph attribute accessors ----------------------------

    /** {@collect.stats}
     * Gets the first line indent setting.
     *
     * @param a the attribute set
     * @return the value, 0 if not set
     */
    public static float getFirstLineIndent(AttributeSet a) {
        Float indent = (Float) a.getAttribute(FirstLineIndent);
        if (indent != null) {
            return indent.floatValue();
        }
        return 0;
    }

    /** {@collect.stats}
     * Sets the first line indent.
     *
     * @param a the attribute set
     * @param i the value
     */
    public static void setFirstLineIndent(MutableAttributeSet a, float i) {
        a.addAttribute(FirstLineIndent, new Float(i));
    }

    /** {@collect.stats}
     * Gets the right indent setting.
     *
     * @param a the attribute set
     * @return the value, 0 if not set
     */
    public static float getRightIndent(AttributeSet a) {
        Float indent = (Float) a.getAttribute(RightIndent);
        if (indent != null) {
            return indent.floatValue();
        }
        return 0;
    }

    /** {@collect.stats}
     * Sets right indent.
     *
     * @param a the attribute set
     * @param i the value
     */
    public static void setRightIndent(MutableAttributeSet a, float i) {
        a.addAttribute(RightIndent, new Float(i));
    }

    /** {@collect.stats}
     * Gets the left indent setting.
     *
     * @param a the attribute set
     * @return the value, 0 if not set
     */
    public static float getLeftIndent(AttributeSet a) {
        Float indent = (Float) a.getAttribute(LeftIndent);
        if (indent != null) {
            return indent.floatValue();
        }
        return 0;
    }

    /** {@collect.stats}
     * Sets left indent.
     *
     * @param a the attribute set
     * @param i the value
     */
    public static void setLeftIndent(MutableAttributeSet a, float i) {
        a.addAttribute(LeftIndent, new Float(i));
    }

    /** {@collect.stats}
     * Gets the line spacing setting.
     *
     * @param a the attribute set
     * @return the value, 0 if not set
     */
    public static float getLineSpacing(AttributeSet a) {
        Float space = (Float) a.getAttribute(LineSpacing);
        if (space != null) {
            return space.floatValue();
        }
        return 0;
    }

    /** {@collect.stats}
     * Sets line spacing.
     *
     * @param a the attribute set
     * @param i the value
     */
    public static void setLineSpacing(MutableAttributeSet a, float i) {
        a.addAttribute(LineSpacing, new Float(i));
    }

    /** {@collect.stats}
     * Gets the space above setting.
     *
     * @param a the attribute set
     * @return the value, 0 if not set
     */
    public static float getSpaceAbove(AttributeSet a) {
        Float space = (Float) a.getAttribute(SpaceAbove);
        if (space != null) {
            return space.floatValue();
        }
        return 0;
    }

    /** {@collect.stats}
     * Sets space above.
     *
     * @param a the attribute set
     * @param i the value
     */
    public static void setSpaceAbove(MutableAttributeSet a, float i) {
        a.addAttribute(SpaceAbove, new Float(i));
    }

    /** {@collect.stats}
     * Gets the space below setting.
     *
     * @param a the attribute set
     * @return the value, 0 if not set
     */
    public static float getSpaceBelow(AttributeSet a) {
        Float space = (Float) a.getAttribute(SpaceBelow);
        if (space != null) {
            return space.floatValue();
        }
        return 0;
    }

    /** {@collect.stats}
     * Sets space below.
     *
     * @param a the attribute set
     * @param i the value
     */
    public static void setSpaceBelow(MutableAttributeSet a, float i) {
        a.addAttribute(SpaceBelow, new Float(i));
    }

    /** {@collect.stats}
     * Gets the alignment setting.
     *
     * @param a the attribute set
     * @return the value <code>StyleConstants.ALIGN_LEFT</code> if not set
     */
    public static int getAlignment(AttributeSet a) {
        Integer align = (Integer) a.getAttribute(Alignment);
        if (align != null) {
            return align.intValue();
        }
        return ALIGN_LEFT;
    }

    /** {@collect.stats}
     * Sets alignment.
     *
     * @param a the attribute set
     * @param align the alignment value
     */
    public static void setAlignment(MutableAttributeSet a, int align) {
        a.addAttribute(Alignment, new Integer(align));
    }

    /** {@collect.stats}
     * Gets the TabSet.
     *
     * @param a the attribute set
     * @return the <code>TabSet</code>
     */
    public static TabSet getTabSet(AttributeSet a) {
        TabSet tabs = (TabSet)a.getAttribute(TabSet);
        // PENDING: should this return a default?
        return tabs;
    }

    /** {@collect.stats}
     * Sets the TabSet.
     *
     * @param a the attribute set.
     * @param tabs the TabSet
     */
    public static void setTabSet(MutableAttributeSet a, TabSet tabs) {
        a.addAttribute(TabSet, tabs);
    }

    // --- privates ---------------------------------------------

    static Object[] keys = {
        NameAttribute, ResolveAttribute, BidiLevel,
        FontFamily, FontSize, Bold, Italic, Underline,
        StrikeThrough, Superscript, Subscript, Foreground,
        Background, ComponentAttribute, IconAttribute,
        FirstLineIndent, LeftIndent, RightIndent, LineSpacing,
        SpaceAbove, SpaceBelow, Alignment, TabSet, Orientation,
        ModelAttribute, ComposedTextAttribute
    };

    StyleConstants(String representation) {
        this.representation = representation;
    }

    private String representation;

    /** {@collect.stats}
     * This is a typesafe enumeration of the <em>well-known</em>
     * attributes that contribute to a paragraph style.  These are
     * aliased by the outer class for general presentation.
     */
    public static class ParagraphConstants extends StyleConstants
        implements AttributeSet.ParagraphAttribute {

        private ParagraphConstants(String representation) {
            super(representation);
        }
    }

    /** {@collect.stats}
     * This is a typesafe enumeration of the <em>well-known</em>
     * attributes that contribute to a character style.  These are
     * aliased by the outer class for general presentation.
     */
    public static class CharacterConstants extends StyleConstants
        implements AttributeSet.CharacterAttribute {

        private CharacterConstants(String representation) {
            super(representation);
        }
    }

    /** {@collect.stats}
     * This is a typesafe enumeration of the <em>well-known</em>
     * attributes that contribute to a color.  These are aliased
     * by the outer class for general presentation.
     */
    public static class ColorConstants extends StyleConstants
        implements AttributeSet.ColorAttribute,  AttributeSet.CharacterAttribute {

        private ColorConstants(String representation) {
            super(representation);
        }
    }

    /** {@collect.stats}
     * This is a typesafe enumeration of the <em>well-known</em>
     * attributes that contribute to a font.  These are aliased
     * by the outer class for general presentation.
     */
    public static class FontConstants extends StyleConstants
        implements AttributeSet.FontAttribute, AttributeSet.CharacterAttribute {

        private FontConstants(String representation) {
            super(representation);
        }
    }


}
