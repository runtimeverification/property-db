import java.net.*;
import java.io.IOException;

public class ServerSocket_Backlog_1 {
	public static void main(String[] args) throws IOException {
		create(true, 65535, 1);
		create(false, 65534, 0);

		bind(true, 1);
		bind(false, 0);
	}

	private static void create(boolean legal, int port, int backlog) throws IOException {
		try {
			ServerSocket sock = new ServerSocket(port, backlog);
		}
		catch (IllegalArgumentException e) {
			if (legal)
				throw e;
		}
	}

	private static void bind(boolean legal, int backlog) throws IOException {
		try {
			ServerSocket sock = new ServerSocket();
			sock.bind(null, backlog);
		}
		catch (IllegalArgumentException e) {
			if (legal)
				throw e;
		}
	}
}

