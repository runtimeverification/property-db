import java.io.*;

public class File_LengthOnDirectory_1 {
	public static void main(String[] args) {
		File f = new File(".");

		long filesize = 0;
		if (f.isFile())
			filesize = f.length();
		else {
			// The return value of the following is unspecified because a File
			// object represents a directory.
			filesize = f.length();
		}
	}
}

