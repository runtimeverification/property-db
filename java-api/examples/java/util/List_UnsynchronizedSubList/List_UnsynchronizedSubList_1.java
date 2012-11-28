import java.util.*;

public class List_UnsynchronizedSubList_1 {
	public static void main(String[] args) throws Exception {
		ArrayList<Integer> backing = new ArrayList<Integer>();
		backing.add(1);
		backing.add(2);
		backing.add(3);

		List<Integer> sub = backing.subList(0, 2);
		int i0 = sub.get(0);

		backing.add(4);

		// Since the backing list was modified by the above line, the sublist was
		// invalidated.
		int i1 = sub.get(1);
	}
}

