/*
 * Copyright (c) 1996, 1999, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package java.util.zip;

/** {@collect.stats}
 *      
* {@description.open}
     * An interface representing a data checksum.

     * {@description.close} *
 * @author      David Connelly
 */
public
interface Checksum {
    /** {@collect.stats}
     *      
* {@description.open}
     * Updates the current checksum with the specified byte.

     * {@description.close}     *
     * @param b the byte to update the checksum with
     */
    public void update(int b);

    /** {@collect.stats}
     *      
* {@description.open}
     * Updates the current checksum with the specified array of bytes.

     * {@description.close}     * @param b the byte array to update the checksum with
     * @param off the start offset of the data
     * @param len the number of bytes to use for the update
     */
    public void update(byte[] b, int off, int len);

    /** {@collect.stats}
     *      
* {@description.open}
     * Returns the current checksum value.

     * {@description.close}     * @return the current checksum value
     */
    public long getValue();

    /** {@collect.stats}
     *      
* {@description.open}
     * Resets the checksum to its initial value.

     * {@description.close}     */
    public void reset();
}
