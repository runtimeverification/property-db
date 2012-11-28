import java.io.*;

public class Console_CloseReader_1 {
	public static void main(String[] args) throws IOException {
		Console cons = System.console();
		Reader reader = cons.reader();
		// Invoking close() on the reader from the console will not close the
		// underlying stream.
		reader.close();
	}
}
