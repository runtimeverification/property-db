/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package java.io;

/**{@collect.stats}
 * {@description.open}
 * An interface for filtering {@link File} objects based on their names
 * or other information.
 * {@description.close}
 *
 * @see File#listFiles(FileFilter)
 */
public interface FileFilter {

    /**{@collect.stats}
     * {@description.open}
     * Indicating whether a specific file should be included in a pathname list.
     * {@description.close}
     *
     * @param pathname
     *            the abstract file to check.
     * @return {@code true} if the file should be included, {@code false}
     *         otherwise.
     */
    public abstract boolean accept(File pathname);
}
