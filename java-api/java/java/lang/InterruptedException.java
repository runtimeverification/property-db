/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
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
     * Thrown when a thread is waiting, sleeping, or otherwise occupied,
 * and the thread is interrupted, either before or during the activity.
 * Occasionally a method may wish to test whether the current
 * thread has been interrupted, and if so, to immediately throw
 * this exception.  The following code can be used to achieve
 * this effect:
 * <pre>
 *  if (Thread.interrupted())  // Clears interrupted status!
 *      throw new InterruptedException();

     * {@description.close} * </pre>
 *
 * @author  Frank Yellin
 * @see     java.lang.Object#wait()
 * @see     java.lang.Object#wait(long)
 * @see     java.lang.Object#wait(long, int)
 * @see     java.lang.Thread#sleep(long)
 * @see     java.lang.Thread#interrupt()
 * @see     java.lang.Thread#interrupted()
 * @since   JDK1.0
 */
public
class InterruptedException extends Exception {
    private static final long serialVersionUID = 6700697376100628473L;

    /** {@collect.stats}
     *      
* {@description.open}
     * Constructs an <code>InterruptedException</code> with no detail  message.

     * {@description.close}     */
    public InterruptedException() {
        super();
    }

    /** {@collect.stats}
     *      
* {@description.open}
     * Constructs an <code>InterruptedException</code> with the
     * specified detail message.

     * {@description.close}     *
     * @param   s   the detail message.
     */
    public InterruptedException(String s) {
        super(s);
    }
}
