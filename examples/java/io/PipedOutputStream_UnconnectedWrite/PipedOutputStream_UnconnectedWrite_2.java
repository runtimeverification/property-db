import java.io.*;

public class PipedOutputStream_UnconnectedWrite_2 {
	public static void main(String[] args) throws IOException {
		PipedOutputStream pos = new PipedOutputStream();

		// A pipe should be connected first in order to write.
		PipedInputStream pis = new PipedInputStream();
//		PipedInputStream pis = new PipedInputStream(pos);

		pos.write(1);
	}
}

