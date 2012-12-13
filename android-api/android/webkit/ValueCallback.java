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

/** {@collect.stats}
 * {@property.open}
 * A callback interface used to provide values asynchronously.
 * {@property.close}
 */
public interface ValueCallback<T> {
    /** {@collect.stats}
	 * {@property.open}
     * Invoked when the value is available.
	 * {@property.close}
     * @param value The value.
     */
    public void onReceiveValue(T value);
};