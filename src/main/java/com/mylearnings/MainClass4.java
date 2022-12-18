package com.mylearnings;

public class MainClass4 {

	public static void main(String[] args) {
		
		int arr[]= {-5,1,5,0,-7};
		
		System.out.println(largestAltitude(arr));

		// TODO Auto-generated method stub

	}

	public static int largestAltitude(int[] gain) {

		int max;

		int arr[] = new int[gain.length + 1];

		arr[0] = max = 0;

		int i = 1;
		while (i < gain.length) {

			arr[i] = arr[i - 1] + gain[i - 1];

			System.out.println(arr[i - 1] + gain[i - 1]);

			i++;

			if (arr[i] > max)
				max = arr[i];

		}

		// System.out.println(Arrays.toString(arr));

		return max;

	}

}
