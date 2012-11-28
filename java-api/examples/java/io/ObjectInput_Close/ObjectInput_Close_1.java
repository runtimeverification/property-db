import java.io.*;

public class ObjectInput_Close_1 {
	public static void main(String[] args) throws IOException {
		byte[] buffer = generateBuffer();
		ByteArrayInputStream bais = new ByteArrayInputStream(buffer);

		ObjectInputStream ois = new ObjectInputStream(bais);
		byte b = ois.readByte();
		// close() must be called to release the resources.
//		ois.close();
	}

	private static byte[] generateBuffer() throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeInt(1);
		oos.close();

		return baos.toByteArray();
	}
}

