import java.io.*;

public class Console_CloseWriter_1 {
	public static void main(String[] args) throws IOException {
		Console cons = System.console();
		Writer writer = cons.writer();
		// Invoking close() on the writer from the console will not close the
		// underlying stream.
		writer.close();
	}
}

