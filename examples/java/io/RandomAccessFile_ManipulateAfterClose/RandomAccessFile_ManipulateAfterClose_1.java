import java.io.*;

public class RandomAccessFile_ManipulateAfterClose_1 {
	public static void main(String[] args) throws FileNotFoundException, IOException {
		File file = File.createTempFile("tmp", ".tmp");
		RandomAccessFile raf = new RandomAccessFile(file, "rw");
		raf.writeByte(100);
		raf.close();

		// A closed random access file cannot perform input or output operations.
		raf.writeByte(101);
	}
}

