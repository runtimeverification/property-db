/*
 * Copyright (c) 2004, 2013, Oracle and/or its affiliates. All rights reserved.
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
    private static final long serialVersionUID = -9186022286469111381L;

    private int responseCode;
    private String location;

    /** {@collect.stats} 
     * {@description.open}
     * Constructs a new {@code HttpRetryException} from the
     * specified response code and exception detail message
     *
     * @param   detail   the detail message.
     * @param   code   the HTTP response code from server.
     */
    public HttpRetryException(String detail, int code) {
        super(detail);
        responseCode = code;
    }

    /**
     * Constructs a new {@code HttpRetryException} with detail message
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
