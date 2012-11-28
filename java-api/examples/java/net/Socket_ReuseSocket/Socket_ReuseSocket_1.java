import java.net.*;
import java.io.IOException;

public class Socket_ReuseSocket_1 {
	public static void main(String[] args) throws IOException, SocketException {
		Socket sock = new Socket("www.illinois.edu", 80);

		sock.close();

		// After close(), the socket should not be reused for binding or
		// connecting. The following call should trigger the property handler.
		sock.bind(null);
	}
}


