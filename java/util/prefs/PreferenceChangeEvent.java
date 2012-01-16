/*
 * Copyright (c) 2000, 2003, Oracle and/or its affiliates. All rights reserved.
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

package java.util.prefs;

import java.io.NotSerializableException;

/** {@collect.stats} 
 * {@description.open}
 * An event emitted by a <tt>Preferences</tt> node to indicate that
 * a preference has been added, removed or has had its value changed.<p>
 *
 * Note, that although PreferenceChangeEvent inherits Serializable interface
 * from EventObject, it is not intended to be Serializable. Appropriate
 * serialization methods are implemented to throw NotSerializableException.
 * {@description.close}
 *
 * @author  Josh Bloch
 * @see Preferences
 * @see PreferenceChangeListener
 * @see NodeChangeEvent
 * @since   1.4
 * @serial exclude
 */
public class PreferenceChangeEvent extends java.util.EventObject {

    /** {@collect.stats} 
     * {@description.open}
     * Key of the preference that changed.
     * {@description.close}
     *
     * @serial
     */
    private String key;

    /** {@collect.stats} 
     * {@description.open}
     * New value for preference, or <tt>null</tt> if it was removed.
     * {@description.close}
     *
     * @serial
     */
    private String newValue;

    /** {@collect.stats} 
     * {@description.open}
     * Constructs a new <code>PreferenceChangeEvent</code> instance.
     * {@description.close}
     *
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
     * {@description.open}
     * Returns the preference node that emitted the event.
     * {@description.close}
     *
     * @return  The preference node that emitted the event.
     */
    public Preferences getNode() {
        return (Preferences) getSource();
    }

    /** {@collect.stats} 
     * {@description.open}
     * Returns the key of the preference that was changed.
     * {@description.close}
     *
     * @return  The key of the preference that was changed.
     */
    public String getKey() {
        return key;
    }

    /** {@collect.stats} 
     * {@description.open}
     * Returns the new value for the preference.
     * {@description.close}
     *
     * @return  The new value for the preference, or <tt>null</tt> if the
     *          preference was removed.
     */
    public String getNewValue() {
        return newValue;
    }

    /** {@collect.stats} 
     * {@description.open}
     * Throws NotSerializableException, since NodeChangeEvent objects
     * are not intended to be serializable.
     * {@description.close}
     */
     private void writeObject(java.io.ObjectOutputStream out)
                                               throws NotSerializableException {
         throw new NotSerializableException("Not serializable.");
     }

    /** {@collect.stats} 
     * {@description.open}
     * Throws NotSerializableException, since PreferenceChangeEvent objects
     * are not intended to be serializable.
     * {@description.close}
     */
     private void readObject(java.io.ObjectInputStream in)
                                               throws NotSerializableException {
         throw new NotSerializableException("Not serializable.");
     }

    // Defined so that this class isn't flagged as a potential problem when
    // searches for missing serialVersionUID fields are done.
    private static final long serialVersionUID = 793724513368024975L;
}
