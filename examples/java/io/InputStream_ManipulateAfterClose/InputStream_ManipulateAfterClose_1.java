import java.io.*;

public class InputStream_ManipulateAfterClose_1 {
	public static void main(String[] args) throws IOException {
		byte[] buffer = { 1, 2, 3, 4, 5};
		ByteArrayInputStream input = new ByteArrayInputStream(buffer);
		int i = input.read();
		input.close();

		// After a stream is closed, most operations, such as read() and reset(), are banned.
		int j = input.read();
	}
}

