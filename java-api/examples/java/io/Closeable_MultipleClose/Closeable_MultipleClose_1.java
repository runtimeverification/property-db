import java.io.*;

public class Closeable_MultipleClose_1 {
	public static void main(String[] args) {
		CharArrayWriter writer = new CharArrayWriter();
		writer.write(100);

		writer.close();
		// Closing a previously closed stream has no effect.
		writer.close();
	}
}

