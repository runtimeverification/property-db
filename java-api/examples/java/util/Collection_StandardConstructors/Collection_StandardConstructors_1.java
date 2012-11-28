import java.util.*;

public class Collection_StandardConstructors_1 {
	// BadCollection0 does not define any of the 'standard' constructors.
	static class BadCollection0 extends AbstractCollection {
		public int size() {
			return 0;
		}

		public Iterator iterator() {
			return null;
		}
	}

	// BadCollection1 defines only one of the 'standard' constructors.
	static class BadCollection1 extends BadCollection0 {
		public BadCollection1() {
		}
	}

	// BadCollection2 defines only one of the 'standard' constructors.
	static class BadCollection2 extends BadCollection0 {
		public BadCollection2(Collection src) {
		}
	}

	// GoodCollection defines both of the 'standard' constructors.
	static class GoodCollection extends BadCollection0 {
		public GoodCollection() {
		}

		public GoodCollection(Collection src) {
		}
	}

	public static void main(String[] args) {
		Collection c1 = new GoodCollection();
		Collection c2 = new BadCollection1();
		Collection c3 = new BadCollection2(c1);
	}
}

