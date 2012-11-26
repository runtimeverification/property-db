import java.net.*;

public class DatagramSocket_SoTimeout_1 {
	public static void main(String[] args) throws SocketException {
		DatagramSocket socket = new DatagramSocket();

		socket.setSoTimeout(0);
		try {
			socket.setSoTimeout(-1);
		}
		catch (IllegalArgumentException e) {
		}
	}
}

