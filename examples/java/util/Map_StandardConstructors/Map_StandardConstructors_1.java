import java.util.*;

public class Map_StandardConstructors_1 {
	// BadMap0 does not define any of the 'standard' constructors.
	static class BadMap0<K, V> extends AbstractMap<K, V> {
		public Set<Map.Entry<K, V>> entrySet() {
			return null;
		}
	}

	// BadMap1 defines only one of the 'standard' constructors.
	static class BadMap1 extends BadMap0 {
		public BadMap1() {
		}
	}

	// BadMap2 defines only one of the 'standard' constructors.
	static class BadMap2 extends BadMap0 {
		public BadMap2(Map src) {
		}
	}

	// GoodMap defines both of the 'standard' constructors.
	static class GoodMap extends BadMap0 {
		public GoodMap() {
		}

		public GoodMap(Map src) {
		}
	}

	public static void main(String[] args) {
		Map c1 = new GoodMap();
		Map c2 = new BadMap1();
		Map c3 = new BadMap2(c1);
	}
}

