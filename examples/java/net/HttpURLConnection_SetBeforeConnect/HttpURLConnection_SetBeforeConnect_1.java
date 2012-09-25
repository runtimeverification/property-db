import java.net.*;
import java.io.InputStream;

public class HttpURLConnection_SetBeforeConnect_1 {
	public static void main(String[] args) throws Exception {
		URL url = new URL("http://www.illinois.edu");
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();

		// One can set options before a connection is made.
		conn.setRequestMethod("GET");

		// The URLConnection is connected to the server.
		InputStream input = conn.getInputStream();

		// Setting options after a connection is made is forbidden.
		conn.setRequestMethod("POST");
	}
}

