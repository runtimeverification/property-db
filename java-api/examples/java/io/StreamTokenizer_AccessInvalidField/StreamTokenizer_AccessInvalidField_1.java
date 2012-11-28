import java.io.*;

public class StreamTokenizer_AccessInvalidField_1 {
	public static void main(String[] args) throws IOException {
		StringReader reader = new StringReader("abc");

		StreamTokenizer tokenizer = new StreamTokenizer(reader);
		for (boolean readeof = false; !readeof; ) {
			int type = tokenizer.nextToken();
			switch (type) {
				case StreamTokenizer.TT_WORD:
					{
						// In case of TT_WORD, sval should be accessed.
						double nval = tokenizer.nval;
//						String sval = tokenizer.sval;
					}
					break;
				case StreamTokenizer.TT_NUMBER:
					{
						// In case of TT_NUMBER, nval should be accessed.
//						double nval = tokenizer.nval;
						String sval = tokenizer.sval;
					}
					break;
				case StreamTokenizer.TT_EOL:
					break;
				case StreamTokenizer.TT_EOF:
					readeof = true;
					break;
			}
		}
	}
}

