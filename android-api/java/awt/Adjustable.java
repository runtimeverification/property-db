/*
 * Copyright (c) 1996, 2003, Oracle and/or its affiliates. All rights reserved.
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

package java.awt;

import java.awt.event.*;

/** {@collect.stats} 
 * The interface for objects which have an adjustable numeric value
 * contained within a bounded range of values.
 *
 * @author Amy Fowler
 * @author Tim Prinzing
 */

public interface Adjustable {

    /** {@collect.stats} 
     * Indicates that the <code>Adjustable</code> has horizontal orientation.
     */
    public static final int HORIZONTAL = 0;

    /** {@collect.stats} 
     * Indicates that the <code>Adjustable</code> has vertical orientation.
     */
    public static final int VERTICAL = 1;

    /** {@collect.stats} 
     * Indicates that the <code>Adjustable</code> has no orientation.
     */
    public static final int NO_ORIENTATION = 2;

    /** {@collect.stats} 
     * Gets the orientation of the adjustable object.
     * @return the orientation of the adjustable object;
     *   either <code>HORIZONTAL</code>, <code>VERTICAL</code>,
     *   or <code>NO_ORIENTATION</code>
     */
    int getOrientation();

    /** {@collect.stats} 
     * Sets the minimum value of the adjustable object.
     * @param min the minimum value
     */
    void setMinimum(int min);

    /** {@collect.stats} 
     * Gets the minimum value of the adjustable object.
     * @return the minimum value of the adjustable object
     */
    int getMinimum();

    /** {@collect.stats} 
     * Sets the maximum value of the adjustable object.
     * @param max the maximum value
     */
    void setMaximum(int max);

    /** {@collect.stats} 
     * Gets the maximum value of the adjustable object.
     * @return the maximum value of the adjustable object
     */
    int getMaximum();

    /** {@collect.stats} 
     * Sets the unit value increment for the adjustable object.
     * @param u the unit increment
     */
    void setUnitIncrement(int u);

    /** {@collect.stats} 
     * Gets the unit value increment for the adjustable object.
     * @return the unit value increment for the adjustable object
     */
    int getUnitIncrement();

    /** {@collect.stats} 
     * Sets the block value increment for the adjustable object.
     * @param b the block increment
     */
    void setBlockIncrement(int b);

    /** {@collect.stats} 
     * Gets the block value increment for the adjustable object.
     * @return the block value increment for the adjustable object
     */
    int getBlockIncrement();

    /** {@collect.stats} 
     * Sets the length of the proportional indicator of the
     * adjustable object.
     * @param v the length of the indicator
     */
    void setVisibleAmount(int v);

    /** {@collect.stats} 
     * Gets the length of the proportional indicator.
     * @return the length of the proportional indicator
     */
    int getVisibleAmount();

    /** {@collect.stats} 
     * Sets the current value of the adjustable object. If
     * the value supplied is less than <code>minimum</code>
     * or greater than <code>maximum</code> - <code>visibleAmount</code>,
     * then one of those values is substituted, as appropriate.
     * <p>
     * Calling this method does not fire an
     * <code>AdjustmentEvent</code>.
     *
     * @param v the current value, between <code>minimum</code>
     *    and <code>maximum</code> - <code>visibleAmount</code>
     */
    void setValue(int v);

    /** {@collect.stats} 
     * Gets the current value of the adjustable object.
     * @return the current value of the adjustable object
     */
    int getValue();

    /** {@collect.stats} 
     * Adds a listener to receive adjustment events when the value of
     * the adjustable object changes.
     * @param l the listener to receive events
     * @see AdjustmentEvent
     */
    void addAdjustmentListener(AdjustmentListener l);

    /** {@collect.stats} 
     * Removes an adjustment listener.
     * @param l the listener being removed
     * @see AdjustmentEvent
     */
    void removeAdjustmentListener(AdjustmentListener l);

}
