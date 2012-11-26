import java.io.*;

public class ObjectInputStreamTest {
	public static void main(String[] args) throws Exception {
		File file = File.createTempFile("javamoptest1", ".tmp");
		{
			FileOutputStream fos = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeInt(12345);
			oos.close();
		}

		FileInputStream fis = new FileInputStream(file);

		try {
			ObjectInputStream stream = new ObjectInputStream(fis);
			stream.mark(1);
			int c = stream.read();
			stream.reset();
			int d = stream.read();
			stream.reset();
			int e = stream.read();
			if (c == d && d == e)
				System.out.println("ObjectInputStream.reset() was properly used.");
			else
				throw new Exception("ObjectInputStream.reset() did not preserve the value.");
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}
