import java.io.*;

public class FileInputStreamTest {
	public static void main(String[] args) throws Exception {
		File file = File.createTempFile("javamoptest1", ".tmp");
		FileWriter writer = new FileWriter(file);
		writer.write("0123456789");
		writer.close();
		file.deleteOnExit();

		try {
			InputStream stream = new FileInputStream(file);
			stream.mark(1);
			int c = stream.read();
			stream.reset();
			int d = stream.read();
			stream.reset();
			int e = stream.read();
			if (c == d && d == e)
				System.out.println("FileInputStream.reset() was properly used.");
			else
				throw new Exception("FileInputStream.reset() did not preserve the value.");
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}
