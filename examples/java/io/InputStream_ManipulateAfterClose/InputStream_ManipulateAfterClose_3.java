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
		// the handler of the InputStream_ManipulateAfterClose property.
		// If the property uses the 'call' pointcut, as opposed to the
		// 'execution' pointcut, the property handler will not be triggered.
		byte[] buf = new byte[5];
		int j = fis.read(buf);

		// Note that FileInputStream.read() does not fire an event because it is
		// a native method. As a result, if one replace the above fis.read(buf)
		// by fis.read(), the propery handler will not be triggered.
	}
}

