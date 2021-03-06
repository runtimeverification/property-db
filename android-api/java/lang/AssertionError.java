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

package java.lang;

/** {@collect.stats} 
 * {@description.open}
 * Thrown to indicate that an assertion has failed.
 *
 * <p>The seven one-argument public constructors provided by this
 * class ensure that the assertion error returned by the invocation:
 * <pre>
 *     new AssertionError(<i>expression</i>)
 * </pre>
 * has as its detail message the <i>string conversion</i> of
 * <i>expression</i> (as defined in <a
 * href="http://java.sun.com/docs/books/jls/second_edition/html/j.title.doc.html">
 * <i>The Java Language Specification, Second Edition</i></a>,
 * <a href="http://java.sun.com/docs/books/jls/second_edition/html/expressions.doc.html#40220">
 * Section  15.18.1.1</a>), regardless of the type of <i>expression</i>.
 * {@description.close}
 *
 * @since   1.4
 */
public class AssertionError extends Error {
    /** {@collect.stats}
     * {@description.open} 
     * Constructs an AssertionError with no detail message.
     * {@description.close}
     */
    public AssertionError() {
    }

    /** {@collect.stats} 
     * {@description.open}
     * This internal constructor does no processing on its string argument,
     * even if it is a null reference.  The public constructors will
     * never call this constructor with a null argument.
     * {@description.close}
     */
    private AssertionError(String detailMessage) {
        super(detailMessage);
    }

    /** {@collect.stats} 
     * {@description.open}
     * Constructs an AssertionError with its detail message derived
     * from the specified object, which is converted to a string as
     * defined in <i>The Java Language Specification, Second
     * Edition</i>, Section 15.18.1.1.
     *<p>
     * If the specified object is an instance of <tt>Throwable</tt>, it
     * becomes the <i>cause</i> of the newly constructed assertion error.
     * {@description.close}
     *
     * @param detailMessage value to be used in constructing detail message
     * @see   Throwable#getCause()
     */
    public AssertionError(Object detailMessage) {
        this("" +  detailMessage);
        if (detailMessage instanceof Throwable)
            initCause((Throwable) detailMessage);
    }

    /** {@collect.stats}
     * {@description.open} 
     * Constructs an AssertionError with its detail message derived
     * from the specified <code>boolean</code>, which is converted to
     * a string as defined in <i>The Java Language Specification,
     * Second Edition</i>, Section 15.18.1.1.
     * {@description.close}
     *
     * @param detailMessage value to be used in constructing detail message
     */
    public AssertionError(boolean detailMessage) {
        this("" +  detailMessage);
    }

    /** {@collect.stats} 
     * {@description.open}
     * Constructs an AssertionError with its detail message derived
     * from the specified <code>char</code>, which is converted to a
     * string as defined in <i>The Java Language Specification, Second
     * Edition</i>, Section 15.18.1.1.
     * {@description.close}
     *
     * @param detailMessage value to be used in constructing detail message
     */
    public AssertionError(char detailMessage) {
        this("" +  detailMessage);
    }

    /** {@collect.stats} 
     * {@description.open}
     * Constructs an AssertionError with its detail message derived
     * from the specified <code>int</code>, which is converted to a
     * string as defined in <i>The Java Language Specification, Second
     * Edition</i>, Section 15.18.1.1.
     * {@description.close}
     *
     * @param detailMessage value to be used in constructing detail message
     */
    public AssertionError(int detailMessage) {
        this("" +  detailMessage);
    }

    /** {@collect.stats} 
     * {@description.open}
     * Constructs an AssertionError with its detail message derived
     * from the specified <code>long</code>, which is converted to a
     * string as defined in <i>The Java Language Specification, Second
     * Edition</i>, Section 15.18.1.1.
     * {@description.close}
     *
     * @param detailMessage value to be used in constructing detail message
     */
    public AssertionError(long detailMessage) {
        this("" +  detailMessage);
    }

    /** {@collect.stats} 
     * {@description.open}
     * Constructs an AssertionError with its detail message derived
     * from the specified <code>float</code>, which is converted to a
     * string as defined in <i>The Java Language Specification, Second
     * Edition</i>, Section 15.18.1.1.
     * {@description.close}
     *
     * @param detailMessage value to be used in constructing detail message
     */
    public AssertionError(float detailMessage) {
        this("" +  detailMessage);
    }

    /** {@collect.stats} 
     * {@description.open}
     * Constructs an AssertionError with its detail message derived
     * from the specified <code>double</code>, which is converted to a
     * string as defined in <i>The Java Language Specification, Second
     * Edition</i>, Section 15.18.1.1.
     * {@description.close}
     *
     * @param detailMessage value to be used in constructing detail message
     */
    public AssertionError(double detailMessage) {
        this("" +  detailMessage);
    }
}
