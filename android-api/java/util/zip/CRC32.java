/*
 * Copyright (c) 1996, 2005, Oracle and/or its affiliates. All rights reserved.
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

package java.util.zip;

/** {@collect.stats} 
 * {@description.open}
 * A class that can be used to compute the CRC-32 of a data stream.
 * {@description.close}
 *
 * @see         Checksum
 * @author      David Connelly
 */
public
class CRC32 implements Checksum {
    private int crc;

    /** {@collect.stats} 
     * {@description.open}
     * Creates a new CRC32 object.
     * {@description.close}
     */
    public CRC32() {
    }


    /** {@collect.stats} 
     * {@description.open}
     * Updates CRC-32 with specified byte.
     * {@description.close}
     */
    public void update(int b) {
        crc = update(crc, b);
    }

    /** {@collect.stats} 
     * {@description.open}
     * Updates CRC-32 with specified array of bytes.
     * {@description.close}
     */
    public void update(byte[] b, int off, int len) {
        if (b == null) {
            throw new NullPointerException();
        }
        if (off < 0 || len < 0 || off > b.length - len) {
            throw new ArrayIndexOutOfBoundsException();
        }
        crc = updateBytes(crc, b, off, len);
    }

    /** {@collect.stats} 
     * {@description.open}
     * Updates checksum with specified array of bytes.
     * {@description.close}
     *
     * @param b the array of bytes to update the checksum with
     */
    public void update(byte[] b) {
        crc = updateBytes(crc, b, 0, b.length);
    }

    /** {@collect.stats} 
     * {@description.open}
     * Resets CRC-32 to initial value.
     * {@description.close}
     */
    public void reset() {
        crc = 0;
    }

    /** {@collect.stats} 
     * {@description.open}
     * Returns CRC-32 value.
     * {@description.close}
     */
    public long getValue() {
        return (long)crc & 0xffffffffL;
    }

    private native static int update(int crc, int b);
    private native static int updateBytes(int crc, byte[] b, int off, int len);
}
