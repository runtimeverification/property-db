/*
 * Copyright (c) 1996, 2007, Oracle and/or its affiliates. All rights reserved.
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

package java.awt.event;

import java.awt.Adjustable;
import java.awt.AWTEvent;

/** {@collect.stats} 
 * The adjustment event emitted by Adjustable objects.
 * @see java.awt.Adjustable
 * @see AdjustmentListener
 *
 * @author Amy Fowler
 * @since 1.1
 */
public class AdjustmentEvent extends AWTEvent {

    /** {@collect.stats} 
     * Marks the first integer id for the range of adjustment event ids.
     */
    public static final int ADJUSTMENT_FIRST    = 601;

    /** {@collect.stats} 
     * Marks the last integer id for the range of adjustment event ids.
     */
    public static final int ADJUSTMENT_LAST     = 601;

    /** {@collect.stats} 
     * The adjustment value changed event.
     */
    public static final int ADJUSTMENT_VALUE_CHANGED = ADJUSTMENT_FIRST; //Event.SCROLL_LINE_UP

    /** {@collect.stats} 
     * The unit increment adjustment type.
     */
    public static final int UNIT_INCREMENT      = 1;

    /** {@collect.stats} 
     * The unit decrement adjustment type.
     */
    public static final int UNIT_DECREMENT      = 2;

    /** {@collect.stats} 
     * The block decrement adjustment type.
     */
    public static final int BLOCK_DECREMENT     = 3;

    /** {@collect.stats} 
     * The block increment adjustment type.
     */
    public static final int BLOCK_INCREMENT     = 4;

    /** {@collect.stats} 
     * The absolute tracking adjustment type.
     */
    public static final int TRACK               = 5;

    /** {@collect.stats} 
     * The adjustable object that fired the event.
     *
     * @serial
     * @see #getAdjustable
     */
    Adjustable adjustable;

    /** {@collect.stats} 
     * <code>value</code> will contain the new value of the
     * adjustable object.  This value will always be  in a
     * range associated adjustable object.
     *
     * @serial
     * @see #getValue
     */
    int value;

    /** {@collect.stats} 
     * The <code>adjustmentType</code> describes how the adjustable
     * object value has changed.
     * This value can be increased/decreased by a block or unit amount
     * where the block is associated with page increments/decrements,
     * and a unit is associated with line increments/decrements.
     *
     * @serial
     * @see #getAdjustmentType
     */
    int adjustmentType;


    /** {@collect.stats} 
     * The <code>isAdjusting</code> is true if the event is one
     * of the series of multiple adjustment events.
     *
     * @since 1.4
     * @serial
     * @see #getValueIsAdjusting
     */
    boolean isAdjusting;


    /*
     * JDK 1.1 serialVersionUID
     */
     private static final long serialVersionUID = 5700290645205279921L;


    /** {@collect.stats} 
     * Constructs an <code>AdjustmentEvent</code> object with the
     * specified <code>Adjustable</code> source, event type,
     * adjustment type, and value.
     * <p>Note that passing in an invalid <code>id</code> results in
     * unspecified behavior.  This method throws an
     * <code>IllegalArgumentException</code> if <code>source</code>
     * is <code>null</code>.
     *
     * @param source the <code>Adjustable</code> object where the
     *               event originated
     * @param id     the event type
     * @param type   the adjustment type
     * @param value  the current value of the adjustment
     * @throws IllegalArgumentException if <code>source</code> is null
     */
    public AdjustmentEvent(Adjustable source, int id, int type, int value) {
        this(source, id, type, value, false);
    }

    /** {@collect.stats} 
     * Constructs an <code>AdjustmentEvent</code> object with the
     * specified Adjustable source, event type, adjustment type, and value.
     * <p>Note that passing in an invalid <code>id</code> results in
     * unspecified behavior.  This method throws an
     * <code>IllegalArgumentException</code> if <code>source</code>
     * is <code>null</code>.

     *
     * @param source the <code>Adjustable</code> object where the
     *               event originated
     * @param id     the event type
     * @param type   the adjustment type
     * @param value  the current value of the adjustment
     * @param isAdjusting <code>true</code> if the event is one
     *               of a series of multiple adjusting events,
     *               otherwise <code>false</code>
     * @throws IllegalArgumentException if <code>source</code> is null
     * @since 1.4
     */
    public AdjustmentEvent(Adjustable source, int id, int type, int value, boolean isAdjusting) {
        super(source, id);
        adjustable = source;
        this.adjustmentType = type;
        this.value = value;
        this.isAdjusting = isAdjusting;
    }

    /** {@collect.stats} 
     * Returns the <code>Adjustable</code> object where this event originated.
     *
     * @return the <code>Adjustable</code> object where this event originated
     */
    public Adjustable getAdjustable() {
        return adjustable;
    }

    /** {@collect.stats} 
     * Returns the current value in the adjustment event.
     *
     * @return the current value in the adjustment event
     */
    public int getValue() {
        return value;
    }

    /** {@collect.stats} 
     * Returns the type of adjustment which caused the value changed
     * event.  It will have one of the following values:
     * <ul>
     * <li>{@link #UNIT_INCREMENT}
     * <li>{@link #UNIT_DECREMENT}
     * <li>{@link #BLOCK_INCREMENT}
     * <li>{@link #BLOCK_DECREMENT}
     * <li>{@link #TRACK}
     * </ul>
     * @return one of the adjustment values listed above
     */
    public int getAdjustmentType() {
        return adjustmentType;
    }

    /** {@collect.stats} 
     * Returns <code>true</code> if this is one of multiple
     * adjustment events.
     *
     * @return <code>true</code> if this is one of multiple
     *         adjustment events, otherwise returns <code>false</code>
     * @since 1.4
     */
    public boolean getValueIsAdjusting() {
        return isAdjusting;
    }

    public String paramString() {
        String typeStr;
        switch(id) {
          case ADJUSTMENT_VALUE_CHANGED:
              typeStr = "ADJUSTMENT_VALUE_CHANGED";
              break;
          default:
              typeStr = "unknown type";
        }
        String adjTypeStr;
        switch(adjustmentType) {
          case UNIT_INCREMENT:
              adjTypeStr = "UNIT_INCREMENT";
              break;
          case UNIT_DECREMENT:
              adjTypeStr = "UNIT_DECREMENT";
              break;
          case BLOCK_INCREMENT:
              adjTypeStr = "BLOCK_INCREMENT";
              break;
          case BLOCK_DECREMENT:
              adjTypeStr = "BLOCK_DECREMENT";
              break;
          case TRACK:
              adjTypeStr = "TRACK";
              break;
          default:
              adjTypeStr = "unknown type";
        }
        return typeStr
            + ",adjType="+adjTypeStr
            + ",value="+value
            + ",isAdjusting="+isAdjusting;
    }
}
