package org.farm.utils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Centered {

	private int max = 0, max2;
	private static int stackSize;
	private static int topOfStack = -1;
	private static int[] stack;
	private static Scanner s;

	public Centered(int size) {
		this.stackSize = size;
		this.stack = new int[size];
	}

	public void push(int val) {
		if (topOfStack + 1 < stackSize) {
			topOfStack++;
			stack[topOfStack] = val;
			if (val > max) {
				max2 = max;
				max = val;
			}
			System.out.println(max);
		}
	}

	public int pop() {
		if (topOfStack >= 0) {
			max = max2;
			max2 = 0;
			stack[topOfStack] = -1;
			return stack[topOfStack--];
		} else
			return -1;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// int a[] = { 1, 1 };
		// int val = sum(a);
		int a = 5;
		a = a >> 1;
		char arr[] = { 'a', 'b', 'c' };
		// char[] result = f(arr, 1, 0);
		// System.out.println(Arrays.toString(result));
		// System.out.println(val);
		double price = 4.000;
		DecimalFormat format = new DecimalFormat("0.#");
		System.out.println(format.format(price));

		int ax = 60, bx = 13, cx = 0;
		cx = ax ^ bx;
		System.out.println("a XOR b" + cx);
		int N, x = 1, q;
		s = new Scanner(System.in);
		do {
			System.out.println("Insert N");
			N = s.nextInt();

		} while (N < 1 || N > 100000);

		Centered c = new Centered(N);
		/*
		 * for (int i = 0; i < N; i++) { do { String[] arrRowItems = s.nextLine().split(" ");
		 * s.skip("(\r\n|[\n\r\u2028\u2029\u0085])?"); q = Integer.parseInt(arrRowItems[0]); if (arrRowItems.length > 1)
		 * x = Integer.parseInt(arrRowItems[1]); System.out.println("Insert QUery");
		 * 
		 * q = s.nextInt(); if (q == 1) {
		 * 
		 * System.out.println("Insert X"); x = s.nextInt(); }
		 * 
		 * } while ((x < 1 || x > 1000000000) || (q < 1 || q > 3));
		 * 
		 * if (q == 1) { c.push(x); } else if (q == 2) { int val = c.pop(); } else if (q == 3) {
		 * System.out.println(c.max); } }
		 */
		// c.push(12);
		// c.push(10);
		Map<Integer, List<Integer>> seq = new HashMap<Integer, List<Integer>>(10);
		List<Integer> list = new ArrayList<Integer>();
		seq.put(0, new ArrayList<Integer>());
		seq.get(0).add(2);
		System.out.println(seq.get(0).size());
		seq.put(1, new ArrayList<Integer>());
		System.out.println(seq.get(1).size());

	}

	private static int check(int[] a) {
		int size = a.length;
		int flag = 0;
		if (size % 2 == 0 || size == 0)
			return 0;
		else {
			int mid = a.length / 2;
			int last = size - 1;
			for (int i = 0; i < mid; i++) {
				if (a[i] <= a[mid] || a[last - i] <= a[mid]) {
					flag = 1;
					break;
				}
				continue;
			}
			if (flag == 0)
				return 1;
			else
				return 0;
		}
	}

	public static int sum(int a[]) {
		int sum_odd = 0, sum_even = 0;

		if (a.length <= 0)
			return 0;
		else {
			for (int i = 0; i < a.length; i++) {
				if (a[i] % 2 == 0)
					sum_even = sum_even + a[i];
				else
					sum_odd = sum_odd + a[i];
			}
		}
		return sum_odd - sum_even;
	}

	public static char[] f(char a[], int start, int len) {
		int i = 1, j = 0;
		if (start < 0 || len < 0)
			return null;
		else if ((a.length - start) < len)
			return null;
		else {
			char[] s = new char[len];
			while (i <= len) {
				s[j] = a[start];
				i++;
				j++;
				start++;
			}
			return s;
		}
	}

}
