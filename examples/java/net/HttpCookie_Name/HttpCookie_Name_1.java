import java.net.*;

public class HttpCookie_Name_1 {
	public static void main(String[] args) throws SocketException {
		create(true, "Abc012");
		create(true, "Abc012_");
		create(true, "Abc+012");

		// The name cannot contain space, tab, or many special characters, such as =.
		create(false, "Abc 012");
		create(false, "Abc=012");
		create(false, "Abc\t012");
	}

	private static void create(boolean legal, String name) {
		try  {
			HttpCookie cookie = new HttpCookie(name, "val");
		}
		catch (IllegalArgumentException e) {
			if (legal)
				throw e;
		}
	}
}


