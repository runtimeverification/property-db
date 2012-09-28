import java.net.*;
import java.io.IOException;

public class ServerSocket_LargeReceiveBuffer_1 {
	public static void main(String[] args) throws IOException {
		int largebuffer = 65536 + 1;

		ServerSocket unbound = new ServerSocket();
		// A buffer larger than 64K bytes can be set, as the socket has not been
		// bound yet.
		unbound.setReceiveBufferSize(largebuffer);

		// Now that the socket is bound, setting a large buffer will trigger the
		// property handler, which prints a warning message.
		unbound.bind(null);
		unbound.setReceiveBufferSize(largebuffer);

		ServerSocket bound = new ServerSocket(65535);
		// As the 'bound' socket was bound above, the following call should
		// trigger the property handler.
		unbound.setReceiveBufferSize(largebuffer);
	}
}

