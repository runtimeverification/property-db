/*
 * Copyright (c) 1995, 2013, Oracle and/or its affiliates. All rights reserved.
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
 * This class represents a datagram packet.
 * <p>
 * Datagram packets are used to implement a connectionless packet
 * delivery service. Each message is routed from one machine to
 * another based solely on information contained within that packet.
 * Multiple packets sent from one machine to another might be routed
 * differently, and might arrive in any order. Packet delivery is
 * not guaranteed.
 * {@description.close}
 *
 * @author  Pavani Diwanji
 * @author  Benjamin Renaud
 * @since   JDK1.0
 */
public final
class DatagramPacket {

    /** {@collect.stats} 
     * {@description.open}
     * Perform class initialization
     * {@description.close}
     */
    static {
        java.security.AccessController.doPrivileged(
            new java.security.PrivilegedAction<Void>() {
                public Void run() {
                    System.loadLibrary("net");
                    return null;
                }
            });
        init();
    }

    /*
     * The fields of this class are package-private since DatagramSocketImpl
     * classes needs to access them.
     */
    byte[] buf;
    int offset;
    int length;
    int bufLength;
    InetAddress address;
    int port;

    /** {@collect.stats} 
     * {@description.open}
     * Constructs a {@code DatagramPacket} for receiving packets of
     * length {@code length}, specifying an offset into the buffer.
     * {@description.close}
     * {@property.open runtime formal:java.net.DatagramPacket_Length}
     * <p>
     * The {@code length} argument must be less than or equal to
     * {@code buf.length}.
     * {@property.close}
     *
     * @param   buf      buffer for holding the incoming datagram.
     * @param   offset   the offset for the buffer
     * @param   length   the number of bytes to read.
     *
     * @since 1.2
     */
    public DatagramPacket(byte buf[], int offset, int length) {
        setData(buf, offset, length);
        this.address = null;
        this.port = -1;
    }

    /** {@collect.stats} 
     * {@description.open}
     * Constructs a {@code DatagramPacket} for receiving packets of
     * length {@code length}.
     * {@description.close}
     * {@property.open runtime formal:java.net.DatagramPacket_Length}
     * <p>
     * The {@code length} argument must be less than or equal to
     * {@code buf.length}.
     * {@property.close}
     *
     * @param   buf      buffer for holding the incoming datagram.
     * @param   length   the number of bytes to read.
     */
    public DatagramPacket(byte buf[], int length) {
        this (buf, 0, length);
    }

    /** {@collect.stats} 
     * {@description.open}
     * Constructs a datagram packet for sending packets of length
     * {@code length} with offset {@code ioffset}to the
     * specified port number on the specified host. 
     * {@description.close}
     * {@property.open runtime formal:java.net.DatagramPacket_Length}
     * The
     * {@code length} argument must be less than or equal to
     * {@code buf.length}.
     * {@property.close}
     *
     * @param   buf      the packet data.
     * @param   offset   the packet data offset.
     * @param   length   the packet data length.
     * @param   address  the destination address.
     * @param   port     the destination port number.
     * @see java.net.InetAddress
     *
     * @since 1.2
     */
    public DatagramPacket(byte buf[], int offset, int length,
                          InetAddress address, int port) {
        setData(buf, offset, length);
        setAddress(address);
        setPort(port);
    }

    /** {@collect.stats} 
     * {@description.open}
     * Constructs a datagram packet for sending packets of length
     * {@code length} with offset {@code ioffset}to the
     * specified port number on the specified host. 
     * {@description.close}
     * {@property.open runtime formal:java.net.DatagramPacket_Length}
     * The
     * {@code length} argument must be less than or equal to
     * {@code buf.length}.
     * {@property.close}
     *
     * @param   buf      the packet data.
     * @param   offset   the packet data offset.
     * @param   length   the packet data length.
     * @param   address  the destination socket address.
     * @throws  IllegalArgumentException if address type is not supported
     * @see java.net.InetAddress
     *
     * @since 1.4
     */
    public DatagramPacket(byte buf[], int offset, int length, SocketAddress address) {
        setData(buf, offset, length);
        setSocketAddress(address);
    }

    /** {@collect.stats} 
     * {@description.open}
     * Constructs a datagram packet for sending packets of length
     * {@code length} to the specified port number on the specified
     * host.
     * {@description.close}
     * {@property.open runtime formal:java.net.DatagramPacket_Length}
     * The {@code length} argument must be less than or equal
     * to {@code buf.length}.
     * {@property.close}
     *
     * @param   buf      the packet data.
     * @param   length   the packet length.
     * @param   address  the destination address.
     * @param   port     the destination port number.
     * @see     java.net.InetAddress
     */
    public DatagramPacket(byte buf[], int length,
                          InetAddress address, int port) {
        this(buf, 0, length, address, port);
    }

    /** {@collect.stats} 
     * {@description.open}
     * Constructs a datagram packet for sending packets of length
     * {@code length} to the specified port number on the specified
     * host. 
     * {@description.close}
     * {@property.open runtime formal:java.net.DatagramPacket_Length}
     * The {@code length} argument must be less than or equal
     * to {@code buf.length}.
     * {@property.close}
     *
     * @param   buf      the packet data.
     * @param   length   the packet length.
     * @param   address  the destination address.
     * @throws  IllegalArgumentException if address type is not supported
     * @since 1.4
     * @see     java.net.InetAddress
     */
    public DatagramPacket(byte buf[], int length, SocketAddress address) {
        this(buf, 0, length, address);
    }

    /** {@collect.stats} 
     * {@description.open}
     * Returns the IP address of the machine to which this datagram is being
     * sent or from which the datagram was received.
     * {@description.close}
     *
     * @return  the IP address of the machine to which this datagram is being
     *          sent or from which the datagram was received.
     * @see     java.net.InetAddress
     * @see #setAddress(java.net.InetAddress)
     */
    public synchronized InetAddress getAddress() {
        return address;
    }

    /** {@collect.stats} 
     * {@description.open}
     * Returns the port number on the remote host to which this datagram is
     * being sent or from which the datagram was received.
     * {@description.close}
     *
     * @return  the port number on the remote host to which this datagram is
     *          being sent or from which the datagram was received.
     * @see #setPort(int)
     */
    public synchronized int getPort() {
        return port;
    }

    /** {@collect.stats} 
     * {@description.open}
     * Returns the data buffer. The data received or the data to be sent
     * starts from the {@code offset} in the buffer,
     * and runs for {@code length} long.
     * {@description.close}
     *
     * @return  the buffer used to receive or  send data
     * @see #setData(byte[], int, int)
     */
    public synchronized byte[] getData() {
        return buf;
    }

    /** {@collect.stats} 
     * {@description.open}
     * Returns the offset of the data to be sent or the offset of the
     * data received.
     * {@description.close}
     *
     * @return  the offset of the data to be sent or the offset of the
     *          data received.
     *
     * @since 1.2
     */
    public synchronized int getOffset() {
        return offset;
    }

    /** {@collect.stats} 
     * {@description.open}
     * Returns the length of the data to be sent or the length of the
     * data received.
     * {@description.close}
     *
     * @return  the length of the data to be sent or the length of the
     *          data received.
     * @see #setLength(int)
     */
    public synchronized int getLength() {
        return length;
    }

    /** {@collect.stats} 
     * {@description.open}
     * Set the data buffer for this packet. This sets the
     * data, length and offset of the packet.
     * {@description.close}
     *
     * @param buf the buffer to set for this packet
     *
     * @param offset the offset into the data
     *
     * @param length the length of the data
     *       and/or the length of the buffer used to receive data
     *
     * @exception NullPointerException if the argument is null
     *
     * @see #getData
     * @see #getOffset
     * @see #getLength
     *
     * @since 1.2
     */
    public synchronized void setData(byte[] buf, int offset, int length) {
        /* this will check to see if buf is null */
        if (length < 0 || offset < 0 ||
            (length + offset) < 0 ||
            ((length + offset) > buf.length)) {
            throw new IllegalArgumentException("illegal length or offset");
        }
        this.buf = buf;
        this.length = length;
        this.bufLength = length;
        this.offset = offset;
    }

    /** {@collect.stats} 
     * {@description.open}
     * Sets the IP address of the machine to which this datagram
     * is being sent.
     * {@description.close}
     * @param iaddr the {@code InetAddress}
     * @since   JDK1.1
     * @see #getAddress()
     */
    public synchronized void setAddress(InetAddress iaddr) {
        address = iaddr;
    }

    /** {@collect.stats} 
     * {@description.open}
     * Sets the port number on the remote host to which this datagram
     * is being sent.
     * {@description.close}
     * @param iport the port number
     * @since   JDK1.1
     * @see #getPort()
     */
    public synchronized void setPort(int iport) {
        if (iport < 0 || iport > 0xFFFF) {
            throw new IllegalArgumentException("Port out of range:"+ iport);
        }
        port = iport;
    }

    /** {@collect.stats} 
     * {@description.open}
     * Sets the SocketAddress (usually IP address + port number) of the remote
     * host to which this datagram is being sent.
     * {@description.close}
     *
     * @param address the {@code SocketAddress}
     * @throws  IllegalArgumentException if address is null or is a
     *          SocketAddress subclass not supported by this socket
     *
     * @since 1.4
     * @see #getSocketAddress
     */
    public synchronized void setSocketAddress(SocketAddress address) {
        if (address == null || !(address instanceof InetSocketAddress))
            throw new IllegalArgumentException("unsupported address type");
        InetSocketAddress addr = (InetSocketAddress) address;
        if (addr.isUnresolved())
            throw new IllegalArgumentException("unresolved address");
        setAddress(addr.getAddress());
        setPort(addr.getPort());
    }

    /** {@collect.stats} 
     * {@description.open}
     * Gets the SocketAddress (usually IP address + port number) of the remote
     * host that this packet is being sent to or is coming from.
     * {@description.close}
     *
     * @return the {@code SocketAddress}
     * @since 1.4
     * @see #setSocketAddress
     */
    public synchronized SocketAddress getSocketAddress() {
        return new InetSocketAddress(getAddress(), getPort());
    }

    /** {@collect.stats} 
     * {@description.open}
     * Set the data buffer for this packet. With the offset of
     * this DatagramPacket set to 0, and the length set to
     * the length of {@code buf}.
     * {@description.close}
     *
     * @param buf the buffer to set for this packet.
     *
     * @exception NullPointerException if the argument is null.
     *
     * @see #getLength
     * @see #getData
     *
     * @since JDK1.1
     */
    public synchronized void setData(byte[] buf) {
        if (buf == null) {
            throw new NullPointerException("null packet buffer");
        }
        this.buf = buf;
        this.offset = 0;
        this.length = buf.length;
        this.bufLength = buf.length;
    }

    /** {@collect.stats} 
     * {@description.open}
     * Set the length for this packet. The length of the packet is
     * the number of bytes from the packet's data buffer that will be
     * sent, or the number of bytes of the packet's data buffer that
     * will be used for receiving data. 
     * {@description.close}
     * {@property.open runtime formal:java.net.DatagramPacket_SetLength}
     * The length must be lesser or
     * equal to the offset plus the length of the packet's buffer.
     * {@property.close}
     *
     * @param length the length to set for this packet.
     *
     * @exception IllegalArgumentException if the length is negative
     * of if the length is greater than the packet's data buffer
     * length.
     *
     * @see #getLength
     * @see #setData
     *
     * @since JDK1.1
     */
    public synchronized void setLength(int length) {
        if ((length + offset) > buf.length || length < 0 ||
            (length + offset) < 0) {
            throw new IllegalArgumentException("illegal length");
        }
        this.length = length;
        this.bufLength = this.length;
    }

    /** {@collect.stats} 
     * {@description.open}
     * Perform class load-time initializations.
     * {@description.close}
     */
    private native static void init();
}