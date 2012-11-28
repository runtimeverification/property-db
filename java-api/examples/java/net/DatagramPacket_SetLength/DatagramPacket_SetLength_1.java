import java.net.*;

public class DatagramPacket_SetLength_1 {
	public static void main(String[] args) {
		byte[] buffer = new byte[5];

		DatagramPacket packet1 = new DatagramPacket(buffer, 5);
		packet1.setLength(5);
		try {
			packet1.setLength(6);
		}
		catch (IllegalArgumentException e) {
		}

		DatagramPacket packet2 = new DatagramPacket(buffer, 1, 4);
		packet2.setLength(4);
		try {
			packet2.setLength(5);
		}
		catch (IllegalArgumentException e) {
		}
	}
}

