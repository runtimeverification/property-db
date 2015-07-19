/*
 * Copyright (c) 2000, Oracle and/or its affiliates. All rights reserved.
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

package java.util.prefs;

/** {@collect.stats}
 *      
* {@description.open}
     * A listener for receiving preference node change events.

     * {@description.close} *
 * @author  Josh Bloch
 * @see     Preferences
 * @see     NodeChangeEvent
 * @see     PreferenceChangeListener
 * @since   1.4
 */

public interface NodeChangeListener extends java.util.EventListener {
    /** {@collect.stats}
     *      
* {@description.open}
     * This method gets called when a child node is added.

     * {@description.close}     *
     * @param evt A node change event object describing the parent
     *            and child node.
     */
    void childAdded(NodeChangeEvent evt);

    /** {@collect.stats}
     *      
* {@description.open}
     * This method gets called when a child node is removed.

     * {@description.close}     *
     * @param evt A node change event object describing the parent
     *            and child node.
     */
    void childRemoved(NodeChangeEvent evt);
}
