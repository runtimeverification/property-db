import java.net.*;
import java.io.IOException;

public class ServerSocket_PerformancePreferences_1 {
	public static void main(String[] args) throws IOException, SocketException {
		ServerSocket sock = new ServerSocket();

		// The following call is fine.
		sock.setPerformancePreferences(1, 0, 0);

		sock.bind(null);

		// Now that the socket is bound, the following call has no effect. The
		// property handler should be triggered.
		sock.setPerformancePreferences(1, 0, 0);
	}
}

