/*
 * Copyright (c) 1998, 2013, Oracle and/or its affiliates. All rights reserved.
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

import java.lang.annotation.Native;

/** {@collect.stats}
 *      
* {@description.open}
     * Package-private abstract class for the local filesystem abstraction.

     * {@description.close} */

abstract class FileSystem {

    /* -- Normalization and construction -- */

    /** {@collect.stats}
     *      
* {@description.open}
     * Return the local filesystem's name-separator character.

     * {@description.close}     */
    public abstract char getSeparator();

    /** {@collect.stats}
     *      
* {@description.open}
     * Return the local filesystem's path-separator character.

     * {@description.close}     */
    public abstract char getPathSeparator();

    /** {@collect.stats}
     *      
* {@description.open}
     * Convert the given pathname string to normal form.  If the string is
     * already in normal form then it is simply returned.

     * {@description.close}     */
    public abstract String normalize(String path);

    /** {@collect.stats}
     *      
* {@description.open}
     * Compute the length of this pathname string's prefix.  The pathname
     * string must be in normal form.

     * {@description.close}     */
    public abstract int prefixLength(String path);

    /** {@collect.stats}
     *      
* {@description.open}
     * Resolve the child pathname string against the parent.
     * Both strings must be in normal form,
     *  and the result
     * will be in normal form.
    * {@description.close} 
	*/
    public abstract String resolve(String parent, String child);

    /** {@collect.stats}
     *      
* {@description.open}
     * Return the parent pathname string to be used when the parent-directory
     * argument in one of the two-argument File constructors is the empty
     * pathname.

     * {@description.close}     */
    public abstract String getDefaultParent();

    /** {@collect.stats}
     *      
* {@description.open}
     * Post-process the given URI path string if necessary.  This is used on
     * win32, e.g., to transform "/c:/foo" into "c:/foo".  The path string
     * still has slash separators; code in the File class will translate them
     * after this method returns.

     * {@description.close}     */
    public abstract String fromURIPath(String path);


    /* -- Path operations -- */

    /** {@collect.stats}
     *      
* {@description.open}
     * Tell whether or not the given abstract pathname is absolute.

     * {@description.close}     */
    public abstract boolean isAbsolute(File f);

    /** {@collect.stats}
     *      
* {@description.open}
     * Resolve the given abstract pathname into absolute form.  Invoked by the
     * getAbsolutePath and getCanonicalPath methods in the File class.

     * {@description.close}     */
    public abstract String resolve(File f);

    public abstract String canonicalize(String path) throws IOException;


    /* -- Attribute accessors -- */

    /* Constants for simple boolean attributes */
    @Native public static final int BA_EXISTS    = 0x01;
    @Native public static final int BA_REGULAR   = 0x02;
    @Native public static final int BA_DIRECTORY = 0x04;
    @Native public static final int BA_HIDDEN    = 0x08;

    /** {@collect.stats}
     *      
* {@description.open}
     * Return the simple boolean attributes for the file or directory denoted
     * by the given abstract pathname, or zero if it does not exist or some
     * other I/O error occurs.

     * {@description.close}     */
    public abstract int getBooleanAttributes(File f);

    @Native public static final int ACCESS_READ    = 0x04;
    @Native public static final int ACCESS_WRITE   = 0x02;
    @Native public static final int ACCESS_EXECUTE = 0x01;

    /** {@collect.stats}
     *      
* {@description.open}
     * Check whether the file or directory denoted by the given abstract
     * pathname may be accessed by this process.  The second argument specifies
     * which access, ACCESS_READ, ACCESS_WRITE or ACCESS_EXECUTE, to check.
     * Return false if access is denied or an I/O error occurs

     * {@description.close}     */
    public abstract boolean checkAccess(File f, int access);
    /** {@collect.stats}
     *      
* {@description.open}
     * Set on or off the access permission (to owner only or to all) to the file
     * or directory denoted by the given abstract pathname, based on the parameters
     * enable, access and oweronly.

     * {@description.close}     */
    public abstract boolean setPermission(File f, int access, boolean enable, boolean owneronly);

    /** {@collect.stats}
     *      
* {@description.open}
     * Return the time at which the file or directory denoted by the given
     * abstract pathname was last modified, or zero if it does not exist or
     * some other I/O error occurs.

     * {@description.close}     */
    public abstract long getLastModifiedTime(File f);

    /** {@collect.stats}
     *      
* {@description.open}
     * Return the length in bytes of the file denoted by the given abstract
     * pathname, or zero if it does not exist, is a directory, or some other
     * I/O error occurs.

     * {@description.close}     */
    public abstract long getLength(File f);


    /* -- File operations -- */

    /** {@collect.stats}
     *      
* {@description.open}
     * Create a new empty file with the given pathname.  Return
     * <code>true</code> if the file was created and <code>false</code> if a
     * file or directory with the given pathname already exists.  Throw an
     * IOException if an I/O error occurs.

     * {@description.close}     */
    public abstract boolean createFileExclusively(String pathname)
        throws IOException;

    /** {@collect.stats}
     *      
* {@description.open}
     * Delete the file or directory denoted by the given abstract pathname,
     * returning <code>true</code> if and only if the operation succeeds.

     * {@description.close}     */
    public abstract boolean delete(File f);

    /** {@collect.stats}
     *      
* {@description.open}
     * List the elements of the directory denoted by the given abstract
     * pathname.  Return an array of strings naming the elements of the
     * directory if successful; otherwise, return <code>null</code>.

     * {@description.close}     */
    public abstract String[] list(File f);

    /** {@collect.stats}
     *      
* {@description.open}
     * Create a new directory denoted by the given abstract pathname,
     * returning <code>true</code> if and only if the operation succeeds.

     * {@description.close}     */
    public abstract boolean createDirectory(File f);

    /** {@collect.stats}
     *      
* {@description.open}
     * Rename the file or directory denoted by the first abstract pathname to
     * the second abstract pathname, returning <code>true</code> if and only if
     * the operation succeeds.

     * {@description.close}     */
    public abstract boolean rename(File f1, File f2);

    /** {@collect.stats}
     *      
* {@description.open}
     * Set the last-modified time of the file or directory denoted by the
     * given abstract pathname, returning <code>true</code> if and only if the
     * operation succeeds.

     * {@description.close}     */
    public abstract boolean setLastModifiedTime(File f, long time);

    /** {@collect.stats}
     *      
* {@description.open}
     * Mark the file or directory denoted by the given abstract pathname as
     * read-only, returning <code>true</code> if and only if the operation
     * succeeds.

     * {@description.close}     */
    public abstract boolean setReadOnly(File f);


    /* -- Filesystem interface -- */

    /** {@collect.stats}
     *      
* {@description.open}
     * List the available filesystem roots.

     * {@description.close}     */
    public abstract File[] listRoots();

    /* -- Disk usage -- */
    @Native public static final int SPACE_TOTAL  = 0;
    @Native public static final int SPACE_FREE   = 1;
    @Native public static final int SPACE_USABLE = 2;

    public abstract long getSpace(File f, int t);

    /* -- Basic infrastructure -- */

    /** {@collect.stats}
     *      
* {@description.open}
     * Compare two abstract pathnames lexicographically.

     * {@description.close}     */
    public abstract int compare(File f1, File f2);

    /** {@collect.stats}
     *      
* {@description.open}
     * Compute the hash code of an abstract pathname.

     * {@description.close}     */
    public abstract int hashCode(File f);

    // Flags for enabling/disabling performance optimizations for file
    // name canonicalization
    static boolean useCanonCaches      = true;
    static boolean useCanonPrefixCache = true;

    private static boolean getBooleanProperty(String prop, boolean defaultVal) {
        String val = System.getProperty(prop);
        if (val == null) return defaultVal;
        if (val.equalsIgnoreCase("true")) {
            return true;
        } else {
            return false;
        }
    }

    static {
        useCanonCaches      = getBooleanProperty("sun.io.useCanonCaches",
                                                 useCanonCaches);
        useCanonPrefixCache = getBooleanProperty("sun.io.useCanonPrefixCache",
                                                 useCanonPrefixCache);
    }
}
