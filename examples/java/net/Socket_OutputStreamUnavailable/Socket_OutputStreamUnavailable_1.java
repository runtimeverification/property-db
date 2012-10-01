import java.net.*;
import java.io.IOException;
import java.io.OutputStream;

public class Socket_OutputStreamUnavailable_1 {
	public static void main(String[] args) throws IOException {
		testValid();

		testNotConnected();
		testClosed();
		testShutdown();
	}

	private static void testValid() throws UnknownHostException, IOException {
		Socket sock = new Socket("www.uiuc.edu", 80);

		try {
			OutputStream input = sock.getOutputStream();
		}
		catch (IOException expected) {
		}
	}

	private static void testNotConnected() throws UnknownHostException, IOException {
		Socket sock = new Socket();

		try {
			OutputStream input = sock.getOutputStream();
		}
		catch (IOException expected) {
		}
	}

	private static void testClosed() throws UnknownHostException, IOException {
		Socket sock = new Socket("www.uiuc.edu", 80);
		sock.close();

		try {
			OutputStream input = sock.getOutputStream();
		}
		catch (IOException expected) {
		}
	}

	private static void testShutdown() throws UnknownHostException, IOException {
		Socket sock = new Socket("www.uiuc.edu", 80);
		sock.shutdownOutput();

		try {
			OutputStream input = sock.getOutputStream();
		}
		catch (IOException expected) {
		}
	}
}

