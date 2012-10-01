import java.net.*;
import java.io.IOException;
import java.io.InputStream;

public class Socket_CloseInput_1 {
	public static void main(String[] args) throws IOException, SocketException {
		Socket sock = new Socket("www.illinois.edu", 80);
		InputStream input = sock.getInputStream();

		sock.close();

		// The above close() also closes the input stream. The following call
		// should trigger the property handler.
		input.read();
	}
}

