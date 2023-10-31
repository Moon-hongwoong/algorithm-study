package dp;

import java.util.Scanner;

public class Yy_1904 {
	
	static int n;
	static long[] arr;
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		n = sc.nextInt();
		arr = new long[n+1];
		
		System.out.println(dp(n));
	}
	
	static long dp(int k) {
		
		arr[1] = 1;
		
		if(n < 2) return 1;
		
		arr[2] = 2;
		
		for(int i=3; i<=n; i++) 
			arr[i] = (arr[i-1] + arr[i-2]) % 15746;
		
		return arr[k];
	}

}