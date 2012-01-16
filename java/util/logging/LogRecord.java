/*
 * Copyright (c) 2000, 2004, Oracle and/or its affiliates. All rights reserved.
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
import java.util.*;
import java.io.*;

/** {@collect.stats} 
 * {@description.open}
 * LogRecord objects are used to pass logging requests between
 * the logging framework and individual log Handlers.
 * <p>
 * When a LogRecord is passed into the logging framework it
 * logically belongs to the framework and should no longer be
 * used or updated by the client application.
 * <p>
 * Note that if the client application has not specified an
 * explicit source method name and source class name, then the
 * LogRecord class will infer them automatically when they are
 * first accessed (due to a call on getSourceMethodName or
 * getSourceClassName) by analyzing the call stack.  Therefore,
 * if a logging Handler wants to pass off a LogRecord to another
 * thread, or to transmit it over RMI, and if it wishes to subsequently
 * obtain method name or class name information it should call
 * one of getSourceClassName or getSourceMethodName to force
 * the values to be filled in.
 * <p>
 * <b> Serialization notes:</b>
 * <ul>
 * <li>The LogRecord class is serializable.
 *
 * <li> Because objects in the parameters array may not be serializable,
 * during serialization all objects in the parameters array are
 * written as the corresponding Strings (using Object.toString).
 *
 * <li> The ResourceBundle is not transmitted as part of the serialized
 * form, but the resource bundle name is, and the recipient object's
 * readObject method will attempt to locate a suitable resource bundle.
 *
 * </ul>
 * {@description.close}
 *
 * @since 1.4
 */

public class LogRecord implements java.io.Serializable {
    private static long globalSequenceNumber;
    private static int nextThreadId=10;
    private static ThreadLocal<Integer> threadIds = new ThreadLocal<Integer>();

    /** {@collect.stats} 
     * @serial Logging message level
     */
    private Level level;

    /** {@collect.stats} 
     * @serial Sequence number
     */
    private long sequenceNumber;

    /** {@collect.stats} 
     * @serial Class that issued logging call
     */
    private String sourceClassName;

    /** {@collect.stats} 
     * @serial Method that issued logging call
     */
    private String sourceMethodName;

    /** {@collect.stats} 
     * @serial Non-localized raw message text
     */
    private String message;

    /** {@collect.stats} 
     * @serial Thread ID for thread that issued logging call.
     */
    private int threadID;

    /** {@collect.stats} 
     * @serial Event time in milliseconds since 1970
     */
    private long millis;

    /** {@collect.stats} 
     * @serial The Throwable (if any) associated with log message
     */
    private Throwable thrown;

    /** {@collect.stats} 
     * @serial Name of the source Logger.
     */
    private String loggerName;

    /** {@collect.stats} 
     * @serial Resource bundle name to localized log message.
     */
    private String resourceBundleName;

    private transient boolean needToInferCaller;
    private transient Object parameters[];
    private transient ResourceBundle resourceBundle;

    /** {@collect.stats} 
     * {@description.open}
     * Construct a LogRecord with the given level and message values.
     * <p>
     * The sequence property will be initialized with a new unique value.
     * These sequence values are allocated in increasing order within a VM.
     * <p>
     * The millis property will be initialized to the current time.
     * <p>
     * The thread ID property will be initialized with a unique ID for
     * the current thread.
     * <p>
     * All other properties will be initialized to "null".
     * {@description.close}
     *
     * @param level  a logging level value
     * @param msg  the raw non-localized logging message (may be null)
     */
    public LogRecord(Level level, String msg) {
        // Make sure level isn't null, by calling random method.
        level.getClass();
        this.level = level;
        message = msg;
        // Assign a thread ID and a unique sequence number.
        synchronized (LogRecord.class) {
            sequenceNumber = globalSequenceNumber++;
            Integer id = threadIds.get();
            if (id == null) {
                id = new Integer(nextThreadId++);
                threadIds.set(id);
            }
            threadID = id.intValue();
        }
        millis = System.currentTimeMillis();
        needToInferCaller = true;
   }

    /** {@collect.stats} 
     * {@description.open}
     * Get the source Logger name's
     * {@description.close}
     *
     * @return source logger name (may be null)
     */
    public String getLoggerName() {
        return loggerName;
    }

    /** {@collect.stats} 
     * {@description.open}
     * Set the source Logger name.
     * {@description.close}
     *
     * @param name   the source logger name (may be null)
     */
    public void setLoggerName(String name) {
        loggerName = name;
    }

    /** {@collect.stats} 
     * {@description.open}
     * Get the localization resource bundle
     * <p>
     * This is the ResourceBundle that should be used to localize
     * the message string before formatting it.  The result may
     * be null if the message is not localizable, or if no suitable
     * ResourceBundle is available.
     * {@description.close}
     */
    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }

    /** {@collect.stats} 
     * {@description.open}
     * Set the localization resource bundle.
     * {@description.close}
     *
     * @param bundle  localization bundle (may be null)
     */
    public void setResourceBundle(ResourceBundle bundle) {
        resourceBundle = bundle;
    }

    /** {@collect.stats} 
     * {@description.open}
     * Get the localization resource bundle name
     * <p>
     * This is the name for the ResourceBundle that should be
     * used to localize the message string before formatting it.
     * The result may be null if the message is not localizable.
     * {@description.close}
     */
    public String getResourceBundleName() {
        return resourceBundleName;
    }

    /** {@collect.stats} 
     * {@description.open}
     * Set the localization resource bundle name.
     * {@description.close}
     *
     * @param name  localization bundle name (may be null)
     */
    public void setResourceBundleName(String name) {
        resourceBundleName = name;
    }

    /** {@collect.stats} 
     * {@description.open}
     * Get the logging message level, for example Level.SEVERE.
     * {@description.close}
     * @return the logging message level
     */
    public Level getLevel() {
        return level;
    }

    /** {@collect.stats} 
     * {@description.open}
     * Set the logging message level, for example Level.SEVERE.
     * {@description.close}
     * @param level the logging message level
     */
    public void setLevel(Level level) {
        if (level == null) {
            throw new NullPointerException();
        }
        this.level = level;
    }

    /** {@collect.stats} 
     * {@description.open}
     * Get the sequence number.
     * <p>
     * Sequence numbers are normally assigned in the LogRecord
     * constructor, which assigns unique sequence numbers to
     * each new LogRecord in increasing order.
     * {@description.close}
     * @return the sequence number
     */
    public long getSequenceNumber() {
        return sequenceNumber;
    }

    /** {@collect.stats} 
     * {@description.open}
     * Set the sequence number.
     * <p>
     * Sequence numbers are normally assigned in the LogRecord constructor,
     * so it should not normally be necessary to use this method.
     * {@description.close}
     */
    public void setSequenceNumber(long seq) {
        sequenceNumber = seq;
    }

    /** {@collect.stats} 
     * {@description.open}
     * Get the  name of the class that (allegedly) issued the logging request.
     * <p>
     * Note that this sourceClassName is not verified and may be spoofed.
     * This information may either have been provided as part of the
     * logging call, or it may have been inferred automatically by the
     * logging framework.  In the latter case, the information may only
     * be approximate and may in fact describe an earlier call on the
     * stack frame.
     * <p>
     * May be null if no information could be obtained.
     * {@description.close}
     *
     * @return the source class name
     */
    public String getSourceClassName() {
        if (needToInferCaller) {
            inferCaller();
        }
        return sourceClassName;
    }

    /** {@collect.stats} 
     * {@description.open}
     * Set the name of the class that (allegedly) issued the logging request.
     * {@description.close}
     *
     * @param sourceClassName the source class name (may be null)
     */
    public void setSourceClassName(String sourceClassName) {
        this.sourceClassName = sourceClassName;
        needToInferCaller = false;
    }

    /** {@collect.stats} 
     * {@description.open}
     * Get the  name of the method that (allegedly) issued the logging request.
     * <p>
     * Note that this sourceMethodName is not verified and may be spoofed.
     * This information may either have been provided as part of the
     * logging call, or it may have been inferred automatically by the
     * logging framework.  In the latter case, the information may only
     * be approximate and may in fact describe an earlier call on the
     * stack frame.
     * <p>
     * May be null if no information could be obtained.
     * {@description.close}
     *
     * @return the source method name
     */
    public String getSourceMethodName() {
        if (needToInferCaller) {
            inferCaller();
        }
        return sourceMethodName;
    }

    /** {@collect.stats} 
     * {@description.open}
     * Set the name of the method that (allegedly) issued the logging request.
     * {@description.close}
     *
     * @param sourceMethodName the source method name (may be null)
     */
    public void setSourceMethodName(String sourceMethodName) {
        this.sourceMethodName = sourceMethodName;
        needToInferCaller = false;
    }

    /** {@collect.stats} 
     * {@description.open}
     * Get the "raw" log message, before localization or formatting.
     * <p>
     * May be null, which is equivalent to the empty string "".
     * <p>
     * This message may be either the final text or a localization key.
     * <p>
     * During formatting, if the source logger has a localization
     * ResourceBundle and if that ResourceBundle has an entry for
     * this message string, then the message string is replaced
     * with the localized value.
     * {@description.close}
     *
     * @return the raw message string
     */
    public String getMessage() {
        return message;
    }

    /** {@collect.stats} 
     * {@description.open}
     * Set the "raw" log message, before localization or formatting.
     * {@description.close}
     *
     * @param message the raw message string (may be null)
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /** {@collect.stats} 
     * {@description.open}
     * Get the parameters to the log message.
     * {@description.close}
     *
     * @return the log message parameters.  May be null if
     *                  there are no parameters.
     */
    public Object[] getParameters() {
        return parameters;
    }

    /** {@collect.stats} 
     * {@description.open}
     * Set the parameters to the log message.
     * {@description.close}
     *
     * @param parameters the log message parameters. (may be null)
     */
    public void setParameters(Object parameters[]) {
        this.parameters = parameters;
    }

    /** {@collect.stats} 
     * {@description.open}
     * Get an identifier for the thread where the message originated.
     * <p>
     * This is a thread identifier within the Java VM and may or
     * may not map to any operating system ID.
     * {@description.close}
     *
     * @return thread ID
     */
    public int getThreadID() {
        return threadID;
    }

    /** {@collect.stats} 
     * {@description.open}
     * Set an identifier for the thread where the message originated.
     * {@description.close}
     * @param threadID  the thread ID
     */
    public void setThreadID(int threadID) {
        this.threadID = threadID;
    }

    /** {@collect.stats} 
     * {@description.open}
     * Get event time in milliseconds since 1970.
     * {@description.close}
     *
     * @return event time in millis since 1970
     */
    public long getMillis() {
        return millis;
    }

    /** {@collect.stats} 
     * {@description.open}
     * Set event time.
     * {@description.close}
     *
     * @param millis event time in millis since 1970
     */
    public void setMillis(long millis) {
        this.millis = millis;
    }

    /** {@collect.stats} 
     * {@description.open}
     * Get any throwable associated with the log record.
     * <p>
     * If the event involved an exception, this will be the
     * exception object. Otherwise null.
     * {@description.close}
     *
     * @return a throwable
     */
    public Throwable getThrown() {
        return thrown;
    }

    /** {@collect.stats} 
     * {@description.open}
     * Set a throwable associated with the log event.
     * {@description.close}
     *
     * @param thrown  a throwable (may be null)
     */
    public void setThrown(Throwable thrown) {
        this.thrown = thrown;
    }

    private static final long serialVersionUID = 5372048053134512534L;

    /** {@collect.stats} 
     * @serialData Default fields, followed by a two byte version number
     * (major byte, followed by minor byte), followed by information on
     * the log record parameter array.  If there is no parameter array,
     * then -1 is written.  If there is a parameter array (possible of zero
     * length) then the array length is written as an integer, followed
     * by String values for each parameter.  If a parameter is null, then
     * a null String is written.  Otherwise the output of Object.toString()
     * is written.
     */
    private void writeObject(ObjectOutputStream out) throws IOException {
        // We have to call defaultWriteObject first.
        out.defaultWriteObject();

        // Write our version number.
        out.writeByte(1);
        out.writeByte(0);
        if (parameters == null) {
            out.writeInt(-1);
            return;
        }
        out.writeInt(parameters.length);
        // Write string values for the parameters.
        for (int i = 0; i < parameters.length; i++) {
            if (parameters[i] == null) {
                out.writeObject(null);
            } else {
                out.writeObject(parameters[i].toString());
            }
        }
    }

    private void readObject(ObjectInputStream in)
                        throws IOException, ClassNotFoundException {
        // We have to call defaultReadObject first.
        in.defaultReadObject();

        // Read version number.
        byte major = in.readByte();
        byte minor = in.readByte();
        if (major != 1) {
            throw new IOException("LogRecord: bad version: " + major + "." + minor);
        }
        int len = in.readInt();
        if (len == -1) {
            parameters = null;
        } else {
            parameters = new Object[len];
            for (int i = 0; i < parameters.length; i++) {
                parameters[i] = in.readObject();
            }
        }
        // If necessary, try to regenerate the resource bundle.
        if (resourceBundleName != null) {
            try {
                resourceBundle = ResourceBundle.getBundle(resourceBundleName);
            } catch (MissingResourceException ex) {
                // This is not a good place to throw an exception,
                // so we simply leave the resourceBundle null.
                resourceBundle = null;
            }
        }

        needToInferCaller = false;
    }

    // Private method to infer the caller's class and method names
    private void inferCaller() {
        needToInferCaller = false;
        // Get the stack trace.
        StackTraceElement stack[] = (new Throwable()).getStackTrace();
        // First, search back to a method in the Logger class.
        int ix = 0;
        while (ix < stack.length) {
            StackTraceElement frame = stack[ix];
            String cname = frame.getClassName();
            if (cname.equals("java.util.logging.Logger")) {
                break;
            }
            ix++;
        }
        // Now search for the first frame before the "Logger" class.
        while (ix < stack.length) {
            StackTraceElement frame = stack[ix];
            String cname = frame.getClassName();
            if (!cname.equals("java.util.logging.Logger")) {
                // We've found the relevant frame.
                setSourceClassName(cname);
                setSourceMethodName(frame.getMethodName());
                return;
            }
            ix++;
        }
        // We haven't found a suitable frame, so just punt.  This is
        // OK as we are only committed to making a "best effort" here.
    }
}
