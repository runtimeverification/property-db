import java.util.*;

public class SortedSet_Comparable_1 {
	public static void main(String[] args) throws Exception {
		SortedSet backing = new TreeSet();
		backing.add(new Integer(1));
		backing.add(new Integer(2));
		backing.add(new Integer(3));

		// SortedSet cannot have element which is not comparable.
		backing.add(new Object());

	}
}

