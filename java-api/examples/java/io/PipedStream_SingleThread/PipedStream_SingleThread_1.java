import java.io.*;

public class PipedStream_SingleThread_1 {
	public static void main(String[] args) throws IOException {
		PipedOutputStream pos = new PipedOutputStream();
		PipedInputStream pis = new PipedInputStream(pos);

		pos.write(1);
		int i = pis.read();
	}
}

