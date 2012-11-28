import java.net.*;
import java.io.IOException;
import java.io.OutputStream;

public class Socket_CloseOutput_1 {
	public static void main(String[] args) throws IOException, SocketException {
		Socket sock = new Socket("www.illinois.edu", 80);
		OutputStream input = sock.getOutputStream();

		sock.close();

		// The above close() also closes the input stream. The following call
		// should trigger the property handler.
		input.write(1);
	}
}

