import java.io.*;

public class ByteArrayOutputStream_Close_1 {
	public static void main(String[] args) throws IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		output.write(1);
		// close() has no effect.
		output.close();
	}
}

