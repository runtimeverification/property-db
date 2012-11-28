import java.io.*;

public class Console_FillZeroPassword_1 {
	public static void main(String[] args) {
		Console cons = System.console();
		char[] passwd = cons.readPassword();
		// Zeroing the returned password is recommended to minimize the lifetime
		// of sensitive data in memory.
//		java.util.Arrays.fill(passwd, ' ');
	}
}

