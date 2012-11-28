import java.net.*;
import java.io.IOException;

public class ServerSocket_SetTimeoutBeforeBlocking_1 {
	public static void main(String[] args) throws IOException {
		final ServerSocket sock = new ServerSocket(65535);

		// The following method call is fine; after 2 seconds, accept() will
		// raise the SocketTimeoutException exception.
		sock.setSoTimeout(2000);
		try {
			sock.accept();
		}
		catch (SocketTimeoutException expected) {
		}

		// In the following routine, timeout was set while accept() is blocking.
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
		sock.accept();
	}
}

