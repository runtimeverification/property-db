/*
 * Copyright (C) 2010 The Android Open Source Project
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

import java.io.InputStream;

/** {@collect.stats}
 * {@description.open}
 * Encapsulates a resource response. Applications can return an instance of this
 * class from {@link WebViewClient#shouldInterceptRequest} to provide a custom
 * response when the WebView requests a particular resource.
 * {@description.close}
 */
public class WebResourceResponse {
    // Accessed by jni, do not rename without modifying the jni code.
    private String mMimeType;
    private String mEncoding;
    private InputStream mInputStream;

    /** {@collect.stats}
	 * {@description.open}
     * Constructs a resource response with the given MIME type, encoding, and
     * input stream.
	 * {@description.close}
	 * {@property.open static}
	 * Callers must implement
     * {@link InputStream#read(byte[]) InputStream.read(byte[])} for the input
     * stream.
	 * {@property.close}
     *
     * @param mimeType the resource response's MIME type, for example text/html
     * @param encoding the resource response's encoding
     * @param data the input stream that provides the resource response's data
     */
    public WebResourceResponse(String mimeType, String encoding,
            InputStream data) {
        mMimeType = mimeType;
        mEncoding = encoding;
        mInputStream = data;
    }

    /** {@collect.stats}
	 * {@description.open}
     * Sets the resource response's MIME type, for example text/html.
	 * {@description.close}
     *
     * @param mimeType the resource response's MIME type
     */
    public void setMimeType(String mimeType) {
        mMimeType = mimeType;
    }

    /** {@collect.stats}
	 * {@description.open}
     * Gets the resource response's MIME type.
	 * {@description.close}
     *
     * @return the resource response's MIME type
     */
    public String getMimeType() {
        return mMimeType;
    }

    /** {@collect.stats}
	 * {@description.open}
     * Sets the resource response's encoding, for example UTF-8. This is used
     * to decode the data from the input stream.
	 * {@description.close}
     *
     * @param encoding the resource response's encoding
     */
    public void setEncoding(String encoding) {
        mEncoding = encoding;
    }

    /** {@collect.stats}
	 * {@description.open}
     * Gets the resource response's encoding.
	 * {@description.close}
     *
     * @return the resource response's encoding
     */
    public String getEncoding() {
        return mEncoding;
    }

    /** {@collect.stats}
	 * {@description.open}
     * Sets the input stream that provides the resource response's data.
	 * {@description.close}
	 * {@property.open static}
	 * Callers
     * must implement {@link InputStream#read(byte[]) InputStream.read(byte[])}.
	 * {@property.close}
     *
     * @param data the input stream that provides the resource response's data
     */
    public void setData(InputStream data) {
        mInputStream = data;
    }

    /** {@collect.stats}
	 * {@description.open}
     * Gets the input stream that provides the resource respone's data.
	 * {@description.close}
     *
     * @return the input stream that provides the resource response's data
     */
    public InputStream getData() {
        return mInputStream;
    }
}
