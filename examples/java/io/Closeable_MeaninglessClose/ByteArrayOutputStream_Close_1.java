import java.io.*;

public class ByteArrayOutputStream_Close_1 {
	public static void main(String[] args) throws IOException {
		OutputStream output = new ByteArrayOutputStream();

		// close() has no effect.
		output.close();
	}
}

