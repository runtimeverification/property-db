/*
 * Copyright (C) 2009 The Android Open Source Project
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

import java.util.Map;

/** {@collect.stats}
 * {@description.open}
 * This class is used to manage the JavaScript storage APIs provided by the
 * {@link WebView}. It manages the Application Cache API, the Web SQL Database
 * API and the HTML5 Web Storage API.
 *
 * The Application Cache API provides a mechanism to create and maintain an
 * application cache to power offline Web applications. Use of the Application
 * Cache API can be attributed to an origin {@link WebStorage.Origin}, however
 * it is not possible to set per-origin quotas. Note that there can be only
 * one application cache per application.
 *
 * The Web SQL Database API provides storage which is private to a given origin.
 * Similar to the Application Cache, use of the Web SQL Database can be attributed
 * to an origin. It is also possible to set per-origin quotas.
 * {@description.close}
 */
public class WebStorage {

    /** {@collect.stats}
	 * {@description.open}
     * Encapsulates a callback function which is used to provide a new quota
     * for a JavaScript storage API.
     * See
     * {@link WebChromeClient#onExceededDatabaseQuota} and
     * {@link WebChromeClient#onReachedMaxAppCacheSize}.
	 * {@description.close}
	 *
	 * {@description.open}
	 * We primarily want this to allow us to call back the sleeping WebCore
	 * thread from outside the WebViewCore class (as the native call is
	 * private).
	 * {@description.close}
	 * {@property.open}
	 * It is imperative that the setDatabaseQuota method is
	 * executed after a decision to either allow or deny new quota is made,
	 * otherwise the WebCore thread will remain asleep.
	 * {@property.close}
     */
    public interface QuotaUpdater {
        /** {@collect.stats}
		 * {@description.open}
         * Provides a new quota, specified in bytes.
		 * {@description.close}
         *
         * @param newQuota the new quota, in bytes
         */
        public void updateQuota(long newQuota);
    };

    /** {@collect.stats}
	 * {@description.open}
     * This class encapsulates information about the amount of storage
     * currently used by an origin for the JavaScript storage APIs.
     * An origin comprises the host, scheme and port of a URI.
     * See {@link WebStorage} for details.
	 * {@description.close}
     */
    public static class Origin {
        private String mOrigin = null;
        private long mQuota = 0;
        private long mUsage = 0;

        /** {@collect.stats} @hide */
        protected Origin(String origin, long quota, long usage) {
            mOrigin = origin;
            mQuota = quota;
            mUsage = usage;
        }

        /** {@collect.stats} @hide */
        protected Origin(String origin, long quota) {
            mOrigin = origin;
            mQuota = quota;
        }

        /** {@collect.stats} @hide */
        protected Origin(String origin) {
            mOrigin = origin;
        }

        /** {@collect.stats}
		 * {@description.open}
         * Gets the string representation of this origin.
		 * {@description.close}
         *
         * @return the string representation of this origin
         */
        // An origin string is created using WebCore::SecurityOrigin::toString().
        // Note that WebCore::SecurityOrigin uses 0 (which is not printed) for
        // the port if the port is the default for the protocol. Eg
        // http://www.google.com and http://www.google.com:80 both record a port
        // of 0 and hence toString() == 'http://www.google.com' for both.
        public String getOrigin() {
            return mOrigin;
        }

        /** {@collect.stats}
		 * {@description.open}
         * Gets the quota for this origin, for the Web SQL Database API, in
         * bytes. If this origin does not use the Web SQL Database API, this
         * quota will be set to zero.
		 * {@description.close}
         *
         * @return the quota, in bytes
         */
        public long getQuota() {
            return mQuota;
        }

        /** {@collect.stats}
		 * {@description.open}
         * Gets the total amount of storage currently being used by this origin,
         * for all JavaScript storage APIs, in bytes.
		 * {@description.close}
         *
         * @return the total amount of storage, in bytes
         */
        public long getUsage() {
            return mUsage;
        }
    }

    /*
     * When calling getOrigins(), getUsageForOrigin() and getQuotaForOrigin(),
     * we need to get the values from WebCore, but we cannot block while doing so
     * as we used to do, as this could result in a full deadlock (other WebCore
     * messages received while we are still blocked here, see http://b/2127737).
     *
     * We have to do everything asynchronously, by providing a callback function.
     * We post a message on the WebCore thread (mHandler) that will get the result
     * from WebCore, and we post it back on the UI thread (using mUIHandler).
     * We can then use the callback function to return the value.
     */

    /** {@collect.stats}
	 * {@description.open}
     * Gets the origins currently using either the Application Cache or Web SQL
     * Database APIs. This method operates asynchronously, with the result
     * being provided via a {@link ValueCallback}. The origins are provided as
     * a map, of type {@code Map<String, WebStorage.Origin>}, from the string
     * representation of the origin to a {@link WebStorage.Origin} object.
	 * {@description.close}
     */
    public void getOrigins(ValueCallback<Map> callback) {
        // Must be a no-op for backward compatibility: see the hidden constructor for reason.
    }

    /** {@collect.stats}
	 * {@description.open}
     * Gets the amount of storage currently being used by both the Application
     * Cache and Web SQL Database APIs by the given origin. The amount is given
     * in bytes and the origin is specified using its string representation.
     * This method operates asynchronously, with the result being provided via
     * a {@link ValueCallback}.
	 * {@description.close}
     */
    public void getUsageForOrigin(String origin, ValueCallback<Long> callback) {
        // Must be a no-op for backward compatibility: see the hidden constructor for reason.
    }

    /** {@collect.stats}
	 * {@description.open}
     * Gets the storage quota for the Web SQL Database API for the given origin.
     * The quota is given in bytes and the origin is specified using its string
     * representation. This method operates asynchronously, with the result
     * being provided via a {@link ValueCallback}. Note that a quota is not
     * enforced on a per-origin basis for the Application Cache API.
	 * {@description.close}
     */
    public void getQuotaForOrigin(String origin, ValueCallback<Long> callback) {
        // Must be a no-op for backward compatibility: see the hidden constructor for reason.
    }

    /** {@collect.stats}
	 * {@description.open}
     * Sets the storage quota for the Web SQL Database API for the given origin.
     * The quota is specified in bytes and the origin is specified using its string
     * representation. Note that a quota is not enforced on a per-origin basis
     * for the Application Cache API.
	 * {@description.close}
     */
    public void setQuotaForOrigin(String origin, long quota) {
        // Must be a no-op for backward compatibility: see the hidden constructor for reason.
    }

    /** {@collect.stats}
	 * {@description.open}
     * Clears the storage currently being used by both the Application Cache and
     * Web SQL Database APIs by the given origin. The origin is specified using
     * its string representation.
	 * {@description.close}
     */
    public void deleteOrigin(String origin) {
        // Must be a no-op for backward compatibility: see the hidden constructor for reason.
    }

    /** {@collect.stats}
	 * {@description.open}
     * Clears all storage currently being used by the JavaScript storage APIs.
     * This includes the Application Cache, Web SQL Database and the HTML5 Web
     * Storage APIs.
	 * {@description.close}
     */
    public void deleteAllData() {
        // Must be a no-op for backward compatibility: see the hidden constructor for reason.
    }

    /** {@collect.stats}
	 * {@description.open}
     * Gets the singleton instance of this class.
	 * {@description.close}
     *
     * @return the singleton {@link WebStorage} instance
     */
    public static WebStorage getInstance() {
      return WebViewFactory.getProvider().getWebStorage();
    }

    /** {@collect.stats}
     * This class should not be instantiated directly, applications must only use
     * {@link #getInstance()} to obtain the instance.
     * Note this constructor was erroneously public and published in SDK levels prior to 16, but
     * applications using it would receive a non-functional instance of this class (there was no
     * way to call createHandler() and createUIHandler(), so it would not work).
     * @hide
     */
    public WebStorage() {}
}
