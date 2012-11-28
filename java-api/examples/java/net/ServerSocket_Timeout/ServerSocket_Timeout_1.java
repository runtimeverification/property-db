import java.net.*;
import java.io.IOException;

public class ServerSocket_Timeout_1 {
	public static void main(String[] args) throws IOException {
		set(true, 1);
		set(false, 0);
	}

	private static void set(boolean legal, int timeout) throws IOException {
		try {
			ServerSocket sock = new ServerSocket();
			sock.setSoTimeout(timeout);
		}
		catch (IllegalArgumentException e) {
			if (legal)
				throw e;
		}
	}
}

