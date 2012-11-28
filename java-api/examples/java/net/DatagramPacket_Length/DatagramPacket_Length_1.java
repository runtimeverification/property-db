import java.net.*;

public class DatagramPacket_Length_1 {
	public static void main(String[] args) {
		byte[] buffer = new byte[5];

		DatagramPacket good = null;
		DatagramPacket bad = null;

		good = new DatagramPacket(buffer, 5);
		try {
			bad = new DatagramPacket(buffer, 6);
		}
		catch (IllegalArgumentException e) {
		}

		good = new DatagramPacket(buffer, 1, 4);
		try {
			bad = new DatagramPacket(buffer, 1, 5);
		}
		catch (IllegalArgumentException e) {
		}
	}
}

