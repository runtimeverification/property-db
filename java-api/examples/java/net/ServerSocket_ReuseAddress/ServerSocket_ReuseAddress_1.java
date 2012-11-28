import java.net.*;
import java.io.IOException;

public class ServerSocket_ReuseAddress_1 {
	public static void main(String[] args) throws IOException, SocketException {
		ServerSocket sock = new ServerSocket();

		// The following call is fine.
		sock.setReuseAddress(true);

		sock.bind(null);

		// Now that the socket is bound, the behavior of the following call is
		// not defined. The property handler should be triggered.
		sock.setReuseAddress(true);
	}
}

