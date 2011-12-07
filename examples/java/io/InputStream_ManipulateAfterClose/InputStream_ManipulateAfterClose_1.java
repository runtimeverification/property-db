import java.io.*;

public class InputStream_ManipulateAfterClose_1 {
	public static void main(String[] args) throws IOException {
		File file = File.createTempFile("javamoptest1", ".tmp");
		FileWriter writer = new FileWriter(file);
		writer.write("0123456789");
		writer.close();
		file.deleteOnExit();

		InputStream input = new FileInputStream(file);
		int i = input.read();
		input.close();

		// After a stream is closed, most operations, such as read() and reset(), are banned.
		int j = input.read();
	}
}

