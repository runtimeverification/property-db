import java.io.*;

public class FileReaderTest {
	public static void main(String[] args) throws Exception {
		File file = File.createTempFile("javamoptest1", ".tmp");
		FileWriter writer = new FileWriter(file);
		writer.write("0123456789");
		writer.close();
		file.deleteOnExit();

		try{
			Reader reader = new FileReader(file);
			reader.mark(1);
			int c = reader.read();
			reader.reset();
			int d = reader.read();
			reader.reset();
			int e = reader.read();
			if (c == d && d == e)
				System.out.println("reset() was properly used.");
			else 
				throw new Exception("reset() did not preserve the value.");
		} catch (Exception e){
			System.err.println(e.getMessage());
		}
	}
}
