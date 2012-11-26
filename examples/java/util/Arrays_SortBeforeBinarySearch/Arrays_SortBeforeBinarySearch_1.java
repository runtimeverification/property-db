import java.util.*;

public class Arrays_SortBeforeBinarySearch_1 {
	public static void main(String[] args) {
		Object[] arr = new Object[5];
		for (int i = 0; i < arr.length; ++i)
			arr[i] = arr.length - i;

		// 'arr' has not been sorted.
		{
			Arrays.binarySearch(arr, 3);
		}

		// Now, 'arr' is sorted.
		{
			Arrays.sort(arr);
			Arrays.binarySearch(arr, 3);
		}

		// 'arr' is no longer sorted.
		{
			arr[2] = 4;
			Arrays.binarySearch(arr, 3);
		}
	}
}

