import java.io.*;

public class Serializable_UID_1 implements Serializable {
	// It is strongly recommended that all serializable classes explicitly
	// declare serialVersionUID values.
//	private static final long serialVersionUID = 62L;

	public static void main(String[] args) {
		Object o1 = new Inner_1();
		Object o2 = new Inner_2();
		Object o3 = new Inner_3();
	}

	static class Inner_1 implements Serializable {
		// A serialVersionUID must be static, final, and of type long.
		private final long serialVersionUID = 63L;
	}

	static class Inner_2 implements Serializable {
		// A serialVersionUID must be static, final, and of type long.
		private static long serialVersionUID = 64L;
	}

	static class Inner_3 implements Serializable {
		// A serialVersionUID must be static, final, and of type long.
		private static final int serialVersionUID = 65;
	}
}

