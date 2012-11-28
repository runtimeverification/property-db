import java.net.*;

public class PasswordAuthentication_FillZeroPassword_1 {
	public static void main(String[] args) {
		String user = "user";
		String passwd = "passwd";
		PasswordAuthentication auth = new PasswordAuthentication(user, passwd.toCharArray());

		char[] obtained = auth.getPassword();

//		java.util.Arrays.fill(obtained, ' ');
	}
}

