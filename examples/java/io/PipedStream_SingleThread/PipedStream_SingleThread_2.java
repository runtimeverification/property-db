import java.io.*;

public class PipedStream_SingleThread_2 {
	private static PipedOutputStream pos;

	public static void main(String[] args) throws IOException, InterruptedException {
		pos = new PipedOutputStream();

		Runnable r = new Runnable()
		{
			@Override
			public void run() {
				try {
					PipedInputStream pis = new PipedInputStream(pos);
					int i = pis.read();
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

