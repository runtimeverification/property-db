/*
 * Copyright (c) 1997, 1999, Oracle and/or its affiliates. All rights reserved.
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

package java.awt.font;

/** {@collect.stats} 
 * The <code>OpenType</code> interface represents OpenType and
 * TrueType fonts.  This interface makes it possible to obtain
 * <i>sfnt</i> tables from the font.  A particular
 * <code>Font</code> object can implement this interface.
 * <p>
 * For more information on TrueType fonts, see the
 * Apple TrueType Reference Manual
 * ( <a href="http://fonts.apple.com/TTRefMan/index.html">http://fonts.apple.com/TTRefMan/index.html</a> ).
 */
public interface OpenType {

  /* 51 tag types so far */

  /** {@collect.stats} 
   * Character to glyph mapping.  Table tag "cmap" in the Open
   * Type Specification.
   */
  public final static int       TAG_CMAP        = 0x636d6170;

  /** {@collect.stats} 
   * Font header.  Table tag "head" in the Open
   * Type Specification.
   */
  public final static int       TAG_HEAD        = 0x68656164;

  /** {@collect.stats} 
   * Naming table.  Table tag "name" in the Open
   * Type Specification.
   */
  public final static int       TAG_NAME        = 0x6e616d65;

  /** {@collect.stats} 
   * Glyph data.  Table tag "glyf" in the Open
   * Type Specification.
   */
  public final static int       TAG_GLYF        = 0x676c7966;

  /** {@collect.stats} 
   * Maximum profile.  Table tag "maxp" in the Open
   * Type Specification.
   */
  public final static int       TAG_MAXP        = 0x6d617870;

  /** {@collect.stats} 
   * CVT preprogram.  Table tag "prep" in the Open
   * Type Specification.
   */
  public final static int       TAG_PREP        = 0x70726570;

  /** {@collect.stats} 
   * Horizontal metrics.  Table tag "hmtx" in the Open
   * Type Specification.
   */
  public final static int       TAG_HMTX        = 0x686d7478;

  /** {@collect.stats} 
   * Kerning.  Table tag "kern" in the Open
   * Type Specification.
   */
  public final static int       TAG_KERN        = 0x6b65726e;

  /** {@collect.stats} 
   * Horizontal device metrics.  Table tag "hdmx" in the Open
   * Type Specification.
   */
  public final static int       TAG_HDMX        = 0x68646d78;

  /** {@collect.stats} 
   * Index to location.  Table tag "loca" in the Open
   * Type Specification.
   */
  public final static int       TAG_LOCA        = 0x6c6f6361;

  /** {@collect.stats} 
   * PostScript Information.  Table tag "post" in the Open
   * Type Specification.
   */
  public final static int       TAG_POST        = 0x706f7374;

  /** {@collect.stats} 
   * OS/2 and Windows specific metrics.  Table tag "OS/2"
   * in the Open Type Specification.
   */
  public final static int       TAG_OS2 = 0x4f532f32;

  /** {@collect.stats} 
   * Control value table.  Table tag "cvt "
   * in the Open Type Specification.
   */
  public final static int       TAG_CVT = 0x63767420;

  /** {@collect.stats} 
   * Grid-fitting and scan conversion procedure.  Table tag
   * "gasp" in the Open Type Specification.
   */
  public final static int       TAG_GASP        = 0x67617370;

  /** {@collect.stats} 
   * Vertical device metrics.  Table tag "VDMX" in the Open
   * Type Specification.
   */
  public final static int       TAG_VDMX        = 0x56444d58;

  /** {@collect.stats} 
   * Vertical metrics.  Table tag "vmtx" in the Open
   * Type Specification.
   */
  public final static int       TAG_VMTX        = 0x766d7478;

  /** {@collect.stats} 
   * Vertical metrics header.  Table tag "vhea" in the Open
   * Type Specification.
   */
  public final static int       TAG_VHEA        = 0x76686561;

  /** {@collect.stats} 
   * Horizontal metrics header.  Table tag "hhea" in the Open
   * Type Specification.
   */
  public final static int       TAG_HHEA        = 0x68686561;

  /** {@collect.stats} 
   * Adobe Type 1 font data.  Table tag "typ1" in the Open
   * Type Specification.
   */
  public final static int       TAG_TYP1        = 0x74797031;

  /** {@collect.stats} 
   * Baseline table.  Table tag "bsln" in the Open
   * Type Specification.
   */
  public final static int       TAG_BSLN        = 0x62736c6e;

  /** {@collect.stats} 
   * Glyph substitution.  Table tag "GSUB" in the Open
   * Type Specification.
   */
  public final static int       TAG_GSUB        = 0x47535542;

  /** {@collect.stats} 
   * Digital signature.  Table tag "DSIG" in the Open
   * Type Specification.
   */
  public final static int       TAG_DSIG        = 0x44534947;

  /** {@collect.stats} 
   * Font program.   Table tag "fpgm" in the Open
   * Type Specification.
   */
  public final static int       TAG_FPGM        = 0x6670676d;

  /** {@collect.stats} 
   * Font variation.   Table tag "fvar" in the Open
   * Type Specification.
   */
  public final static int       TAG_FVAR        = 0x66766172;

  /** {@collect.stats} 
   * Glyph variation.  Table tag "gvar" in the Open
   * Type Specification.
   */
  public final static int       TAG_GVAR        = 0x67766172;

  /** {@collect.stats} 
   * Compact font format (Type1 font).  Table tag
   * "CFF " in the Open Type Specification.
   */
  public final static int       TAG_CFF = 0x43464620;

  /** {@collect.stats} 
   * Multiple master supplementary data.  Table tag
   * "MMSD" in the Open Type Specification.
   */
  public final static int       TAG_MMSD        = 0x4d4d5344;

  /** {@collect.stats} 
   * Multiple master font metrics.  Table tag
   * "MMFX" in the Open Type Specification.
   */
  public final static int       TAG_MMFX        = 0x4d4d4658;

  /** {@collect.stats} 
   * Baseline data.  Table tag "BASE" in the Open
   * Type Specification.
   */
  public final static int       TAG_BASE        = 0x42415345;

  /** {@collect.stats} 
   * Glyph definition.  Table tag "GDEF" in the Open
   * Type Specification.
   */
  public final static int       TAG_GDEF        = 0x47444546;

  /** {@collect.stats} 
   * Glyph positioning.  Table tag "GPOS" in the Open
   * Type Specification.
   */
  public final static int       TAG_GPOS        = 0x47504f53;

  /** {@collect.stats} 
   * Justification.  Table tag "JSTF" in the Open
   * Type Specification.
   */
  public final static int       TAG_JSTF        = 0x4a535446;

  /** {@collect.stats} 
   * Embedded bitmap data.  Table tag "EBDT" in the Open
   * Type Specification.
   */
  public final static int       TAG_EBDT        = 0x45424454;

  /** {@collect.stats} 
   * Embedded bitmap location.  Table tag "EBLC" in the Open
   * Type Specification.
   */
  public final static int       TAG_EBLC        = 0x45424c43;

  /** {@collect.stats} 
   * Embedded bitmap scaling.  Table tag "EBSC" in the Open
   * Type Specification.
   */
  public final static int       TAG_EBSC        = 0x45425343;

  /** {@collect.stats} 
   * Linear threshold.  Table tag "LTSH" in the Open
   * Type Specification.
   */
  public final static int       TAG_LTSH        = 0x4c545348;

  /** {@collect.stats} 
   * PCL 5 data.  Table tag "PCLT" in the Open
   * Type Specification.
   */
  public final static int       TAG_PCLT        = 0x50434c54;

  /** {@collect.stats} 
   * Accent attachment.  Table tag "acnt" in the Open
   * Type Specification.
   */
  public final static int       TAG_ACNT        = 0x61636e74;

  /** {@collect.stats} 
   * Axis variaiton.  Table tag "avar" in the Open
   * Type Specification.
   */
  public final static int       TAG_AVAR        = 0x61766172;

  /** {@collect.stats} 
   * Bitmap data.  Table tag "bdat" in the Open
   * Type Specification.
   */
  public final static int       TAG_BDAT        = 0x62646174;

  /** {@collect.stats} 
   * Bitmap location.  Table tag "bloc" in the Open
   * Type Specification.
   */
  public final static int       TAG_BLOC        = 0x626c6f63;

   /** {@collect.stats} 
    * CVT variation.  Table tag "cvar" in the Open
    * Type Specification.
    */
  public final static int       TAG_CVAR        = 0x63766172;

  /** {@collect.stats} 
   * Feature name.  Table tag "feat" in the Open
    * Type Specification.
   */
  public final static int       TAG_FEAT        = 0x66656174;

  /** {@collect.stats} 
   * Font descriptors.  Table tag "fdsc" in the Open
   * Type Specification.
   */
  public final static int       TAG_FDSC        = 0x66647363;

  /** {@collect.stats} 
   * Font metrics.  Table tag "fmtx" in the Open
   * Type Specification.
   */
  public final static int       TAG_FMTX        = 0x666d7478;

  /** {@collect.stats} 
   * Justification.  Table tag "just" in the Open
   * Type Specification.
   */
  public final static int       TAG_JUST        = 0x6a757374;

  /** {@collect.stats} 
   * Ligature caret.   Table tag "lcar" in the Open
   * Type Specification.
   */
  public final static int       TAG_LCAR        = 0x6c636172;

  /** {@collect.stats} 
   * Glyph metamorphosis.  Table tag "mort" in the Open
   * Type Specification.
   */
  public final static int       TAG_MORT        = 0x6d6f7274;

  /** {@collect.stats} 
   * Optical bounds.  Table tag "opbd" in the Open
   * Type Specification.
   */
  public final static int       TAG_OPBD        = 0x6d6f7274;

  /** {@collect.stats} 
   * Glyph properties.  Table tag "prop" in the Open
   * Type Specification.
   */
  public final static int       TAG_PROP        = 0x70726f70;

  /** {@collect.stats} 
   * Tracking.  Table tag "trak" in the Open
   * Type Specification.
   */
  public final static int       TAG_TRAK        = 0x7472616b;

  /** {@collect.stats} 
   * Returns the version of the <code>OpenType</code> font.
   * 1.0 is represented as 0x00010000.
   * @return the version of the <code>OpenType</code> font.
   */
  public int getVersion();

  /** {@collect.stats} 
   * Returns the table as an array of bytes for a specified tag.
   * Tags for sfnt tables include items like <i>cmap</i>,
   * <i>name</i> and <i>head</i>.  The <code>byte</code> array
   * returned is a copy of the font data in memory.
   * @param     sfntTag a four-character code as a 32-bit integer
   * @return a <code>byte</code> array that is the table that
   * contains the font data corresponding to the specified
   * tag.
   */
  public byte[] getFontTable(int sfntTag);

  /** {@collect.stats} 
   * Returns the table as an array of bytes for a specified tag.
   * Tags for sfnt tables include items like <i>cmap</i>,
   * <i>name</i> and <i>head</i>.  The byte array returned is a
   * copy of the font data in memory.
   * @param     strSfntTag a four-character code as a
   *            <code>String</code>
   * @return a <code>byte</code> array that is the table that
   * contains the font data corresponding to the specified
   * tag.
   */
  public byte[] getFontTable(String strSfntTag);

  /** {@collect.stats} 
   * Returns a subset of the table as an array of bytes
   * for a specified tag.  Tags for sfnt tables include
   * items like <i>cmap</i>, <i>name</i> and <i>head</i>.
   * The byte array returned is a copy of the font data in
   * memory.
   * @param     sfntTag a four-character code as a 32-bit integer
   * @param     offset index of first byte to return from table
   * @param     count number of bytes to return from table
   * @return a subset of the table corresponding to
   *            <code>sfntTag</code> and containing the bytes
   *            starting at <code>offset</code> byte and including
   *            <code>count</code> bytes.
   */
  public byte[] getFontTable(int sfntTag, int offset, int count);

  /** {@collect.stats} 
   * Returns a subset of the table as an array of bytes
   * for a specified tag.  Tags for sfnt tables include items
   * like <i>cmap</i>, <i>name</i> and <i>head</i>. The
   * <code>byte</code> array returned is a copy of the font
   * data in memory.
   * @param     strSfntTag a four-character code as a
   * <code>String</code>
   * @param     offset index of first byte to return from table
   * @param     count  number of bytes to return from table
   * @return a subset of the table corresponding to
   *            <code>strSfntTag</code> and containing the bytes
   *            starting at <code>offset</code> byte and including
   *            <code>count</code> bytes.
   */
  public byte[] getFontTable(String strSfntTag, int offset, int count);

  /** {@collect.stats} 
   * Returns the size of the table for a specified tag. Tags for sfnt
   * tables include items like <i>cmap</i>, <i>name</i> and <i>head</i>.
   * @param     sfntTag a four-character code as a 32-bit integer
   * @return the size of the table corresponding to the specified
   * tag.
   */
  public int getFontTableSize(int sfntTag);

  /** {@collect.stats} 
   * Returns the size of the table for a specified tag. Tags for sfnt
   * tables include items like <i>cmap</i>, <i>name</i> and <i>head</i>.
   * @param     strSfntTag a four-character code as a
   * <code>String</code>
   * @return the size of the table corresponding to the specified tag.
   */
  public int getFontTableSize(String strSfntTag);


}
