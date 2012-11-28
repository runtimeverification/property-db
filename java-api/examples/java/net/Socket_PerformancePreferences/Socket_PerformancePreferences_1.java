import java.net.*;
import java.io.IOException;

public class Socket_PerformancePreferences_1 {
	public static void main(String[] args) throws IOException, SocketException {
		Socket sock = new Socket();

		// The following call is fine.
		sock.setPerformancePreferences(1, 0, 0);

		InetSocketAddress addr = new InetSocketAddress("www.illinois.edu", 80);
		sock.connect(addr);

		// Now that the socket is bound, the following call has no effect. The
		// property handler should be triggered.
		sock.setPerformancePreferences(1, 0, 0);
	}
}

