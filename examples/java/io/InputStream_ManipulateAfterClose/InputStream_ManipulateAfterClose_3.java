import java.io.*;

public class InputStream_ManipulateAfterClose_3 {
	public static void main(String[] args) throws IOException {
		File file = File.createTempFile("javamoptest1", ".tmp");
		FileWriter writer = new FileWriter(file);
		writer.write("0123456789");
		writer.close();
		file.deleteOnExit();

		FileInputStream fis = new FileInputStream(file);
		BufferedInputStream bis = new BufferedInputStream(fis);

		int i = bis.read();

		// This will close not only 'bis' but also 'fis'.
		bis.close();

		// Since 'fis' has been also closed, the following should be caught by
		// the handler of the InputStream_ManipulateAfterClose property. However,
		// the property handler will not be triggered if the property is not
		// thoroughly applied to all the necessary class files. For example,
		// unless rt.jar is weaved, the following call will not fire any event,
		// causing the property handler not to be triggered.
		byte[] buf = new byte[5];
		int j = fis.read(buf);
	}
}

