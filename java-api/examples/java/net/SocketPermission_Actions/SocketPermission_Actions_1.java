import java.net.*;

public class SocketPermission_Actions_1 {
	public static void main(String[] args) {
		create(true, "listen");
		create(true, "listen,accept");

		create(false, "");
		create(false, "foo");
		create(false, "foo,listen");
		create(false, "listen,foo");
	}

	private static void create(boolean legal, String actions) {
		try {
			SocketPermission p = new SocketPermission("", actions);
		}
		catch (IllegalArgumentException e) {
			if (legal)
				throw e;
		}
	}
}

