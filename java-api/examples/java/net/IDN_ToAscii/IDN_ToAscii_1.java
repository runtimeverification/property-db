import java.net.*;

public class IDN_ToAscii_1 {
	public static void main(String[] args) {
		toAscii(true, "abc");
		toAscii(false, "\uAC00\uB098\uB2E4");
	}

	private static void toAscii(boolean legal, String input) {
		try {
			IDN.toASCII(input);
		}
		catch (IllegalArgumentException e) {
			if (legal)
				throw e;
		}
	}
}

