package cz.agel.demo;

import static org.junit.Assert.*;
import cz.borec.demo.primenumbers.PrimeNumberDetectorImpl;

import org.junit.Test;

public class PrimeNumberDetectorImplTest {

	private static final int PRIME_NUMBER_COUNT = 1229;
	private PrimeNumberDetectorImpl detector = PrimeNumberDetectorImpl.getInstance();

	@Test
	public void test() {
		
		assertTrue(detector.isPrimeNumber(2));
		assertTrue(detector.isPrimeNumber(3));
		assertTrue(detector.isPrimeNumber(5));
		assertTrue(detector.isPrimeNumber(7));
		assertTrue(detector.isPrimeNumber(11));
		
		assertFalse(detector.isPrimeNumber(0));
		assertFalse(detector.isPrimeNumber(1));
		assertFalse(detector.isPrimeNumber(6));
		assertFalse(detector.isPrimeNumber(9));
		assertFalse(detector.isPrimeNumber(12));
		
		try {
			detector.isPrimeNumber(PrimeNumberDetectorImpl.RANGE + 100);
			fail("Exception should be thrown.");
		}
		catch(IndexOutOfBoundsException e) {
			System.out.println("OK");
		}
	}

	@Test
	public void testCount() {
		
		int count = 0;
		
		for (int i = 0; i < PrimeNumberDetectorImpl.RANGE; i++) {
			if(detector.isPrimeNumber(i)) {
				count ++;
			}
		}
		
		System.out.println(count);
		assertTrue(count == PRIME_NUMBER_COUNT);
	}

}
