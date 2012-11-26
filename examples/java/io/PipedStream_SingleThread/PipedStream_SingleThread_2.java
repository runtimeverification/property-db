import java.io.*;

public class PipedStream_SingleThread_2 {
	private static PipedOutputStream pos;
	private static PipedInputStream pis;

	public static void main(String[] args) throws IOException, InterruptedException {
		pos = new PipedOutputStream();
		pis = new PipedInputStream(pos);

		Runnable r = new Runnable()
		{
			@Override
			public void run() {
				try {
					int i = pis.read();

					// It is recommended not to read ad write in a thread.
					pos.write(2);
				}
				catch (IOException ignored) {
				}
			}
		};
		Thread thr2 = new Thread(r);
		thr2.start();

		pos.write(1);

		thr2.join();
	}
}

