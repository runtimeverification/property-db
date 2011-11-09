import java.io.*;

public class StringWriter_Close_1 {
	public static void main(String[] args) throws IOException {
		StringWriter writer = new StringWriter();
		// close() has no effect.
		writer.close();
	}
}

