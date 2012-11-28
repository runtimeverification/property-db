import java.io.*;

public class PipedOutputStream_UnconnectedWrite_1 {
	public static void main(String[] args) throws IOException {
		PipedOutputStream pos = new PipedOutputStream();
		PipedInputStream pis = new PipedInputStream();

		// A pipe should be connected first in order to write.
//		pis.connect(pos);

		pos.write(1);
	}
}

