import java.io.*;

public class OutputStream_ManipulateAfterClose_2 {
	public static void main(String[] args) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeInt(1);
		oos.close();

		// A closed stream cannot perform output operations.
		// However, OutputStream_ManipulateAfterClose does not capture the
		// violation caused by the following method call, because writeInt() is
		// undefined in OutputStream. If the following was oos.write(2), a
		// violation would be captured.
		oos.writeInt(2);
	}
}

