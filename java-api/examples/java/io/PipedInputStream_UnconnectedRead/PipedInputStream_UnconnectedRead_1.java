import java.io.*;

public class PipedInputStream_UnconnectedRead_1 {
	public static void main(String[] args) throws IOException {
		PipedInputStream pis = new PipedInputStream();
		PipedOutputStream pos = new PipedOutputStream();

		// A pipe should be connected first in order to read.
//		pos.connect(pis);

		int i = pis.read();
	}
}

