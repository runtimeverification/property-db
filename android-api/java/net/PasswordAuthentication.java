/*
 * Copyright (c) 1997, 2001, Oracle and/or its affiliates. All rights reserved.
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

package java.net;


/** {@collect.stats} 
 * {@description.open}
 * The class PasswordAuthentication is a data holder that is used by
 * Authenticator.  It is simply a repository for a user name and a password.
 * {@description.close}
 *
 * @see java.net.Authenticator
 * @see java.net.Authenticator#getPasswordAuthentication()
 *
 * @author  Bill Foote
 * @since   1.2
 */

public final class PasswordAuthentication {

    private String userName;
    private char[] password;

    /** {@collect.stats} 
     * {@description.open}
     * Creates a new <code>PasswordAuthentication</code> object from the given
     * user name and password.
     *
     * <p> Note that the given user password is cloned before it is stored in
     * the new <code>PasswordAuthentication</code> object.
     * {@description.close}
     *
     * @param userName the user name
     * @param password the user's password
     */
    public PasswordAuthentication(String userName, char[] password) {
        this.userName = userName;
        this.password = password.clone();
    }

    /** {@collect.stats} 
     * {@description.open}
     * Returns the user name.
     * {@description.close}
     *
     * @return the user name
     */
    public String getUserName() {
        return userName;
    }

    /** {@collect.stats} 
     * {@description.open}
     * Returns the user password.
     * {@description.close}
     *
     * {@property.open runtime formal:java.net.PasswordAuthentication_FillZeroPassword}
     * <p> Note that this method returns a reference to the password. It is
     * the caller's responsibility to zero out the password information after
     * it is no longer needed.
     * {@property.close}
     *
     * @return the password
     */
    public char[] getPassword() {
        return password;
    }
}
