import java.net.*;
import java.io.IOException;

public class Socket_ReuseAddress_1 {
	public static void main(String[] args) throws IOException, SocketException {
		Socket sock = new Socket();

		// The following call is fine.
		sock.setReuseAddress(true);

		sock.bind(null);

		// Now that the socket is bound, the behavior of the following call is
		// not defined. The property handler should be triggered.
		sock.setReuseAddress(true);
	}
}

