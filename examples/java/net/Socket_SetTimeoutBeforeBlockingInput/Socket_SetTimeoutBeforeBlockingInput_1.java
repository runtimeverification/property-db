import java.net.*;
import java.io.IOException;
import java.io.InputStream;

public class Socket_SetTimeoutBeforeBlockingInput_1 {
	public static void main(String[] args) throws IOException {
		// This program assumes the following socket cannot read input
		// immediately. If this assumption does not hold, this test is
		// meaningless.
		final Socket sock = new Socket("www.illinois.edu", 80);

		// The following method call is fine; after 2 seconds, read() will raise
		// the SocketTimeoutException exception.
		sock.setSoTimeout(2000);
		try {
			InputStream in = sock.getInputStream();
			in.read();
		}
		catch (SocketTimeoutException expected) {
		}

		// In the following routine, timeout was set while read() is blocking.
		// The property handler should be triggered.
		Thread thread = new Thread() {
			@Override
			public void run() {
				try {
					Thread.sleep(1000);
				}
				catch (InterruptedException ignored) {
				}

				try {
					sock.setSoTimeout(1000);
				}
				catch (SocketException ignored) {
				}
			}
		};

		thread.start();
		InputStream in = sock.getInputStream();
		in.read();
	}
}

