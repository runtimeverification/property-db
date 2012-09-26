import java.net.*;

public class NetPermission_Actions_1 {
	public static void main(String[] args) {
		create(true, null);

		create(false, "foo");
	}

	private static void create(boolean legal, String actions) {
		try {
			NetPermission p = new NetPermission("*", actions);
		}
		catch (IllegalArgumentException e) {
			if (legal)
				throw e;
		}
	}
}

