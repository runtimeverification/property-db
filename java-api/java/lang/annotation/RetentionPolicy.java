/*
 * Copyright (c) 2003, 2004, Oracle and/or its affiliates. All rights reserved.
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

package java.lang.annotation;

/** {@collect.stats}
 *      
* {@description.open}
     * Annotation retention policy.  The constants of this enumerated type
 * describe the various policies for retaining annotations.  They are used
 * in conjunction with the {@link Retention} meta-annotation type to specify
 * how long annotations are to be retained.

     * {@description.close} *
 * @author  Joshua Bloch
 * @since 1.5
 */
public enum RetentionPolicy {
    /** {@collect.stats}
     *      
* {@description.open}
     * Annotations are to be discarded by the compiler.

     * {@description.close}     */
    SOURCE,

    /** {@collect.stats}
     *      
* {@description.open}
     * Annotations are to be recorded in the class file by the compiler
     * but need not be retained by the VM at run time.  This is the default
     * behavior.

     * {@description.close}     */
    CLASS,

    /** {@collect.stats}
     *      
* {@description.open}
     * Annotations are to be recorded in the class file by the compiler and
     * retained by the VM at run time, so they may be read reflectively.

     * {@description.close}     *
     * @see java.lang.reflect.AnnotatedElement
     */
    RUNTIME
}
