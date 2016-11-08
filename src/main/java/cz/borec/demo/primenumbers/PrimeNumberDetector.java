package cz.borec.demo.primenumbers;

/**
 * Intended to consider if the given number is a <b>prime number</b> or not.
 * @author Petr
 *
 */
public interface PrimeNumberDetector {

	/**
	 * @param number
	 * @return true if the given number is a <b>prime number</b>.
	 */
	boolean isPrimeNumber(final int number);
}
