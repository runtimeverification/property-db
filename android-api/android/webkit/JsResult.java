/*
 * Copyright (C) 2007 The Android Open Source Project
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

/** {@collect.stats}
 * {@description.open}
 * An instance of this class is passed as a parameter in various {@link WebChromeClient} action
 * notifications. The object is used as a handle onto the underlying JavaScript-originated request,
 * and provides a means for the client to indicate whether this action should proceed.
 * {@description.close}
 */
public class JsResult {
    /** {@collect.stats}
	 * {@description.open}
     * Callback interface, implemented by the WebViewProvider implementation to receive
     * notifications when the JavaScript result represented by a JsResult instance has
	 * {@description.close}
     * @hide Only for use by WebViewProvider implementations
     */
    public interface ResultReceiver {
        public void onJsResultComplete(JsResult result);
    }
    // This is the caller of the prompt and is the object that is waiting.
    private final ResultReceiver mReceiver;
    // This is a basic result of a confirm or prompt dialog.
    private boolean mResult;

    /** {@collect.stats}
	 * {@description.open}
     * Handle the result if the user cancelled the dialog.
	 * {@description.close}
     */
    public final void cancel() {
        mResult = false;
        wakeUp();
    }

    /** {@collect.stats}
	 * {@description.open}
     * Handle a confirmation response from the user.
	 * {@description.close}
     */
    public final void confirm() {
        mResult = true;
        wakeUp();
    }

    /** {@collect.stats}
     * @hide Only for use by WebViewProvider implementations
     */
    public JsResult(ResultReceiver receiver) {
        mReceiver = receiver;
    }

    /** {@collect.stats}
     * @hide Only for use by WebViewProvider implementations
     */
    public final boolean getResult() {
        return mResult;
    }

    /* Notify the caller that the JsResult has completed */
    private final void wakeUp() {
        mReceiver.onJsResultComplete(this);
    }
}
