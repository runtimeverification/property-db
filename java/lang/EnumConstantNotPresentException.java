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

package java.lang;

/** {@collect.stats}
 * {@description.open} 
 * Thrown when an application tries to access an enum constant by name
 * and the enum type contains no constant with the specified name.
 * {@description.close}
 *
 * @author  Josh Bloch
 * @since   1.5
 */
public class EnumConstantNotPresentException extends RuntimeException {
    /** {@collect.stats}
     * {@description.open} 
     * The type of the missing enum constant.
     * {@description.close}
     */
    private Class<? extends Enum> enumType;

    /** {@collect.stats} 
     * {@description.open}
     * The name of the missing enum constant.
     * {@description.close}
     */
    private String constantName;

    /** {@collect.stats} 
     * {@description.open}
     * Constructs an <tt>EnumConstantNotPresentException</tt> for the
     * specified constant.
     * {@description.close}
     *
     * @param enumType the type of the missing enum constant
     * @param constantName the name of the missing enum constant
     */
    public EnumConstantNotPresentException(Class<? extends Enum> enumType,
                                           String constantName) {
        super(enumType.getName() + "." + constantName);
        this.enumType = enumType;
        this.constantName  = constantName;
    }

    /** {@collect.stats} 
     * {@description.open}
     * Returns the type of the missing enum constant.
     * {@description.close}
     *
     * @return the type of the missing enum constant
     */
    public Class<? extends Enum> enumType() { return enumType; }

    /** {@collect.stats} 
     * {@description.open}
     * Returns the name of the missing enum constant.
     * {@description.close}
     *
     * @return the name of the missing enum constant
     */
    public String constantName() { return constantName; }
}
