import java.io.*;

public class DataInputStreamTest {
	public static void main(String[] args) throws Exception {
		DataInputStream stream = new DataInputStream(new StringBufferInputStream("1234567890"));

		stream.mark(1);
		stream.read();
		stream.read();

		stream.reset();
		stream.read();
		stream.read();
	}
}
