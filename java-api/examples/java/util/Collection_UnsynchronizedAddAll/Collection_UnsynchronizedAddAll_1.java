import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

public class Collection_UnsynchronizedAddAll_1 {
	static final int numelem = 100000;

	private static void modify(Collection<Integer> c) {
		c.clear();
		for (int i = 0; i < numelem; ++i)
			c.add(i);
	}

	private static void test(Collection<Integer> col) throws Exception {
		final Vector<Integer> src = new Vector<Integer>();
		modify(src);

		Runnable r = new Runnable()
		{
			@Override
			public void run() {
				modify(src);
			}
		};
		Thread modifying = new Thread(r);

		// The other thread will be likely modifying the shared collection 'src',
		// while addAll() is accessing 'src'.
		modifying.start();
		col.addAll(src);

		modifying.join();
	}

	public static void main(String[] args) {
		try {
			ArrayBlockingQueue<Integer> que = new ArrayBlockingQueue<Integer>(numelem);
			test(que);
		}
		catch (Exception ignored) {
		}

		try {
			ArrayList<Integer> list = new ArrayList<Integer>(numelem);
			test(list);
		}
		catch (Exception ignored) {
		}
	}
}

