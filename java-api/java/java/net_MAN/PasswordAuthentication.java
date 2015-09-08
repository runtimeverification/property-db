/*
 * Copyright (c) 1997, 2013, Oracle and/or its affiliates. All rights reserved.
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
