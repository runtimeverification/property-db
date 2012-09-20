import java.net.*;

public class HttpCookie_Domain_1 {
	public static void main(String[] args) throws SocketException {
		HttpCookie cookie = new HttpCookie("name", "val");

		set(true, cookie, "Abc012");
		set(true, cookie, "Abc012_");
		set(true, cookie, "Abc+012");

		// A quoted-string can contain some special characters.
		set(true, cookie, "\"Abc 012\"");
		set(true, cookie, "\"Abc=012\"");
		set(true, cookie, "\"Abc\t012\"");

		// The name cannot contain space, tab, or many special characters, such as =.
		set(false, cookie, "Abc 012");
		set(false, cookie, "Abc=012");
		set(false, cookie, "Abc\t012");
	}

	private static void set(boolean legal, HttpCookie cookie, String domain) {
		try  {
			cookie.setDomain(domain);
		}
		catch (IllegalArgumentException e) {
			if (legal)
				throw e;
		}
	}
}
