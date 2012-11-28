import java.util.*;

public class ArrayDeque_Iterator_1 {
	public static void main(String[] args) throws Exception {
		ArrayDeque<Integer> q = new ArrayDeque<Integer>();
		q.add(1);
		q.add(2);

		Iterator i = q.iterator();

		i.hasNext();

		q.add(3);

		// The iterator is not valid anymore because the deque has been modified above.
		i.next();
	}
}

