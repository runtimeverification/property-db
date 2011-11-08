import java.io.*;

public class PipedInputStream_UnconnectedRead_2 {
	public static void main(String[] args) throws IOException {
		PipedInputStream pis = new PipedInputStream();

		// A pipe should be connected first in order to read.
		PipedOutputStream pos = new PipedOutputStream();
//		PipedOutputStream pos = new PipedOutputStream(pis);

		pos.write(1);
		int i = pis.read();
	}
}

