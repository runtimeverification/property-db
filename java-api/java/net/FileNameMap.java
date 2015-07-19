/*
 * Copyright (c) 1996, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.net;

/** {@collect.stats} 
 * {@description.open}
 * A simple interface which provides a mechanism to map
 * between a file name and a MIME type string.
 * {@description.close}
 *
 * @author  Steven B. Byrne
 * @since   JDK1.1
 */
public interface FileNameMap {

    /** {@collect.stats} 
     * {@description.open}
     * Gets the MIME type for the specified file name.
     * {@description.close}
     * @param fileName the specified file name
     * @return a {@code String} indicating the MIME
     * type for the specified file name.
     */
    public String getContentTypeFor(String fileName);
}