import java.net.*;
import java.io.IOException;

public class ContentHandler_GetContent_1 {
	public static void main(String[] args) throws Exception {
		ContentHandler handler = new MyContentHandler();
		Object content = handler.getContent(null);
	}

	static class MyContentHandler extends ContentHandler {
		@Override
		public Object getContent(URLConnection urlc) throws IOException {
			return null;
		}
	}
}

