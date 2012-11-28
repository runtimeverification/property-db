import java.net.*;
import java.io.InputStream;

public class URLConnection_Connect_1 {
	public static void main(String[] args) throws Exception {
		URL url = new URL("http://www.illinois.edu");
		URLConnection conn = url.openConnection();

		// The URLConnection is connected to the server.
		InputStream input = conn.getInputStream();

		// As URLConnection.connect() is called and the connection has already
		// been opened, the following call should trigger the property handler.
		conn.connect();
	}
}

