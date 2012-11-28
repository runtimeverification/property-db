import java.net.*;

public class DatagramSocket_Port_1 {
	public static void main(String[] args) throws SocketException {
		DatagramSocket bad = null;

		try {
			bad = new DatagramSocket(65536);
		}
		catch (IllegalArgumentException e) {
		}

		try {
			bad = new DatagramSocket(65536, null);
		}
		catch (IllegalArgumentException e) {
		}
	}
}

