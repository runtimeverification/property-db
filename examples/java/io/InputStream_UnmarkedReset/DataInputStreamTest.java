import java.io.*;

public class DataInputStreamTest {
	public static void main(String[] args) throws Exception {
		DataInputStream stream = new DataInputStream(new StringBufferInputStream("1234567890"));
		stream.reset();
		int c = stream.read();
	}
}
