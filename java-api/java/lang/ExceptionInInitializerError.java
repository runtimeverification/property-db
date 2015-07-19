/*
 * Copyright (c) 1996, 2000, Oracle and/or its affiliates. All rights reserved.
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

package java.lang;

/** {@collect.stats}
 *      
* {@description.open}
     * Signals that an unexpected exception has occurred in a static initializer.
 * An <code>ExceptionInInitializerError</code> is thrown to indicate that an
 * exception occurred during evaluation of a static initializer or the
 * initializer for a static variable.
 *
 * <p>As of release 1.4, this exception has been retrofitted to conform to
 * the general purpose exception-chaining mechanism.  The "saved throwable
 * object" that may be provided at construction time and accessed via
 * the {@link #getException()} method is now known as the <i>cause</i>,
 * and may be accessed via the {@link Throwable#getCause()} method, as well
 * as the aforementioned "legacy method."

     * {@description.close} *
 * @author  Frank Yellin
 * @since   JDK1.1
 */
public class ExceptionInInitializerError extends LinkageError {
    /** {@collect.stats}
     *      
* {@description.open}
     * Use serialVersionUID from JDK 1.1.X for interoperability

     * {@description.close}     */
    private static final long serialVersionUID = 1521711792217232256L;

    /** {@collect.stats}
     *      
* {@description.open}
     * This field holds the exception if the
     * ExceptionInInitializerError(Throwable thrown) constructor was
     * used to instantiate the object

     * {@description.close}     *
     * @serial
     *
     */
    private Throwable exception;

    /** {@collect.stats}
     *      
* {@description.open}
     * Constructs an <code>ExceptionInInitializerError</code> with
     * <code>null</code> as its detail message string and with no saved
     * throwable object.
     * A detail message is a String that describes this particular exception.

     * {@description.close}     */
    public ExceptionInInitializerError() {
        initCause(null);  // Disallow subsequent initCause
    }

    /** {@collect.stats}
     *      
* {@description.open}
     * Constructs a new <code>ExceptionInInitializerError</code> class by
     * saving a reference to the <code>Throwable</code> object thrown for
     * later retrieval by the {@link #getException()} method. The detail
     * message string is set to <code>null</code>.

     * {@description.close}     *
     * @param thrown The exception thrown
     */
    public ExceptionInInitializerError(Throwable thrown) {
        initCause(null);  // Disallow subsequent initCause
        this.exception = thrown;
    }

    /** {@collect.stats}
     *      
* {@description.open}
     * Constructs an ExceptionInInitializerError with the specified detail
     * message string.  A detail message is a String that describes this
     * particular exception. The detail message string is saved for later
     * retrieval by the {@link Throwable#getMessage()} method. There is no
     * saved throwable object.

     * {@description.close}     *
     *
     * @param s the detail message
     */
    public ExceptionInInitializerError(String s) {
        super(s);
        initCause(null);  // Disallow subsequent initCause
    }

    /** {@collect.stats}
     *      
* {@description.open}
     * Returns the exception that occurred during a static initialization that
     * caused this error to be created.
     *
     * <p>This method predates the general-purpose exception chaining facility.
     * The {@link Throwable#getCause()} method is now the preferred means of
     * obtaining this information.

     * {@description.close}     *
     * @return the saved throwable object of this
     *         <code>ExceptionInInitializerError</code>, or <code>null</code>
     *         if this <code>ExceptionInInitializerError</code> has no saved
     *         throwable object.
     */
    public Throwable getException() {
        return exception;
    }

    /** {@collect.stats}
     *      
* {@description.open}
     * Returns the cause of this error (the exception that occurred
     * during a static initialization that caused this error to be created).

     * {@description.close}     *
     * @return  the cause of this error or <code>null</code> if the
     *          cause is nonexistent or unknown.
     * @since   1.4
     */
    public Throwable getCause() {
        return exception;
    }
}
