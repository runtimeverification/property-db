import java.io.*;

public class PushbackInputStreamTest {
	public static void main(String[] args) throws Exception {
		try {
			PushbackInputStream stream = new PushbackInputStream(new StringBufferInputStream("1234567890"));
			stream.mark(1);
			int c = stream.read();
			stream.reset();
			int d = stream.read();
			stream.reset();
			int e = stream.read();
			if (c == d && d == e)
				System.out.println("PushbackInputStream.reset() was properly used.");
			else
				throw new Exception("PushbackInputStream.reset() did not preserve the value.");
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}
