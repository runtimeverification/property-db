import java.io.*;

public class BufferedReaderTest {
	public static void main(String[] args) throws Exception {

		Reader reader = new BufferedReader(new StringReader("1234567890"), 1);

		reader.mark(1);

		// We read only one byte; so, reset() is valid.
		reader.read();
		reader.reset();

		// The buffer cannot hold two bytes; so, reset() is invalid.
		reader.read();
		reader.read();
		reader.reset();

		reader.read();
		reader.read();
	}
}
