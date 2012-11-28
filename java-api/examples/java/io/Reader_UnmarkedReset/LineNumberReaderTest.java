import java.io.*;

public class LineNumberReaderTest {
	public static void main(String[] args) throws Exception {

		Reader reader = new LineNumberReader(new StringReader("1234567890"), 1);
		reader.reset();
		int c = reader.read();
	}
}
