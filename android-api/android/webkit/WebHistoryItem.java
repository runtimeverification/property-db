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

import android.graphics.Bitmap;

/** {@collect.stats}
 * {@description.open}
 * A convenience class for accessing fields in an entry in the back/forward list
 * of a WebView. Each WebHistoryItem is a snapshot of the requested history
 * item. Each history item may be updated during the load of a page.
 * {@description.close}
 * @see WebBackForwardList
 */
public class WebHistoryItem implements Cloneable {

    /** {@collect.stats}
     * @hide
     */
    public WebHistoryItem() {
    }

    /** {@collect.stats}
	 * {@description.open}
     * Return an identifier for this history item. If an item is a copy of
     * another item, the identifiers will be the same even if they are not the
     * same object.
	 * {@description.close}
     * @return The id for this item.
     * @deprecated This method is now obsolete.
     * @hide Since API level {@link android.os.Build.VERSION_CODES#JELLY_BEAN_MR1}
     */
    @Deprecated
    public int getId() {
        throw new MustOverrideException();
    }

    /** {@collect.stats}
	 * {@description.open}
     * Return the url of this history item. The url is the base url of this
     * history item. See getTargetUrl() for the url that is the actual target of
     * this history item.
	 * {@description.close}
     * @return The base url of this history item.
	 * {@description.open}
     * Note: The VM ensures 32-bit atomic read/write operations so we don't have
     * to synchronize this method.
	 * {@description.close}
     */
    public String getUrl() {
        throw new MustOverrideException();
    }

    /** {@collect.stats}
	 * {@description.open}
     * Return the original url of this history item. This was the requested
     * url, the final url may be different as there might have been 
     * redirects while loading the site.
	 * {@description.close}
     * @return The original url of this history item.
     */
    public String getOriginalUrl() {
        throw new MustOverrideException();
    }
    
    /** {@collect.stats}
	 * {@description.open}
     * Return the document title of this history item.
	 * {@description.close}
     * @return The document title of this history item.
	 * {@description.open}
     * Note: The VM ensures 32-bit atomic read/write operations so we don't have
     * to synchronize this method.
	 * {@description.close}
     */
    public String getTitle() {
        throw new MustOverrideException();
    }

    /** {@collect.stats}
	 * {@description.open}
     * Return the favicon of this history item or null if no favicon was found.
	 * {@description.close}
     * @return A Bitmap containing the favicon for this history item or null.
	 * {@description.open}
     * Note: The VM ensures 32-bit atomic read/write operations so we don't have
     * to synchronize this method.
	 * {@description.close}
     */
    public Bitmap getFavicon() {
        throw new MustOverrideException();
    }

    /** {@collect.stats}
	 * {@description.open}
     * Clone the history item for use by clients of WebView.
	 * {@description.close}
     */
    protected synchronized WebHistoryItem clone() {
        throw new MustOverrideException();
    }

}
