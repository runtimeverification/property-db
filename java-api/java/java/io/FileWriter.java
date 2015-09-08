/*
 * Copyright (c) 1996, 2001, Oracle and/or its affiliates. All rights reserved.
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

package java.io;


/** {@collect.stats}
 *      
* {@description.open}
     * Convenience class for writing character files.  The constructors of this
 * class assume that the default character encoding and the default byte-buffer
 * size are acceptable.  To specify these values yourself, construct an
 * OutputStreamWriter on a FileOutputStream.

     * {@description.close} *
 *      
* {@property.open}
     * <p>Whether or not a file is available or may be created depends upon the
 * underlying platform.  Some platforms, in particular, allow a file to be
 * opened for writing by only one <tt>FileWriter</tt> (or other file-writing
 * object) at a time.  In such situations the constructors in this class
 * will fail if the file involved is already open.

     * {@property.close} *
 *      
* {@description.open}
     * <p><code>FileWriter</code> is meant for writing streams of characters.
 * For writing streams of raw bytes, consider using a
 * <code>FileOutputStream</code>.

     * {@description.close} *
 * @see OutputStreamWriter
 * @see FileOutputStream
 *
 * @author      Mark Reinhold
 * @since       JDK1.1
 */

public class FileWriter extends OutputStreamWriter {

    /** {@collect.stats}
     *      
* {@description.open}
     * Constructs a FileWriter object given a file name.

     * {@description.close}     *
     * @param fileName  String The system-dependent filename.
     * @throws IOException  if the named file exists but is a directory rather
     *                  than a regular file, does not exist but cannot be
     *                  created, or cannot be opened for any other reason
     */
    public FileWriter(String fileName) throws IOException {
        super(new FileOutputStream(fileName));
    }

    /** {@collect.stats}
     *      
* {@description.open}
     * Constructs a FileWriter object given a file name with a boolean
     * indicating whether or not to append the data written.

     * {@description.close}     *
     * @param fileName  String The system-dependent filename.
     * @param append    boolean if <code>true</code>, then data will be written
     *                  to the end of the file rather than the beginning.
     * @throws IOException  if the named file exists but is a directory rather
     *                  than a regular file, does not exist but cannot be
     *                  created, or cannot be opened for any other reason
     */
    public FileWriter(String fileName, boolean append) throws IOException {
        super(new FileOutputStream(fileName, append));
    }

    /** {@collect.stats}
     *      
* {@description.open}
     * Constructs a FileWriter object
     * {@description.close} given a File object.
     *
     * @param file  a File object to write to.
     * @throws IOException  if the file exists but is a directory rather than
     *                  a regular file, does not exist but cannot be created,
     *                  or cannot be opened for any other reason
     */
    public FileWriter(File file) throws IOException {
        super(new FileOutputStream(file));
    }

    /** {@collect.stats}
     *      
* {@description.open}
     * Constructs a FileWriter object given a File object. If the second
     * argument is <code>true</code>, then bytes will be written to the end
     * of the file rather than the beginning.

     * {@description.close}     *
     * @param file  a File object to write to
     * @param     append    if <code>true</code>, then bytes will be written
     *                      to the end of the file rather than the beginning
     * @throws IOException  if the file exists but is a directory rather than
     *                  a regular file, does not exist but cannot be created,
     *                  or cannot be opened for any other reason
     * @since 1.4
     */
    public FileWriter(File file, boolean append) throws IOException {
        super(new FileOutputStream(file, append));
    }

    /** {@collect.stats}
     *      
* {@description.open}
     * Constructs a FileWriter object associated with a file descriptor.

     * {@description.close}     *
     * @param fd  FileDescriptor object to write to.
     */
    public FileWriter(FileDescriptor fd) {
        super(new FileOutputStream(fd));
    }

}
