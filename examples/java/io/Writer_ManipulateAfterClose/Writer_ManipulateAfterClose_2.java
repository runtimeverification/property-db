import java.io.*;

public class Writer_ManipulateAfterClose_2 {
	public static void main(String[] args) throws IOException {
		Writer writer = new StringWriter();
		writer.write(100);
		writer.close();

		// StringWriter is an exceptional subclass of Writer;
		// write() after close() is valid.
		writer.write(101);
	}
}

