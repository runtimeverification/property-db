import java.io.*;

public class CharArrayWriter_Close_1 {
	public static void main(String[] args) throws IOException {
		Writer writer = new CharArrayWriter();

		// close() has no effect.
		writer.close();
	}
}

