import java.io.*;

public class PushbackInputStream_UnreadAheadLimit_1 {
	public static void main(String[] args) throws IOException {
		byte[] buffer = { 1, 2, 3, 4, 5};
		ByteArrayInputStream bais = new ByteArrayInputStream(buffer);

		PushbackInputStream pis = new PushbackInputStream(bais);
		pis.unread(1);

		// Since the internal buffer can hold only one byte, the above
		// unread(1) already filled the buffer.
		pis.unread(2);
	}
}

