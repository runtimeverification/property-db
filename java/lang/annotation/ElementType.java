/*
 * Copyright (c) 2003, 2004, Oracle and/or its affiliates. All rights reserved.
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

package java.lang.annotation;

/** {@collect.stats} 
 * {@description.open}
 * A program element type.  The constants of this enumerated type
 * provide a simple classification of the declared elements in a
 * Java program.
 *
 * <p>These constants are used with the {@link Target} meta-annotation type
 * to specify where it is legal to use an annotation type.
 * {@description.close}
 *
 * @author  Joshua Bloch
 * @since 1.5
 */
public enum ElementType {
    /** {@collect.stats}  {@description.open}Class, interface (including annotation type), or enum declaration {@description.close}*/
    TYPE,

    /** {@collect.stats}  {@description.open}Field declaration (includes enum constants) {@description.close}*/
    FIELD,

    /** {@collect.stats}  {@description.open}Method declaration {@description.close}*/
    METHOD,

    /** {@collect.stats}  {@description.open}Parameter declaration {@description.close}*/
    PARAMETER,

    /** {@collect.stats}  {@description.open}Constructor declaration {@description.close}*/
    CONSTRUCTOR,

    /** {@collect.stats}  {@description.open}Local variable declaration {@description.close}*/
    LOCAL_VARIABLE,

    /** {@collect.stats}  {@description.open}Annotation type declaration {@description.close}*/
    ANNOTATION_TYPE,

    /** {@collect.stats}  {@description.open}Package declaration {@description.close}*/
    PACKAGE
}
