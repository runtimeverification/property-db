import java.net.*;
import java.io.IOException;
import java.security.Permission;

public class URLConnection_OverrideGetPermission_1 {
	public static void main(String[] args) throws Exception {
		URLConnection a1 = new Inner_1();
		URLConnection a2 = new Inner_2();
	}

	static class Inner_1 extends URLConnection {
		Inner_1() {
			super(null);
		}

		@Override
		public void connect() throws IOException {
		}

		@Override
		public Permission getPermission() throws IOException {
			return null;
		}
	}

	static class Inner_2 extends URLConnection {
		Inner_2() {
			super(null);
		}

		@Override
		public void connect() throws IOException {
		}

		// getPermission() should have been overriden.
	}
}

