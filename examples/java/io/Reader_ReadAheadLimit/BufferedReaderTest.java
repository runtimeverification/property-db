import java.io.*;

public class BufferedReaderTest {
	public static void main(String[] args) throws Exception {

		Reader reader = new BufferedReader(new StringReader("1234567890"), 1);

		reader.mark(1);
		reader.read();
		reader.read();

		reader.reset();
		reader.read();
		reader.read();
	}
}
