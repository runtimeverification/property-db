/*
 * Copyright (c) 1996, 1999, Oracle and/or its affiliates. All rights reserved.
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
 * An interface representing a data checksum.
 * {@description.close}
 *
 * @author      David Connelly
 */
public
interface Checksum {
    /** {@collect.stats} 
     * {@description.open}
     * Updates the current checksum with the specified byte.
     * {@description.close}
     *
     * @param b the byte to update the checksum with
     */
    public void update(int b);

    /** {@collect.stats} 
     * {@description.open}
     * Updates the current checksum with the specified array of bytes.
     * {@description.close}
     * @param b the byte array to update the checksum with
     * @param off the start offset of the data
     * @param len the number of bytes to use for the update
     */
    public void update(byte[] b, int off, int len);

    /** {@collect.stats} 
     * {@description.open}
     * Returns the current checksum value.
     * {@description.close}
     * @return the current checksum value
     */
    public long getValue();

    /** {@collect.stats} 
     * {@description.open}
     * Resets the checksum to its initial value.
     * {@description.close}
     */
    public void reset();
}
