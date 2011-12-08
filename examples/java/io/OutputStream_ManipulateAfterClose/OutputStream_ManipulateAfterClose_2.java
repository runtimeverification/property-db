import java.io.*;

public class OutputStream_ManipulateAfterClose_2 {
	public static void main(String[] args) throws IOException {
		OutputStream output = new ByteArrayOutputStream();
		output.write(1);
		output.close();

		// ByteArrayOutputStream is an exceptional subclass of OutputStream;
		// write() after close() is valid.
		output.write(2);
	}
}

