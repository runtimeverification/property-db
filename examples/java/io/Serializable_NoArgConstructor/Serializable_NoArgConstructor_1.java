import java.io.*;

public class Serializable_NoArgConstructor_1 {
	static class Super_1 {
		// The following hides the compiler-generating no-arg constructor,
		// violating the Serializable_NoArgConstructor property.
		private Super_1() {
		}
	}

	static class Sub_1 extends Super_1 implements Serializable {
	}

	public static void main(String[] args) {
		Object o1 = new Sub_1();
	}
}

