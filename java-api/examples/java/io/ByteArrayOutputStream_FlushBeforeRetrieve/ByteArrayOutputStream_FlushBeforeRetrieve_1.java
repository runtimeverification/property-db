import java.io.*;

public class ByteArrayOutputStream_FlushBeforeRetrieve_1 {
	public static void main(String[] args) throws IOException {
		ByteArrayOutputStream underlying = new ByteArrayOutputStream();
		BufferedOutputStream buffered = new BufferedOutputStream(underlying, 10);
		buffered.write(1);
		// flush() is needed to make the above write() operation effective.
//		buffered.flush();
		byte[] contents = underlying.toByteArray();
	}
}

