import java.net.*;
import java.io.IOException;
import java.io.InputStream;

public class Socket_InputStreamUnavailable_1 {
	public static void main(String[] args) throws IOException {
		testValid();

		testNotConnected();
		testClosed();
		testShutdown();
	}

	private static void testValid() throws UnknownHostException, IOException {
		Socket sock = new Socket("www.uiuc.edu", 80);

		try {
			InputStream input = sock.getInputStream();
		}
		catch (IOException expected) {
		}
	}

	private static void testNotConnected() throws UnknownHostException, IOException {
		Socket sock = new Socket();

		try {
			InputStream input = sock.getInputStream();
		}
		catch (IOException expected) {
		}
	}

	private static void testClosed() throws UnknownHostException, IOException {
		Socket sock = new Socket("www.uiuc.edu", 80);
		sock.close();

		try {
			InputStream input = sock.getInputStream();
		}
		catch (IOException expected) {
		}
	}

	private static void testShutdown() throws UnknownHostException, IOException {
		Socket sock = new Socket("www.uiuc.edu", 80);
		sock.shutdownInput();

		try {
			InputStream input = sock.getInputStream();
		}
		catch (IOException expected) {
		}
	}
}

