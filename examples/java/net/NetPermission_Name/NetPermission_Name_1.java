import java.net.*;

public class NetPermission_Name_1 {
	public static void main(String[] args) {
		create(true, "*");
		create(true, "foo");
		create(true, "foo*");
		create(true, "foo.*");

		create(false, "*foo");
		create(false, "f*o");
	}

	private static void create(boolean legal, String name) {
		try {
			NetPermission p = new NetPermission(name);
		}
		catch (IllegalArgumentException e) {
			if (legal)
				throw e;
		}
	}
}

