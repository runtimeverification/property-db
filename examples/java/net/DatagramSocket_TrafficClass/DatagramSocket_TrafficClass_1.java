import java.net.*;

public class DatagramSocket_TrafficClass_1 {
	public static void main(String[] args) throws SocketException {
		DatagramSocket socket = new DatagramSocket();

		set(true, socket, 0);
		set(true, socket, 1 << 1);
		set(true, socket, 1 << 2);
		set(true, socket, 1 << 3);
		set(true, socket, 1 << 4);

		// The value should be between 0 and 256 inclusive.
		set(false, socket, 256);

		// The last low bit is always ignored.
		set(false, socket, 1);

		// Setting the precedence field may result in an exception.
		set(false, socket, 1 << 5);
	}

	private static void set(boolean legal, DatagramSocket socket, int tc) throws SocketException {
		try {
			socket.setTrafficClass(tc);
		}
		catch (IllegalArgumentException e) {
			if (legal)
				throw e;
		}
	}
}

