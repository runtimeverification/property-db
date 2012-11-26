import java.net.*;

public class URL_SetURLStreamHandlerFactory_1 {
	public static void main(String[] args) {
		URL.setURLStreamHandlerFactory(null);

		// URL.setURLStreamHandlerFactory() can be called at most once.
		URL.setURLStreamHandlerFactory(null);
	}
}

