package cz.borec.demo.primenumbers;

import cz.borec.demo.primenumbers.PrimeNumberDetector;

public class PrimeNumberDetectorImpl implements PrimeNumberDetector {

	public static final int RANGE = 10000;
	private boolean[] bitmap = new boolean[RANGE];
	private static PrimeNumberDetectorImpl instance = null;

	@Override
	public boolean isPrimeNumber(final int number) {
		if (number >= RANGE || number < 0) {
			throw new IndexOutOfBoundsException(
					String.format(
							"Given number: {%d} must be between 0 and max value: {%d}.",
							number, RANGE - 1));
		}
		return !bitmap[number];
	}

	private PrimeNumberDetectorImpl() {
		super();
		initializePrimeNumberBitmap();
	}

	private void initializePrimeNumberBitmap() {
		initializePrimeNumberBitmap1();
		//initializePrimeNumberBitmap2();
	}

	private void initializePrimeNumberBitmap2() {
		bitmap[0] = bitmap[1] = true; // nula a jedna nejsou prvocisla
		
		int sqrt = (int) Math.sqrt(bitmap.length);
		
		for (int i = 2; i <= sqrt; i++) {
			if (bitmap[i] == true)
				continue;
			for (int j = 2 * i; j < bitmap.length; j += i) { // samotne citani
				bitmap[j] = true; // nemuze byt z definice prvocislem (je
									// nasobkem jineho cisla)
			}
		}

	}

	private void initializePrimeNumberBitmap1() {
		int range = RANGE - 1;

		long sqrt = (long) (Math.sqrt(range));

		for (int j = 3; j < sqrt; j += 2) {
			if (bitmap[j] == false) {
				for (int k = j; k <= range / j; k += 2) {
					bitmap[k * j] = true;
				}

			}
		}
		for (int l = 4; l < range; l += 2) {
			bitmap[l] = true;
		}
		bitmap[0] = bitmap[1] = true;
	}

	public static PrimeNumberDetectorImpl getInstance() {
		if (instance == null) {
			instance = new PrimeNumberDetectorImpl();
		}
		return instance;
	}

	/*
	 * public static void main(String[] args) { PrimeNumberDetectorImpl d = new
	 * PrimeNumberDetectorImpl(); for (int i = 0; i < 20; i++) {
	 * System.out.println(i + ": " + d.isPrimeNumber(i)); } }
	 */

}
