import java.util.*;

public class ArrayDeque_NonNull_1 {
	public static void main(String[] args) {
		ArrayDeque<Integer> q = new ArrayDeque<Integer>();

		// null is not permitted; so, any of these will fail.
		q.add(null);
		q.offer(null);
		q.push(null);
	}
}
