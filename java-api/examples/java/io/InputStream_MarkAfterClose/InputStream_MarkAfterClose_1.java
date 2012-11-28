import java.io.*;

public class InputStream_MarkAfterClose_1 {
	public static void main(String[] args) throws IOException {
		byte[] buffer = { 1, 2, 3, 4, 5};
		ByteArrayInputStream input = new ByteArrayInputStream(buffer);
		int i = input.read();
		input.close();

		// After a stream is closed, mark() has no effect on the stream.
		input.mark(8);
	}
}

