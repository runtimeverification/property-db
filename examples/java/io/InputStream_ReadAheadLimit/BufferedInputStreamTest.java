import java.io.*;

public class BufferedInputStreamTest {
	public static void main(String[] args) throws Exception {
		BufferedInputStream stream = new BufferedInputStream(new StringBufferInputStream("1234567890"), 1);

		stream.mark(1);
		stream.read();
		stream.read();

		stream.reset();
		stream.read();
		stream.read();
	}
}
