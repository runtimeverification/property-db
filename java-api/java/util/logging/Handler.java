/*
 * Copyright (c) 2000, 2006, Oracle and/or its affiliates. All rights reserved.
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


package java.util.logging;

import java.io.UnsupportedEncodingException;
/** {@collect.stats} 
 * {@description.open}
 * A <tt>Handler</tt> object takes log messages from a <tt>Logger</tt> and
 * exports them.  It might for example, write them to a console
 * or write them to a file, or send them to a network logging service,
 * or forward them to an OS log, or whatever.
 * <p>
 * A <tt>Handler</tt> can be disabled by doing a <tt>setLevel(Level.OFF)</tt>
 * and can  be re-enabled by doing a <tt>setLevel</tt> with an appropriate level.
 * <p>
 * <tt>Handler</tt> classes typically use <tt>LogManager</tt> properties to set
 * default values for the <tt>Handler</tt>'s <tt>Filter</tt>, <tt>Formatter</tt>,
 * and <tt>Level</tt>.  See the specific documentation for each concrete
 * <tt>Handler</tt> class.
 *
 * {@description.close}
 *
 * @since 1.4
 */

public abstract class Handler {
    private static final int offValue = Level.OFF.intValue();
    private LogManager manager = LogManager.getLogManager();
    private Filter filter;
    private Formatter formatter;
    private Level logLevel = Level.ALL;
    private ErrorManager errorManager = new ErrorManager();
    private String encoding;

    // Package private support for security checking.  When sealed
    // is true, we access check updates to the class.
    boolean sealed = true;

    /** {@collect.stats} 
     * {@description.open}
     * Default constructor.  The resulting <tt>Handler</tt> has a log
     * level of <tt>Level.ALL</tt>, no <tt>Formatter</tt>, and no
     * <tt>Filter</tt>.  A default <tt>ErrorManager</tt> instance is installed
     * as the <tt>ErrorManager</tt>.
     * {@description.close}
     */
    protected Handler() {
    }

    /** {@collect.stats} 
     * {@description.open}
     * Publish a <tt>LogRecord</tt>.
     * <p>
     * The logging request was made initially to a <tt>Logger</tt> object,
     * which initialized the <tt>LogRecord</tt> and forwarded it here.
     * <p>
     * The <tt>Handler</tt>  is responsible for formatting the message, when and
     * if necessary.  The formatting should include localization.
     * {@description.close}
     *
     * @param  record  description of the log event. A null record is
     *                 silently ignored and is not published
     */
    public abstract void publish(LogRecord record);

    /** {@collect.stats} 
     * {@description.open}
     * Flush any buffered output.
     * {@description.close}
     */
    public abstract void flush();

    /** {@collect.stats} 
     * {@description.open}
     * Close the <tt>Handler</tt> and free all associated resources.
     * <p>
     * {@description.close}
     * {@property.open}
     * The close method will perform a <tt>flush</tt> and then close the
     * <tt>Handler</tt>.   After close has been called this <tt>Handler</tt>
     * should no longer be used.  Method calls may either be silently
     * ignored or may throw runtime exceptions.
     * {@property.close}
     *
     * @exception  SecurityException  if a security manager exists and if
     *             the caller does not have <tt>LoggingPermission("control")</tt>.
     */
    public abstract void close() throws SecurityException;

    /** {@collect.stats} 
     * {@description.open}
     * Set a <tt>Formatter</tt>.  This <tt>Formatter</tt> will be used
     * to format <tt>LogRecords</tt> for this <tt>Handler</tt>.
     * <p>
     * Some <tt>Handlers</tt> may not use <tt>Formatters</tt>, in
     * which case the <tt>Formatter</tt> will be remembered, but not used.
     * <p>
     * {@description.close}
     * @param newFormatter the <tt>Formatter</tt> to use (may not be null)
     * @exception  SecurityException  if a security manager exists and if
     *             the caller does not have <tt>LoggingPermission("control")</tt>.
     */
    public void setFormatter(Formatter newFormatter) throws SecurityException {
        checkAccess();
        // Check for a null pointer:
        newFormatter.getClass();
        formatter = newFormatter;
    }

    /** {@collect.stats} 
     * {@description.open}
     * Return the <tt>Formatter</tt> for this <tt>Handler</tt>.
     * {@description.close}
     * @return the <tt>Formatter</tt> (may be null).
     */
    public Formatter getFormatter() {
        return formatter;
    }

    /** {@collect.stats} 
     * {@description.open}
     * Set the character encoding used by this <tt>Handler</tt>.
     * <p>
     * The encoding should be set before any <tt>LogRecords</tt> are written
     * to the <tt>Handler</tt>.
     * {@description.close}
     *
     * @param encoding  The name of a supported character encoding.
     *        May be null, to indicate the default platform encoding.
     * @exception  SecurityException  if a security manager exists and if
     *             the caller does not have <tt>LoggingPermission("control")</tt>.
     * @exception  UnsupportedEncodingException if the named encoding is
     *          not supported.
     */
    public void setEncoding(String encoding)
                        throws SecurityException, java.io.UnsupportedEncodingException {
        checkAccess();
        if (encoding != null) {
            try {
                if(!java.nio.charset.Charset.isSupported(encoding)) {
                    throw new UnsupportedEncodingException(encoding);
                }
            } catch (java.nio.charset.IllegalCharsetNameException e) {
                throw new UnsupportedEncodingException(encoding);
            }
        }
        this.encoding = encoding;
    }

    /** {@collect.stats} 
     * {@description.open}
     * Return the character encoding for this <tt>Handler</tt>.
     * {@description.close}
     *
     * @return  The encoding name.  May be null, which indicates the
     *          default encoding should be used.
     */
    public String getEncoding() {
        return encoding;
    }

    /** {@collect.stats} 
     * {@description.open}
     * Set a <tt>Filter</tt> to control output on this <tt>Handler</tt>.
     * <P>
     * For each call of <tt>publish</tt> the <tt>Handler</tt> will call
     * this <tt>Filter</tt> (if it is non-null) to check if the
     * <tt>LogRecord</tt> should be published or discarded.
     * {@description.close}
     *
     * @param   newFilter  a <tt>Filter</tt> object (may be null)
     * @exception  SecurityException  if a security manager exists and if
     *             the caller does not have <tt>LoggingPermission("control")</tt>.
     */
    public void setFilter(Filter newFilter) throws SecurityException {
        checkAccess();
        filter = newFilter;
    }

    /** {@collect.stats} 
     * {@description.open}
     * Get the current <tt>Filter</tt> for this <tt>Handler</tt>.
     * {@description.close}
     *
     * @return  a <tt>Filter</tt> object (may be null)
     */
    public Filter getFilter() {
        return filter;
    }

    /** {@collect.stats} 
     * {@description.open}
     * Define an ErrorManager for this Handler.
     * <p>
     * The ErrorManager's "error" method will be invoked if any
     * errors occur while using this Handler.
     * {@description.close}
     *
     * @param em  the new ErrorManager
     * @exception  SecurityException  if a security manager exists and if
     *             the caller does not have <tt>LoggingPermission("control")</tt>.
     */
    public void setErrorManager(ErrorManager em) {
        checkAccess();
        if (em == null) {
           throw new NullPointerException();
        }
        errorManager = em;
    }

    /** {@collect.stats} 
     * {@description.open}
     * Retrieves the ErrorManager for this Handler.
     * {@description.close}
     *
     * @exception  SecurityException  if a security manager exists and if
     *             the caller does not have <tt>LoggingPermission("control")</tt>.
     */
    public ErrorManager getErrorManager() {
        checkAccess();
        return errorManager;
    }

   /** {@collect.stats} 
    * {@description.open}
     * Protected convenience method to report an error to this Handler's
     * ErrorManager.  Note that this method retrieves and uses the ErrorManager
     * without doing a security check.  It can therefore be used in
     * environments where the caller may be non-privileged.
     * {@description.close}
     *
     * @param msg    a descriptive string (may be null)
     * @param ex     an exception (may be null)
     * @param code   an error code defined in ErrorManager
     */
    protected void reportError(String msg, Exception ex, int code) {
        try {
            errorManager.error(msg, ex, code);
        } catch (Exception ex2) {
            System.err.println("Handler.reportError caught:");
            ex2.printStackTrace();
        }
    }

    /** {@collect.stats} 
     * {@description.open}
     * Set the log level specifying which message levels will be
     * logged by this <tt>Handler</tt>.  Message levels lower than this
     * value will be discarded.
     * <p>
     * The intention is to allow developers to turn on voluminous
     * logging, but to limit the messages that are sent to certain
     * <tt>Handlers</tt>.
     * {@description.close}
     *
     * @param newLevel   the new value for the log level
     * @exception  SecurityException  if a security manager exists and if
     *             the caller does not have <tt>LoggingPermission("control")</tt>.
     */
    public synchronized void setLevel(Level newLevel) throws SecurityException {
        if (newLevel == null) {
            throw new NullPointerException();
        }
        checkAccess();
        logLevel = newLevel;
    }

    /** {@collect.stats} 
     * {@description.open}
     * Get the log level specifying which messages will be
     * logged by this <tt>Handler</tt>.  Message levels lower
     * than this level will be discarded.
     * {@description.close}
     * @return  the level of messages being logged.
     */
    public synchronized Level getLevel() {
        return logLevel;
    }

    /** {@collect.stats} 
     * {@description.open}
     * Check if this <tt>Handler</tt> would actually log a given <tt>LogRecord</tt>.
     * <p>
     * This method checks if the <tt>LogRecord</tt> has an appropriate
     * <tt>Level</tt> and  whether it satisfies any <tt>Filter</tt>.  It also
     * may make other <tt>Handler</tt> specific checks that might prevent a
     * handler from logging the <tt>LogRecord</tt>. It will return false if
     * the <tt>LogRecord</tt> is Null.
     * <p>
     * {@description.close}
     * @param record  a <tt>LogRecord</tt>
     * @return true if the <tt>LogRecord</tt> would be logged.
     *
     */
    public boolean isLoggable(LogRecord record) {
        int levelValue = getLevel().intValue();
        if (record.getLevel().intValue() < levelValue || levelValue == offValue) {
            return false;
        }
        Filter filter = getFilter();
        if (filter == null) {
            return true;
        }
        return filter.isLoggable(record);
    }

    // Package-private support method for security checks.
    // If "sealed" is true, we check that the caller has
    // appropriate security privileges to update Handler
    // state and if not throw a SecurityException.
    void checkAccess() throws SecurityException {
        if (sealed) {
            manager.checkAccess();
        }
    }
}
