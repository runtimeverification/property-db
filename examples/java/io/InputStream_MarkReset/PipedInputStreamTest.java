import java.io.*;

public class PipedInputStreamTest {
	public static void main(String[] args) throws Exception {
		PipedOutputStream output = new PipedOutputStream();

		try {
			InputStream stream = new PipedInputStream(output);
			output.write(1);
			output.write(2);
			output.write(3);
			output.write(4);
			output.write(5);

			stream.mark(1);
			int c = stream.read();
			stream.reset();
			int d = stream.read();
			stream.reset();
			int e = stream.read();
			if (c == d && d == e)
				System.out.println("PipedInputStream.reset() was properly used.");
			else
				throw new Exception("PipedInputStream.reset() did not preserve the value.");
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}
