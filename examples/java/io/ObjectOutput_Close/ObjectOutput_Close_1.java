import java.io.*;

public class ObjectOutput_Close_1 {
	public static void main(String[] args) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeInt(1);
		// close() must be called to release the resources.
//		oos.close();
	}
}

