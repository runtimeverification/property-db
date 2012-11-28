import java.io.*;

public class SequenceInputStreamTest {
	public static void main(String[] args) throws Exception {
		try {
			SequenceInputStream stream = new SequenceInputStream(new StringBufferInputStream("12345"), new StringBufferInputStream("67890"));
			stream.mark(1);
			int c = stream.read();
			stream.reset();
			int d = stream.read();
			stream.reset();
			int e = stream.read();
			if (c == d && d == e)
				System.out.println("SequenceInputStream.reset() was properly used.");
			else
				throw new Exception("SequenceInputStream.reset() did not preserve the value.");
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}
