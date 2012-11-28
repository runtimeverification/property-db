import java.io.*;

public class Reader_ManipulateAfterClose_1 {
	public static void main(String[] args) throws IOException {
		StringReader reader = new StringReader("hello");
		int i = reader.read();
		reader.close();

		// After a reader is closed, most operations, such as read() and reset(), are banned.
		int j = reader.read();
	}
}

