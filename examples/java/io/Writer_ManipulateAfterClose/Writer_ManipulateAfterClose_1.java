import java.io.*;

public class Writer_ManipulateAfterClose_1 {
	public static void main(String[] args) throws IOException {
		StringWriter writer = new StringWriter();
		writer.write(100);
		writer.close();

		// A closed writer cannot perform output operations.
		writer.write(101);
	}
}

