import java.io.*;

public class File_DeleteTempFile_1 {
	public static void main(String[] args) throws IOException {
		File file = File.createTempFile("pre", ".tmp");

		// A temporary file can be deleted either explicitly or automatically.
//		file.delete();
//		file.deleteOnExit();
	}
}

