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
 * a preference has been added, removed or has had its value changed.<p>
 *
 * Note, that although PreferenceChangeEvent inherits Serializable interface
 * from EventObject, it is not intended to be Serializable. Appropriate
 * serialization methods are implemented to throw NotSerializableException.

     * {@description.close} *
 * @author  Josh Bloch
 * @see Preferences
 * @see PreferenceChangeListener
 * @see NodeChangeEvent
 * @since   1.4
 * @serial exclude
 */
public class PreferenceChangeEvent extends java.util.EventObject {

    /** {@collect.stats}
     *      
* {@description.open}
     * Key of the preference that changed.

     * {@description.close}     *
     * @serial
     */
    private String key;

    /** {@collect.stats}
     *      
* {@description.open}
     * New value for preference, or <tt>null</tt> if it was removed.

     * {@description.close}     *
     * @serial
     */
    private String newValue;

    /** {@collect.stats}
     *      
* {@description.open}
     * Constructs a new <code>PreferenceChangeEvent</code> instance.

     * {@description.close}     *
     * @param node  The Preferences node that emitted the event.
     * @param key  The key of the preference that was changed.
     * @param newValue  The new value of the preference, or <tt>null</tt>
     *                  if the preference is being removed.
     */
    public PreferenceChangeEvent(Preferences node, String key,
                                 String newValue) {
        super(node);
        this.key = key;
        this.newValue = newValue;
    }

    /** {@collect.stats}
     *      
* {@description.open}
     * Returns the preference node that emitted the event.

     * {@description.close}     *
     * @return  The preference node that emitted the event.
     */
    public Preferences getNode() {
        return (Preferences) getSource();
    }

    /** {@collect.stats}
     *      
* {@description.open}
     * Returns the key of the preference that was changed.

     * {@description.close}     *
     * @return  The key of the preference that was changed.
     */
    public String getKey() {
        return key;
    }

    /** {@collect.stats}
     *      
* {@description.open}
     * Returns the new value for the preference.

     * {@description.close}     *
     * @return  The new value for the preference, or <tt>null</tt> if the
     *          preference was removed.
     */
    public String getNewValue() {
        return newValue;
    }

    /** {@collect.stats}
     *      
* {@description.open}
     * Throws NotSerializableException, since NodeChangeEvent objects
     * are not intended to be serializable.

     * {@description.close}     */
     private void writeObject(java.io.ObjectOutputStream out)
                                               throws NotSerializableException {
         throw new NotSerializableException("Not serializable.");
     }

    /** {@collect.stats}
     *      
* {@description.open}
     * Throws NotSerializableException, since PreferenceChangeEvent objects
     * are not intended to be serializable.

     * {@description.close}     */
     private void readObject(java.io.ObjectInputStream in)
                                               throws NotSerializableException {
         throw new NotSerializableException("Not serializable.");
     }

    // Defined so that this class isn't flagged as a potential problem when
    // searches for missing serialVersionUID fields are done.
    private static final long serialVersionUID = 793724513368024975L;
}
