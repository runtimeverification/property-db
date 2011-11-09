import java.io.*;

public class PushbackInputStream_UnreadAheadLimit_1 {
	public static void main(String[] args) throws IOException {
		byte[] buffer = { 1, 2, 3, 4, 5};
		ByteArrayInputStream bais = new ByteArrayInputStream(buffer);

		PushbackInputStream pis = new PushbackInputStream(bais);
		pis.unread(1);
		pis.unread(2);
	}
}

