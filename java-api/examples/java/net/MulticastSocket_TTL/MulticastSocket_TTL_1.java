import java.net.*;
import java.io.IOException;

public class MulticastSocket_TTL_1 {
	public static void main(String[] args) throws IOException {
		MulticastSocket socket = new MulticastSocket();
		set(true, socket, 255);
		set(false, socket, 256);
	}

	private static void set(boolean legal, MulticastSocket socket, int ttl) throws IOException {
		try {
			socket.setTimeToLive(ttl);
		}
		catch (IllegalArgumentException e) {
			if (legal)
				throw e;
		}
	}
}

