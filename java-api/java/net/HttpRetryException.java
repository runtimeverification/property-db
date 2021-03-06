/*
 * Copyright (c) 2004, Oracle and/or its affiliates. All rights reserved.
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

package java.net;

import java.io.IOException;

/** {@collect.stats} 
 * {@description.open}
 * Thrown to indicate that a HTTP request needs to be retried
 * but cannot be retried automatically, due to streaming mode
 * being enabled.
 * {@description.close}
 *
 * @author  Michael McMahon
 * @since   1.5
 */
public
class HttpRetryException extends IOException {

    private int responseCode;
    private String location;

    /** {@collect.stats} 
     * {@description.open}
     * Constructs a new <code>HttpRetryException</code> from the
     * specified response code and exception detail message
     * {@description.close}
     *
     * @param   detail   the detail message.
     * @param   code   the HTTP response code from server.
     */
    public HttpRetryException(String detail, int code) {
        super(detail);
        responseCode = code;
    }

    /** {@collect.stats} 
     * {@description.open}
     * Constructs a new <code>HttpRetryException</code> with detail message
     * responseCode and the contents of the Location response header field.
     * {@description.close}
     *
     * @param   detail   the detail message.
     * @param   code   the HTTP response code from server.
     * @param   location   the URL to be redirected to
     */
    public HttpRetryException(String detail, int code, String location) {
        super (detail);
        responseCode = code;
        this.location = location;
    }

    /** {@collect.stats} 
     * {@description.open}
     * Returns the http response code
     * {@description.close}
     *
     * @return  The http response code.
     */
    public int responseCode() {
        return responseCode;
    }

    /** {@collect.stats} 
     * {@description.open}
     * Returns a string explaining why the http request could
     * not be retried.
     * {@description.close}
     *
     * @return  The reason string
     */
    public String getReason() {
        return super.getMessage();
    }

    /** {@collect.stats} 
     * {@description.open}
     * Returns the value of the Location header field if the
     * error resulted from redirection.
     * {@description.close}
     *
     * @return The location string
     */
    public String getLocation() {
        return location;
    }
}
