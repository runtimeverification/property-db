import java.net.*;
import java.io.IOException;

public class Socket_Timeout_1 {
	public static void main(String[] args) throws IOException {
		set(true, 1);
		set(false, 0);
	}

	private static void set(boolean legal, int timeout) throws IOException {
		try {
			Socket sock = new Socket();
			sock.setSoTimeout(timeout);
		}
		catch (IllegalArgumentException e) {
			if (legal)
				throw e;
		}
	}
}

