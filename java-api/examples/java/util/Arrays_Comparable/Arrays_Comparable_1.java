import java.util.*;

public class Arrays_Comparable_1 {
	static class Noncomparable {
		public int i;

		Noncomparable(int i) {
			this.i = i;
		}
	}

	public static void main(String[] args) {
		Object[] objs = new Object[3];
		objs[0] = new Noncomparable(0);
		objs[1] = new Noncomparable(1);
		objs[2] = new Noncomparable(2);

		Arrays.sort(objs);
	}
}

