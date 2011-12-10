import java.io.*;

public class ByteArrayInputStream_Close_1 {
	public static void main(String[] args) throws IOException {
		byte[] buffer = { 1, 2, 3, 4, 5};
		InputStream input = new ByteArrayInputStream(buffer);

		// close() has no effect.
		input.close();
	}
}

