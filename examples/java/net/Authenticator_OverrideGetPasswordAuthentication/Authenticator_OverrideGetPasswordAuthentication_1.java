import java.net.*;

public class Authenticator_OverrideGetPasswordAuthentication_1 {
	public static void main(String[] args) throws Exception {
		Authenticator a1 = new Inner_1();
		Authenticator a2 = new Inner_2();
	}

	static class Inner_1 extends Authenticator {
		@Override
		protected PasswordAuthentication getPasswordAuthentication() {
			return null;
		}
	}

	static class Inner_2 extends Authenticator {
		// getPasswordAuthentication() should have been overriden.
	}
}

