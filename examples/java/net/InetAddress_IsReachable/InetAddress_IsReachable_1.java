import java.net.*;
import java.io.IOException;

public class InetAddress_IsReachable_1 {
	public static void main(String[] args) throws Exception {
		call(true, 0, 0);

		call(false, -1, -1);
		call(false, -1, 0);
		call(false, 0, -1);
	}

	private static void call(boolean legal, int ttl, int timeout) throws IOException, UnknownHostException {
		try {
			InetAddress addr = InetAddress.getLocalHost();
			addr.isReachable(null, ttl, timeout);
		}
		catch (IllegalArgumentException e) {
			if (legal)
				throw e;
		}
	}
}


