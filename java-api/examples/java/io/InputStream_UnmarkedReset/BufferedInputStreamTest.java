import java.io.*;

public class BufferedInputStreamTest {
	public static void main(String[] args) throws Exception {
		BufferedInputStream stream = new BufferedInputStream(new StringBufferInputStream("1234567890"));
		stream.reset();
		int c = stream.read();
	}
}
