package cz.borec.demo;

import cz.borec.demo.primenumbers.PrimeNumberDetectorImpl;

public class A {
	
	private PrimeNumberDetectorImpl p = PrimeNumberDetectorImpl.getInstance();
	
	boolean exec(String a, String b) {
		int s = 0;
		for (int i = 0; i < a.length(); i++) {
			//if(a.charAt(i))
		}
		
		return false;
		
	}
	
	public static void main(String[] args) {
		new A().main1(args);
	}
	
	public void main1(String[] args) {
		int[] a = new int[10000];
		int j = 0;
		for (int i = 0; i < 10000; i++) {
			if(p.isPrimeNumber(i)) {
				a[j++] = i;
				System.out.println(i);
			}
		}
		System.out.println(j);
		System.out.println(a[31]);
	}
}
