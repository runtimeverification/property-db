import java.io.*;

public class InputStream_ManipulateAfterClose_2 {
	public static void main(String[] args) throws IOException {
		byte[] buffer = { 1, 2, 3, 4, 5};
		ByteArrayInputStream input = new ByteArrayInputStream(buffer);
		int i = input.read();
		input.close();

		// Although ByteArrayInputStream is a subclass of InputStream, calling
		// read() after close() is allowed.
		int j = input.read();
	}
}

