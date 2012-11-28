import java.net.*;
import java.io.UnsupportedEncodingException;

public class URLEncoder_EncodeUTF8_1 {
	public static void main(String[] args) throws UnsupportedEncodingException {
		URLEncoder.encode("Hello", "utf-8");

		// As UTF-8 is recommended, the following should trigger the property
		// handler.
		URLEncoder.encode("Hello", "utf-16");
	}
}

