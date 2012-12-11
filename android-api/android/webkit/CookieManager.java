/*
 * Copyright (C) 2006 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package android.webkit;

import android.net.WebAddress;

/** {@collect.stats}
 * {@description.open}
 * Manages the cookies used by an application's {@link WebView} instances.
 * Cookies are manipulated according to RFC2109.
 * {@description.close}
 */
public class CookieManager {
    /** {@collect.stats}
     * @hide Only for use by WebViewProvider implementations
     */
    protected CookieManager() {
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException("doesn't implement Cloneable");
    }

    /** {@collect.stats}
	 * {@description.open}
     * Gets the singleton CookieManager instance.
	 * {@description.close}
	 * {@property.open runtime formal:android.webkit.CookieManager_GetInstance}
	 * If this method is used
     * before the application instantiates a {@link WebView} instance,
     * {@link CookieSyncManager#createInstance CookieSyncManager.createInstance(Context)}
     * must be called first.
	 * {@property.close}
     *
     * @return the singleton CookieManager instance
     */
    public static synchronized CookieManager getInstance() {
        return WebViewFactory.getProvider().getCookieManager();
    }

    /** {@collect.stats}
	 * {@description.open}
     * Sets whether the application's {@link WebView} instances should send and
     * accept cookies.
	 * {@description.close}
     *
     * @param accept whether {@link WebView} instances should send and accept
     *               cookies
     */
    public synchronized void setAcceptCookie(boolean accept) {
        throw new MustOverrideException();
    }

    /** {@collect.stats}
	 * {@description.open}
     * Gets whether the application's {@link WebView} instances send and accept
     * cookies.
	 * {@description.close}
     *
     * @return true if {@link WebView} instances send and accept cookies
     */
    public synchronized boolean acceptCookie() {
        throw new MustOverrideException();
    }

	/** {@collect.stats}
	 * {@description.open}
	 * Sets a cookie for the given URL. Any existing cookie with the same host,
	 * path and name will be replaced with the new cookie.
	 * {@description.close}
	 * {@property.open}
	 * The cookie being set
     * must not have expired and must not be a session cookie, otherwise it
     * will be ignored.
	 * {@property.close}
     *
     * @param url the URL for which the cookie is set
     * @param value the cookie as a string, using the format of the 'Set-Cookie'
     *              HTTP response header
     */
    public void setCookie(String url, String value) {
        throw new MustOverrideException();
    }

    /** {@collect.stats}
	 * {@description.open}
     * Gets the cookies for the given URL.
	 * {@description.close}
     *
     * @param url the URL for which the cookies are requested
     * @return value the cookies as a string, using the format of the 'Cookie'
     *               HTTP request header
     */
    public String getCookie(String url) {
        throw new MustOverrideException();
    }

    /** {@collect.stats}
	 * {@description.open}
     * See {@link #getCookie(String)}.
	 * {@description.close}
     *
     * @param url the URL for which the cookies are requested
     * @param privateBrowsing whether to use the private browsing cookie jar
     * @return value the cookies as a string, using the format of the 'Cookie'
     *               HTTP request header
     * @hide Used by Browser, no intention to publish.
     */
    public String getCookie(String url, boolean privateBrowsing) {
        throw new MustOverrideException();
    }

    /** {@collect.stats}
	 * {@description.open}
     * Gets cookie(s) for a given uri so that it can be set to "cookie:" in http
     * request header.
	 * {@description.close}
     *
     * @param uri the WebAddress for which the cookies are requested
     * @return value the cookies as a string, using the format of the 'Cookie'
     *               HTTP request header
     * @hide Used by RequestHandle, no intention to publish.
     */
    public synchronized String getCookie(WebAddress uri) {
        throw new MustOverrideException();
    }

    /** {@collect.stats}
	 * {@description.open}
     * Removes all session cookies, which are cookies without an expiration
     * date.
	 * {@description.close}
     */
    public void removeSessionCookie() {
        throw new MustOverrideException();
    }

    /** {@collect.stats}
	 * {@description.open}
     * Removes all cookies.
	 * {@description.close}
     */
    public void removeAllCookie() {
        throw new MustOverrideException();
    }

    /** {@collect.stats}
	 * {@description.open}
     * Gets whether there are stored cookies.
	 * {@description.close}
     *
     * @return true if there are stored cookies
     */
    public synchronized boolean hasCookies() {
        throw new MustOverrideException();
    }

    /** {@collect.stats}
	 * {@description.open}
     * See {@link #hasCookies()}.
	 * {@description.close}
     *
     * @param privateBrowsing whether to use the private browsing cookie jar
     * @hide Used by Browser, no intention to publish.
     */
    public synchronized boolean hasCookies(boolean privateBrowsing) {
        throw new MustOverrideException();
    }

    /** {@collect.stats}
	 * {@description.open}
     * Removes all expired cookies.
	 * {@description.close}
     */
    public void removeExpiredCookie() {
        throw new MustOverrideException();
    }

    /** {@collect.stats}
	 * {@description.open}
     * Flushes all cookies managed by the Chrome HTTP stack to flash.
	 * {@description.close}
     *
     * @hide Package level api, called from CookieSyncManager
     */
    protected void flushCookieStore() {
        throw new MustOverrideException();
    }

    /** {@collect.stats}
	 * {@description.open}
     * Gets whether the application's {@link WebView} instances send and accept
     * cookies for file scheme URLs.
	 * {@description.close}
     *
     * @return true if {@link WebView} instances send and accept cookies for
     *         file scheme URLs
     */
    // Static for backward compatibility.
    public static boolean allowFileSchemeCookies() {
        return getInstance().allowFileSchemeCookiesImpl();
    }

    /** {@collect.stats}
	 * {@description.open}
     * Implements {@link #allowFileSchemeCookies()}.
	 * {@description.close}
     *
     * @hide Only for use by WebViewProvider implementations
     */
    protected boolean allowFileSchemeCookiesImpl() {
        throw new MustOverrideException();
    }

    /** {@collect.stats}
	 * {@description.open}
     * Sets whether the application's {@link WebView} instances should send and
     * accept cookies for file scheme URLs.
	 * {@description.close}
	 * {@property.open}
     * Use of cookies with file scheme URLs is potentially insecure. Do not use
     * this feature unless you can be sure that no unintentional sharing of
     * cookie data can take place.
     * <p>
     * Note that calls to this method will have no effect if made after a
     * {@link WebView} or CookieManager instance has been created.
	 * {@property.close}
     */
    // Static for backward compatibility.
    public static void setAcceptFileSchemeCookies(boolean accept) {
        getInstance().setAcceptFileSchemeCookiesImpl(accept);
    }

    /** {@collect.stats}
	 * {@description.open}
     * Implements {@link #setAcceptFileSchemeCookies(boolean)}.
	 * {@description.close}
     *
     * @hide Only for use by WebViewProvider implementations
     */
    protected void setAcceptFileSchemeCookiesImpl(boolean accept) {
        throw new MustOverrideException();
    }
}
