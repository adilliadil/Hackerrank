import java.util.Scanner;

public class ANDProduct {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int T = in.nextInt();
		
		for (int t = 0; t < T; t++) {
			long A = in.nextLong();
			long B = in.nextLong();
			long shiftValue;
			long result = 0;
			for (int i = 31; i >= 0; i--) {
				shiftValue = (1 << i);
				if ((shiftValue & A) == (shiftValue & B))
					result += shiftValue & A;
				else
					break;
			}
			System.out.println(result);
		}
	}
	
}
