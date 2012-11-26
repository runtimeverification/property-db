import java.io.*;

public class LineNumberInputStreamTest {
	public static void main(String[] args) throws Exception {
		LineNumberInputStream stream = new LineNumberInputStream(new StringBufferInputStream("1234567890"));

		stream.mark(1);
		stream.read();
		stream.read();

		stream.reset();
		stream.read();
		stream.read();
	}
}
