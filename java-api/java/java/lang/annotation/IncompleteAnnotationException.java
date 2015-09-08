/*
 * Copyright (c) 2003, 2013, Oracle and/or its affiliates. All rights reserved.
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
 * {@description.open} 
 * Thrown to indicate that a program has attempted to access an element of
 * an annotation type that was added to the annotation type definition after
 * the annotation was compiled (or serialized).  This exception will not be
 * thrown if the new element has a default value.
 * {@description.close}
 *
 * This exception can be thrown by the {@linkplain
 * java.lang.reflect.AnnotatedElement API used to read annotations
 * reflectively}.
 *
 * @author  Josh Bloch
 * @see     java.lang.reflect.AnnotatedElement
 * @since 1.5
 */
public class IncompleteAnnotationException extends RuntimeException {
    private static final long serialVersionUID = 8445097402741811912L;

    private Class<? extends Annotation> annotationType;
    private String elementName;

    /** {@collect.stats}
     *      
* {@description.open}
     * Constructs an IncompleteAnnotationException to indicate that
     * the named element was missing from the specified annotation type.

     * {@description.close}     *
     * @param annotationType the Class object for the annotation type
     * @param elementName the name of the missing element
     * @throws NullPointerException if either parameter is {@code null}
     */
    public IncompleteAnnotationException(
            Class<? extends Annotation> annotationType,
            String elementName) {
        super(annotationType.getName() + " missing element " +
              elementName.toString());

        this.annotationType = annotationType;
        this.elementName = elementName;
    }

    /** {@collect.stats}
     *      
* {@description.open}
     * Returns the Class object for the annotation type with the
     * missing element.

     * {@description.close}     *
     * @return the Class object for the annotation type with the
     *     missing element
     */
    public Class<? extends Annotation> annotationType() {
        return annotationType;
    }

    /** {@collect.stats}
     *      
* {@description.open}
     * Returns the name of the missing element.

     * {@description.close}     *
     * @return the name of the missing element
     */
    public String elementName() {
        return elementName;
    }
}
