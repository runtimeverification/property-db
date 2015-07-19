/*
 * Copyright (c) 1995, 2013, Oracle and/or its affiliates. All rights reserved.
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
 * This interface defines a factory for content handlers. An
 * implementation of this interface should map a MIME type into an
 * instance of {@code ContentHandler}.
 * <p>
 * This interface is used by the {@code URLStreamHandler} class
 * to create a {@code ContentHandler} for a MIME type.
 * {@description.close}
 *
 * @author  James Gosling
 * @see     java.net.ContentHandler
 * @see     java.net.URLStreamHandler
 * @since   JDK1.0
 */
public interface ContentHandlerFactory {
    /** {@collect.stats} 
     * {@description.open}
     * Creates a new {@code ContentHandler} to read an object from
     * a {@code URLStreamHandler}.
     * {@description.close}
     *
     * @param   mimetype   the MIME type for which a content handler is desired.

     * @return  a new {@code ContentHandler} to read an object from a
     *          {@code URLStreamHandler}.
     * @see     java.net.ContentHandler
     * @see     java.net.URLStreamHandler
     */
    ContentHandler createContentHandler(String mimetype);
}
