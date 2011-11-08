import java.io.*;

public class OutputStream_ManipulateAfterClose_2 {
	public static void main(String[] args) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeInt(1);
		oos.close();

		// A closed stream cannot perform output operations.
		oos.writeInt(2);
	}
}

