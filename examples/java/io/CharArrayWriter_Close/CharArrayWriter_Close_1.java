import java.io.*;

public class CharArrayWriter_Close_1 {
	public static void main(String[] args) {
		CharArrayWriter writer = new CharArrayWriter();
		writer.write(100);
		// close() has no effect.
		writer.close();
	}
}

