import java.io.*;

public class OutputStream_ManipulateAfterClose_1 {
	public static void main(String[] args) throws IOException {
		File file = File.createTempFile("javamoptest1", ".tmp");
		file.deleteOnExit();

		OutputStream output = new FileOutputStream(file);
		output.write(1);
		output.close();

		// A closed stream cannot perform output operations.
		output.write(2);
	}
}

