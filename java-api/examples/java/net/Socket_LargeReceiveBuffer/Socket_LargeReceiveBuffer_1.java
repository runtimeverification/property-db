import java.net.*;
import java.io.IOException;

public class Socket_LargeReceiveBuffer_1 {
	public static void main(String[] args) throws IOException {
		int largebuffer = 65536 + 1;

		Socket unconnected = new Socket();
		// A buffer larger than 64K bytes can be set, as the socket has not been
		// connected yet.
		unconnected.setReceiveBufferSize(largebuffer);

		// Now that the socket is connected, setting a large buffer will trigger the
		// property handler, which prints a warning message.
		InetSocketAddress addr = new InetSocketAddress("www.illinois.edu", 80);
		unconnected.connect(addr);
		unconnected.setReceiveBufferSize(largebuffer);

		Socket connected = new Socket(addr.getAddress(), addr.getPort());
		// As the 'connected' socket was connected above, the following call should
		// trigger the property handler.
		connected.setReceiveBufferSize(largebuffer);
	}
}


