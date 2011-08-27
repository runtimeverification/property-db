/*
 * Copyright (c) 1997, 2007, Oracle and/or its affiliates. All rights reserved.
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

package java.awt.dnd.peer;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DropTarget;
import java.awt.dnd.InvalidDnDOperationException;

/** {@collect.stats}
 * <p>
 * This interface is exposed by the underlying window system platform to
 * enable control of platform DnD operations
 * </p>
 *
 * @since 1.2
 *
 */

public interface DropTargetContextPeer {

    /** {@collect.stats}
     * update the peer's notion of the Target's actions
     */

    void setTargetActions(int actions);

    /** {@collect.stats}
     * get the current Target actions
     */

    int getTargetActions();

    /** {@collect.stats}
     * get the DropTarget associated with this peer
     */

    DropTarget getDropTarget();

    /** {@collect.stats}
     * get the (remote) DataFlavors from the peer
     */

    DataFlavor[] getTransferDataFlavors();

    /** {@collect.stats}
     * get an input stream to the remote data
     */

    Transferable getTransferable() throws InvalidDnDOperationException;

    /** {@collect.stats}
     * @return if the DragSource Transferable is in the same JVM as the Target
     */

    boolean isTransferableJVMLocal();

    /** {@collect.stats}
     * accept the Drag
     */

    void acceptDrag(int dragAction);

    /** {@collect.stats}
     * reject the Drag
     */

    void rejectDrag();

    /** {@collect.stats}
     * accept the Drop
     */

    void acceptDrop(int dropAction);

    /** {@collect.stats}
     * reject the Drop
     */

    void rejectDrop();

    /** {@collect.stats}
     * signal complete
     */

    void dropComplete(boolean success);

}
