import java.io.*;

public class InputStreamReaderTest {
	public static void main(String[] args) throws Exception {

		try{
			Reader reader = new InputStreamReader(new ByteArrayInputStream("1234567890".getBytes()));
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
