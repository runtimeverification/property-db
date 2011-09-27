/*
 * Copyright (c) 1998, 2005, Oracle and/or its affiliates. All rights reserved.
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

package java.io;


/**
 * {@description.open}
 * Package-private abstract class for the local filesystem abstraction.
 * {@description.close}
 */

abstract class FileSystem {

    /**
     * {@description.open}
     * Return the FileSystem object representing this platform's local
     * filesystem.
     * {@description.close}
     */
    public static native FileSystem getFileSystem();


    /* -- Normalization and construction -- */

    /**
     * {@description.open}
     * Return the local filesystem's name-separator character.
     * {@description.close}
     */
    public abstract char getSeparator();

    /**
     * {@description.open}
     * Return the local filesystem's path-separator character.
     * {@description.close}
     */
    public abstract char getPathSeparator();

    /**
     * {@description.open}
     * Convert the given pathname string to normal form.  If the string is
     * already in normal form then it is simply returned.
     * {@description.close}
     */
    public abstract String normalize(String path);

    /**
     * {@description.open}
     * Compute the length of this pathname string's prefix.  The pathname
     * string must be in normal form.
     * {@description.close}
     */
    public abstract int prefixLength(String path);

    /**
     * {@description.open}
     * Resolve the child pathname string against the parent.
     * Both strings must be in normal form, and the result
     * will be in normal form.
     * {@description.close}
     */
    public abstract String resolve(String parent, String child);

    /**
     * {@description.open}
     * Return the parent pathname string to be used when the parent-directory
     * argument in one of the two-argument File constructors is the empty
     * pathname.
     * {@description.close}
     */
    public abstract String getDefaultParent();

    /**
     * {@description.open}
     * Post-process the given URI path string if necessary.  This is used on
     * win32, e.g., to transform "/c:/foo" into "c:/foo".  The path string
     * still has slash separators; code in the File class will translate them
     * after this method returns.
     * {@description.close}
     */
    public abstract String fromURIPath(String path);


    /* -- Path operations -- */

    /**
     * {@description.open}
     * Tell whether or not the given abstract pathname is absolute.
     * {@description.close}
     */
    public abstract boolean isAbsolute(File f);

    /**
     * {@description.open}
     * Resolve the given abstract pathname into absolute form.  Invoked by the
     * getAbsolutePath and getCanonicalPath methods in the File class.
     * {@description.close}
     */
    public abstract String resolve(File f);

    public abstract String canonicalize(String path) throws IOException;


    /* -- Attribute accessors -- */

    /* Constants for simple boolean attributes */
    public static final int BA_EXISTS    = 0x01;
    public static final int BA_REGULAR   = 0x02;
    public static final int BA_DIRECTORY = 0x04;
    public static final int BA_HIDDEN    = 0x08;

    /**
     * {@description.open}
     * Return the simple boolean attributes for the file or directory denoted
     * by the given abstract pathname, or zero if it does not exist or some
     * other I/O error occurs.
     * {@description.close}
     */
    public abstract int getBooleanAttributes(File f);

    public static final int ACCESS_READ    = 0x04;
    public static final int ACCESS_WRITE   = 0x02;
    public static final int ACCESS_EXECUTE = 0x01;

    /**
     * {@description.open}
     * Check whether the file or directory denoted by the given abstract
     * pathname may be accessed by this process.  The second argument specifies
     * which access, ACCESS_READ, ACCESS_WRITE or ACCESS_EXECUTE, to check.
     * Return false if access is denied or an I/O error occurs
     * {@description.close}
     */
    public abstract boolean checkAccess(File f, int access);
    /**
     * {@description.open}
     * Set on or off the access permission (to owner only or to all) to the file
     * or directory denoted by the given abstract pathname, based on the parameters
     * enable, access and oweronly.
     * {@description.close}
     */
    public abstract boolean setPermission(File f, int access, boolean enable, boolean owneronly);

    /**
     * {@description.open}
     * Return the time at which the file or directory denoted by the given
     * abstract pathname was last modified, or zero if it does not exist or
     * some other I/O error occurs.
     * {@description.close}
     */
    public abstract long getLastModifiedTime(File f);

    /**
     * {@description.open}
     * Return the length in bytes of the file denoted by the given abstract
     * pathname, or zero if it does not exist, is a directory, or some other
     * I/O error occurs.
     * {@description.close}
     */
    public abstract long getLength(File f);


    /* -- File operations -- */

    /**
     * {@description.open}
     * Create a new empty file with the given pathname.  Return
     * <code>true</code> if the file was created and <code>false</code> if a
     * file or directory with the given pathname already exists.  Throw an
     * IOException if an I/O error occurs.
     * {@description.close}
     */
    public abstract boolean createFileExclusively(String pathname)
        throws IOException;

    /**
     * {@description.open}
     * Delete the file or directory denoted by the given abstract pathname,
     * returning <code>true</code> if and only if the operation succeeds.
     * {@description.close}
     */
    public abstract boolean delete(File f);

    /**
     * {@description.open}
     * List the elements of the directory denoted by the given abstract
     * pathname.  Return an array of strings naming the elements of the
     * directory if successful; otherwise, return <code>null</code>.
     * {@description.close}
     */
    public abstract String[] list(File f);

    /**
     * {@description.open}
     * Create a new directory denoted by the given abstract pathname,
     * returning <code>true</code> if and only if the operation succeeds.
     * {@description.close}
     */
    public abstract boolean createDirectory(File f);

    /**
     * {@description.open}
     * Rename the file or directory denoted by the first abstract pathname to
     * the second abstract pathname, returning <code>true</code> if and only if
     * the operation succeeds.
     * {@description.close}
     */
    public abstract boolean rename(File f1, File f2);

    /**
     * {@description.open}
     * Set the last-modified time of the file or directory denoted by the
     * given abstract pathname, returning <code>true</code> if and only if the
     * operation succeeds.
     * {@description.close}
     */
    public abstract boolean setLastModifiedTime(File f, long time);

    /**
     * {@description.open}
     * Mark the file or directory denoted by the given abstract pathname as
     * read-only, returning <code>true</code> if and only if the operation
     * succeeds.
     * {@description.close}
     */
    public abstract boolean setReadOnly(File f);


    /* -- Filesystem interface -- */

    /**
     * {@description.open}
     * List the available filesystem roots.
     * {@description.close}
     */
    public abstract File[] listRoots();

    /* -- Disk usage -- */
    public static final int SPACE_TOTAL  = 0;
    public static final int SPACE_FREE   = 1;
    public static final int SPACE_USABLE = 2;

    public abstract long getSpace(File f, int t);

    /* -- Basic infrastructure -- */

    /**
     * {@description.open}
     * Compare two abstract pathnames lexicographically.
     * {@description.close}
     */
    public abstract int compare(File f1, File f2);

    /**
     * {@description.open}
     * Compute the hash code of an abstract pathname.
     * {@description.close}
     */
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
