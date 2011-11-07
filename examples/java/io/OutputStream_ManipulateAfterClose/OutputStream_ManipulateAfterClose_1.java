import java.io.*;

public class OutputStream_ManipulateAfterClose_1 {
	public static void main(String[] args) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		baos.write(1);
		baos.close();

		// A closed stream cannot perform output operations.
		baos.write(2);
	}
}

