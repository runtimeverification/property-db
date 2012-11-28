import java.io.*;

public class PipedStream_SingleThread_3 {
	private static PipedOutputStream pos;
	private static PipedInputStream pis;

	public static void main(String[] args) throws IOException, InterruptedException {
		pos = new PipedOutputStream();
		pis = new PipedInputStream(pos);

		Thread thr2 = new Thread(new Runnable()
		{
			@Override
			public void run() {
				try {
					pos.write(1);
				}
				catch (IOException ignored) {
				}
			}
		});
		thr2.start();
		int i = pis.read();
		thr2.join();

		// The main thread reads data from the pipe in the above code, and then
		// relay it to another thread by reusing the same pipe. Although it does
		// not deadlock in this case, it's still bad practice.

		Thread thr3 = new Thread(new Runnable()
		{
			@Override
			public void run() {
				try {
					int i = pis.read();
				}
				catch (IOException ignored) {
				}
			}
		});
		thr3.start();
		pos.write(i);
		thr3.join();
	}
}

