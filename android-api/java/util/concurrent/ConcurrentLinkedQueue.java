/*
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

/*
 * This file is available under and governed by the GNU General Public
 * License version 2 only, as published by the Free Software Foundation.
 * However, the following notice accompanied the original version of this
 * file:
 *
 * Written by Doug Lea with assistance from members of JCP JSR-166
 * Expert Group and released to the public domain, as explained at
 * http://creativecommons.org/licenses/publicdomain
 */

package java.util.concurrent;
import java.util.*;
import java.util.concurrent.atomic.*;


/** {@collect.stats} 
 * {@description.open}
 * An unbounded thread-safe {@linkplain Queue queue} based on linked nodes.
 * This queue orders elements FIFO (first-in-first-out).
 * The <em>head</em> of the queue is that element that has been on the
 * queue the longest time.
 * The <em>tail</em> of the queue is that element that has been on the
 * queue the shortest time. New elements
 * are inserted at the tail of the queue, and the queue retrieval
 * operations obtain elements at the head of the queue.
 * A <tt>ConcurrentLinkedQueue</tt> is an appropriate choice when
 * many threads will share access to a common collection.
 * This queue does not permit <tt>null</tt> elements.
 *
 * <p>This implementation employs an efficient &quot;wait-free&quot;
 * algorithm based on one described in <a
 * href="http://www.cs.rochester.edu/u/michael/PODC96.html"> Simple,
 * Fast, and Practical Non-Blocking and Blocking Concurrent Queue
 * Algorithms</a> by Maged M. Michael and Michael L. Scott.
 *
 * <p>Beware that, unlike in most collections, the <tt>size</tt> method
 * is <em>NOT</em> a constant-time operation. Because of the
 * asynchronous nature of these queues, determining the current number
 * of elements requires a traversal of the elements.
 *
 * <p>This class and its iterator implement all of the
 * <em>optional</em> methods of the {@link Collection} and {@link
 * Iterator} interfaces.
 *
 * <p>Memory consistency effects: As with other concurrent
 * collections, actions in a thread prior to placing an object into a
 * {@code ConcurrentLinkedQueue}
 * <a href="package-summary.html#MemoryVisibility"><i>happen-before</i></a>
 * actions subsequent to the access or removal of that element from
 * the {@code ConcurrentLinkedQueue} in another thread.
 *
 * <p>This class is a member of the
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 * Java Collections Framework</a>.
 * {@description.close}
 *
 * @since 1.5
 * @author Doug Lea
 * @param <E> the type of elements held in this collection
 *
 */
public class ConcurrentLinkedQueue<E> extends AbstractQueue<E>
        implements Queue<E>, java.io.Serializable {
    private static final long serialVersionUID = 196745693267521676L;

    /*
     * This is a straight adaptation of Michael & Scott algorithm.
     * For explanation, read the paper.  The only (minor) algorithmic
     * difference is that this version supports lazy deletion of
     * internal nodes (method remove(Object)) -- remove CAS'es item
     * fields to null. The normal queue operations unlink but then
     * pass over nodes with null item fields. Similarly, iteration
     * methods ignore those with nulls.
     *
     * Also note that like most non-blocking algorithms in this
     * package, this implementation relies on the fact that in garbage
     * collected systems, there is no possibility of ABA problems due
     * to recycled nodes, so there is no need to use "counted
     * pointers" or related techniques seen in versions used in
     * non-GC'ed settings.
     */

    private static class Node<E> {
        private volatile E item;
        private volatile Node<E> next;

        private static final
            AtomicReferenceFieldUpdater<Node, Node>
            nextUpdater =
            AtomicReferenceFieldUpdater.newUpdater
            (Node.class, Node.class, "next");
        private static final
            AtomicReferenceFieldUpdater<Node, Object>
            itemUpdater =
            AtomicReferenceFieldUpdater.newUpdater
            (Node.class, Object.class, "item");

        Node(E x) { item = x; }

        Node(E x, Node<E> n) { item = x; next = n; }

        E getItem() {
            return item;
        }

        boolean casItem(E cmp, E val) {
            return itemUpdater.compareAndSet(this, cmp, val);
        }

        void setItem(E val) {
            itemUpdater.set(this, val);
        }

        Node<E> getNext() {
            return next;
        }

        boolean casNext(Node<E> cmp, Node<E> val) {
            return nextUpdater.compareAndSet(this, cmp, val);
        }

        void setNext(Node<E> val) {
            nextUpdater.set(this, val);
        }

    }

    private static final
        AtomicReferenceFieldUpdater<ConcurrentLinkedQueue, Node>
        tailUpdater =
        AtomicReferenceFieldUpdater.newUpdater
        (ConcurrentLinkedQueue.class, Node.class, "tail");
    private static final
        AtomicReferenceFieldUpdater<ConcurrentLinkedQueue, Node>
        headUpdater =
        AtomicReferenceFieldUpdater.newUpdater
        (ConcurrentLinkedQueue.class,  Node.class, "head");

    private boolean casTail(Node<E> cmp, Node<E> val) {
        return tailUpdater.compareAndSet(this, cmp, val);
    }

    private boolean casHead(Node<E> cmp, Node<E> val) {
        return headUpdater.compareAndSet(this, cmp, val);
    }


    /** {@collect.stats} 
     * {@description.open}
     * Pointer to header node, initialized to a dummy node.  The first
     * actual node is at head.getNext().
     * {@description.close}
     */
    private transient volatile Node<E> head = new Node<E>(null, null);

    /** {@collect.stats}
     * {@description.open}
     * Pointer to last node on list 
     * {@description.close}
     */
    private transient volatile Node<E> tail = head;


    /** {@collect.stats} 
     * {@description.open}
     * Creates a <tt>ConcurrentLinkedQueue</tt> that is initially empty.
     * {@description.close}
     */
    public ConcurrentLinkedQueue() {}

    /** {@collect.stats} 
     * {@description.open}
     * Creates a <tt>ConcurrentLinkedQueue</tt>
     * initially containing the elements of the given collection,
     * added in traversal order of the collection's iterator.
     * {@description.close}
     * @param c the collection of elements to initially contain
     * @throws NullPointerException if the specified collection or any
     *         of its elements are null
     */
    public ConcurrentLinkedQueue(Collection<? extends E> c) {
        for (Iterator<? extends E> it = c.iterator(); it.hasNext();)
            add(it.next());
    }

    // Have to override just to update the javadoc

    /** {@collect.stats} 
     * {@description.open}
     * Inserts the specified element at the tail of this queue.
     * {@description.close}
     *
     * @return <tt>true</tt> (as specified by {@link Collection#add})
     * @throws NullPointerException if the specified element is null
     */
    public boolean add(E e) {
        return offer(e);
    }

    /** {@collect.stats} 
     * {@description.open}
     * Inserts the specified element at the tail of this queue.
     * {@description.close}
     *
     * @return <tt>true</tt> (as specified by {@link Queue#offer})
     * @throws NullPointerException if the specified element is null
     */
    public boolean offer(E e) {
        if (e == null) throw new NullPointerException();
        Node<E> n = new Node<E>(e, null);
        for (;;) {
            Node<E> t = tail;
            Node<E> s = t.getNext();
            if (t == tail) {
                if (s == null) {
                    if (t.casNext(s, n)) {
                        casTail(t, n);
                        return true;
                    }
                } else {
                    casTail(t, s);
                }
            }
        }
    }

    public E poll() {
        for (;;) {
            Node<E> h = head;
            Node<E> t = tail;
            Node<E> first = h.getNext();
            if (h == head) {
                if (h == t) {
                    if (first == null)
                        return null;
                    else
                        casTail(t, first);
                } else if (casHead(h, first)) {
                    E item = first.getItem();
                    if (item != null) {
                        first.setItem(null);
                        return item;
                    }
                    // else skip over deleted item, continue loop,
                }
            }
        }
    }

    public E peek() { // same as poll except don't remove item
        for (;;) {
            Node<E> h = head;
            Node<E> t = tail;
            Node<E> first = h.getNext();
            if (h == head) {
                if (h == t) {
                    if (first == null)
                        return null;
                    else
                        casTail(t, first);
                } else {
                    E item = first.getItem();
                    if (item != null)
                        return item;
                    else // remove deleted node and continue
                        casHead(h, first);
                }
            }
        }
    }

    /** {@collect.stats} 
     * {@description.open}
     * Returns the first actual (non-header) node on list.  This is yet
     * another variant of poll/peek; here returning out the first
     * node, not element (so we cannot collapse with peek() without
     * introducing race.)
     * {@description.close}
     */
    Node<E> first() {
        for (;;) {
            Node<E> h = head;
            Node<E> t = tail;
            Node<E> first = h.getNext();
            if (h == head) {
                if (h == t) {
                    if (first == null)
                        return null;
                    else
                        casTail(t, first);
                } else {
                    if (first.getItem() != null)
                        return first;
                    else // remove deleted node and continue
                        casHead(h, first);
                }
            }
        }
    }


    /** {@collect.stats} 
     * {@description.open}
     * Returns <tt>true</tt> if this queue contains no elements.
     * {@description.close}
     *
     * @return <tt>true</tt> if this queue contains no elements
     */
    public boolean isEmpty() {
        return first() == null;
    }

    /** {@collect.stats} 
     * {@description.open}
     * Returns the number of elements in this queue.  If this queue
     * contains more than <tt>Integer.MAX_VALUE</tt> elements, returns
     * <tt>Integer.MAX_VALUE</tt>.
     *
     * <p>Beware that, unlike in most collections, this method is
     * <em>NOT</em> a constant-time operation. Because of the
     * asynchronous nature of these queues, determining the current
     * number of elements requires an O(n) traversal.
     * {@description.close}
     *
     * @return the number of elements in this queue
     */
    public int size() {
        int count = 0;
        for (Node<E> p = first(); p != null; p = p.getNext()) {
            if (p.getItem() != null) {
                // Collections.size() spec says to max out
                if (++count == Integer.MAX_VALUE)
                    break;
            }
        }
        return count;
    }

    /** {@collect.stats} 
     * {@description.open}
     * Returns <tt>true</tt> if this queue contains the specified element.
     * More formally, returns <tt>true</tt> if and only if this queue contains
     * at least one element <tt>e</tt> such that <tt>o.equals(e)</tt>.
     * {@description.close}
     *
     * @param o object to be checked for containment in this queue
     * @return <tt>true</tt> if this queue contains the specified element
     */
    public boolean contains(Object o) {
        if (o == null) return false;
        for (Node<E> p = first(); p != null; p = p.getNext()) {
            E item = p.getItem();
            if (item != null &&
                o.equals(item))
                return true;
        }
        return false;
    }

    /** {@collect.stats} 
     * {@description.open}
     * Removes a single instance of the specified element from this queue,
     * if it is present.  More formally, removes an element <tt>e</tt> such
     * that <tt>o.equals(e)</tt>, if this queue contains one or more such
     * elements.
     * Returns <tt>true</tt> if this queue contained the specified element
     * (or equivalently, if this queue changed as a result of the call).
     * {@description.close}
     *
     * @param o element to be removed from this queue, if present
     * @return <tt>true</tt> if this queue changed as a result of the call
     */
    public boolean remove(Object o) {
        if (o == null) return false;
        for (Node<E> p = first(); p != null; p = p.getNext()) {
            E item = p.getItem();
            if (item != null &&
                o.equals(item) &&
                p.casItem(item, null))
                return true;
        }
        return false;
    }

    /** {@collect.stats} 
     * {@description.open}
     * Returns an array containing all of the elements in this queue, in
     * proper sequence.
     *
     * <p>The returned array will be "safe" in that no references to it are
     * maintained by this queue.  (In other words, this method must allocate
     * a new array).  The caller is thus free to modify the returned array.
     *
     * <p>This method acts as bridge between array-based and collection-based
     * APIs.
     * {@description.close}
     *
     * @return an array containing all of the elements in this queue
     */
    public Object[] toArray() {
        // Use ArrayList to deal with resizing.
        ArrayList<E> al = new ArrayList<E>();
        for (Node<E> p = first(); p != null; p = p.getNext()) {
            E item = p.getItem();
            if (item != null)
                al.add(item);
        }
        return al.toArray();
    }

    /** {@collect.stats} 
     * {@description.open}
     * Returns an array containing all of the elements in this queue, in
     * proper sequence; the runtime type of the returned array is that of
     * the specified array.  If the queue fits in the specified array, it
     * is returned therein.  Otherwise, a new array is allocated with the
     * runtime type of the specified array and the size of this queue.
     *
     * <p>If this queue fits in the specified array with room to spare
     * (i.e., the array has more elements than this queue), the element in
     * the array immediately following the end of the queue is set to
     * <tt>null</tt>.
     *
     * <p>Like the {@link #toArray()} method, this method acts as bridge between
     * array-based and collection-based APIs.  Further, this method allows
     * precise control over the runtime type of the output array, and may,
     * under certain circumstances, be used to save allocation costs.
     *
     * <p>Suppose <tt>x</tt> is a queue known to contain only strings.
     * The following code can be used to dump the queue into a newly
     * allocated array of <tt>String</tt>:
     *
     * <pre>
     *     String[] y = x.toArray(new String[0]);</pre>
     *
     * Note that <tt>toArray(new Object[0])</tt> is identical in function to
     * <tt>toArray()</tt>.
     * {@description.close}
     *
     * @param a the array into which the elements of the queue are to
     *          be stored, if it is big enough; otherwise, a new array of the
     *          same runtime type is allocated for this purpose
     * @return an array containing all of the elements in this queue
     * @throws ArrayStoreException if the runtime type of the specified array
     *         is not a supertype of the runtime type of every element in
     *         this queue
     * @throws NullPointerException if the specified array is null
     */
    public <T> T[] toArray(T[] a) {
        // try to use sent-in array
        int k = 0;
        Node<E> p;
        for (p = first(); p != null && k < a.length; p = p.getNext()) {
            E item = p.getItem();
            if (item != null)
                a[k++] = (T)item;
        }
        if (p == null) {
            if (k < a.length)
                a[k] = null;
            return a;
        }

        // If won't fit, use ArrayList version
        ArrayList<E> al = new ArrayList<E>();
        for (Node<E> q = first(); q != null; q = q.getNext()) {
            E item = q.getItem();
            if (item != null)
                al.add(item);
        }
        return al.toArray(a);
    }

    /** {@collect.stats} 
     * {@description.open}
     * Returns an iterator over the elements in this queue in proper sequence.
     * {@description.close}
     * {@property.open synchronized}
     * The returned iterator is a "weakly consistent" iterator that
     * will never throw {@link ConcurrentModificationException},
     * and guarantees to traverse elements as they existed upon
     * construction of the iterator, and may (but is not guaranteed to)
     * reflect any modifications subsequent to construction.
     * {@property.close}
     *
     * @return an iterator over the elements in this queue in proper sequence
     */
    public Iterator<E> iterator() {
        return new Itr();
    }

    private class Itr implements Iterator<E> {
        /** {@collect.stats} 
         * {@description.open}
         * Next node to return item for.
         * {@description.close}
         */
        private Node<E> nextNode;

        /** {@collect.stats} 
         * {@description.open}
         * nextItem holds on to item fields because once we claim
         * that an element exists in hasNext(), we must return it in
         * the following next() call even if it was in the process of
         * being removed when hasNext() was called.
         * {@description.close}
         */
        private E nextItem;

        /** {@collect.stats} 
         * {@description.open}
         * Node of the last returned item, to support remove.
         * {@description.close}
         */
        private Node<E> lastRet;

        Itr() {
            advance();
        }

        /** {@collect.stats} 
         * {@description.open}
         * Moves to next valid node and returns item to return for
         * next(), or null if no such.
         * {@description.close}
         */
        private E advance() {
            lastRet = nextNode;
            E x = nextItem;

            Node<E> p = (nextNode == null)? first() : nextNode.getNext();
            for (;;) {
                if (p == null) {
                    nextNode = null;
                    nextItem = null;
                    return x;
                }
                E item = p.getItem();
                if (item != null) {
                    nextNode = p;
                    nextItem = item;
                    return x;
                } else // skip over nulls
                    p = p.getNext();
            }
        }

        public boolean hasNext() {
            return nextNode != null;
        }

        public E next() {
            if (nextNode == null) throw new NoSuchElementException();
            return advance();
        }

        public void remove() {
            Node<E> l = lastRet;
            if (l == null) throw new IllegalStateException();
            // rely on a future traversal to relink.
            l.setItem(null);
            lastRet = null;
        }
    }

    /** {@collect.stats} 
     * {@description.open}
     * Save the state to a stream (that is, serialize it).
     * {@description.close}
     *
     * @serialData All of the elements (each an <tt>E</tt>) in
     * the proper order, followed by a null
     * @param s the stream
     */
    private void writeObject(java.io.ObjectOutputStream s)
        throws java.io.IOException {

        // Write out any hidden stuff
        s.defaultWriteObject();

        // Write out all elements in the proper order.
        for (Node<E> p = first(); p != null; p = p.getNext()) {
            Object item = p.getItem();
            if (item != null)
                s.writeObject(item);
        }

        // Use trailing null as sentinel
        s.writeObject(null);
    }

    /** {@collect.stats} 
     * {@description.open}
     * Reconstitute the Queue instance from a stream (that is,
     * deserialize it).
     * {@description.close}
     * @param s the stream
     */
    private void readObject(java.io.ObjectInputStream s)
        throws java.io.IOException, ClassNotFoundException {
        // Read in capacity, and any hidden stuff
        s.defaultReadObject();
        head = new Node<E>(null, null);
        tail = head;
        // Read in all elements and place in queue
        for (;;) {
            E item = (E)s.readObject();
            if (item == null)
                break;
            else
                offer(item);
        }
    }

}
