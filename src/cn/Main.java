package cn;
import java.math.BigInteger;
import java.security.SecureRandom;


public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SecureRandom random = new SecureRandom();
		print(new BigInteger(130, random).toString(32));
	}
	
	private static void print(String str) {
		System.out.println(str);
	}
}
