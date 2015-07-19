/*
 * Copyright (c) 2000, 2003, Oracle and/or its affiliates. All rights reserved.
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

import java.io.NotSerializableException;

/** {@collect.stats}
 *      
* {@description.open}
     * An event emitted by a <tt>Preferences</tt> node to indicate that
 * a child of that node has been added or removed.<p>
 *
 * Note, that although NodeChangeEvent inherits Serializable interface from
 * java.util.EventObject, it is not intended to be Serializable. Appropriate
 * serialization methods are implemented to throw NotSerializableException.

     * {@description.close} *
 * @author  Josh Bloch
 * @see     Preferences
 * @see     NodeChangeListener
 * @see     PreferenceChangeEvent
 * @since   1.4
 * @serial  exclude
 */

public class NodeChangeEvent extends java.util.EventObject {
    /** {@collect.stats}
     *      
* {@description.open}
     * The node that was added or removed.

     * {@description.close}     *
     * @serial
     */
    private Preferences child;

    /** {@collect.stats}
     *      
* {@description.open}
     * Constructs a new <code>NodeChangeEvent</code> instance.

     * {@description.close}     *
     * @param parent  The parent of the node that was added or removed.
     * @param child   The node that was added or removed.
     */
    public NodeChangeEvent(Preferences parent, Preferences child) {
        super(parent);
        this.child = child;
    }

    /** {@collect.stats}
     *      
* {@description.open}
     * Returns the parent of the node that was added or removed.

     * {@description.close}     *
     * @return  The parent Preferences node whose child was added or removed
     */
    public Preferences getParent() {
        return (Preferences) getSource();
    }

    /** {@collect.stats}
     *      
* {@description.open}
     * Returns the node that was added or removed.

     * {@description.close}     *
     * @return  The node that was added or removed.
     */
    public Preferences getChild() {
        return child;
    }

    /** {@collect.stats}
     *      
* {@description.open}
     * Throws NotSerializableException, since NodeChangeEvent objects are not
     * intended to be serializable.

     * {@description.close}     */
     private void writeObject(java.io.ObjectOutputStream out)
                                               throws NotSerializableException {
         throw new NotSerializableException("Not serializable.");
     }

    /** {@collect.stats}
     *      
* {@description.open}
     * Throws NotSerializableException, since NodeChangeEvent objects are not
     * intended to be serializable.

     * {@description.close}     */
     private void readObject(java.io.ObjectInputStream in)
                                               throws NotSerializableException {
         throw new NotSerializableException("Not serializable.");
     }

    // Defined so that this class isn't flagged as a potential problem when
    // searches for missing serialVersionUID fields are done.
    private static final long serialVersionUID = 8068949086596572957L;
}
