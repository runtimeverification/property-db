import java.util.*;

public class Collections_CopySize_1 {
	public static void main(String[] args) throws Exception {
		ArrayList<Integer> dest = new ArrayList<Integer>();
		dest.add(1);
		dest.add(2);

		ArrayList<Integer> src = new ArrayList<Integer>();
                src.add(1);
                src.add(2);
                src.add(3);
                
                // The length of destination is not as long as source.
                Collections.copy(dest, src);
	}
}

