package cn;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class CipherNote {

	public CipherNote(String passphrase) {
		generateKey(passphrase);
	}

	private Cipher cipher;
	private SecretKeySpec skeySpec;
	private final static String configFile = "cnote.config";
	
	private void generateKey(String passphrase) {
		try {
			this.cipher = Cipher.getInstance("AES");

			MessageDigest sha = MessageDigest.getInstance("SHA-1");
			byte[] bKey = new byte[16];
			// 128bit sha-1 key
			System.arraycopy(sha.digest(passphrase.getBytes("UTF-8")), 0, bKey, 0, 16);
			this.skeySpec = new SecretKeySpec(bKey, "AES");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public String encrypt(String str) {
		try {
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
			byte[] estr = cipher.doFinal(str.getBytes("UTF-8"));
			return new String(Base64.encodeBase64(estr));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String decrypt(String str) throws Exception {
		cipher.init(Cipher.DECRYPT_MODE, skeySpec);
		byte[] base64 = Base64.decodeBase64(str);
		return new String(cipher.doFinal(base64), "UTF-8");
	}
	
	public void setupCipher() {
		String randomStr = generateRandomString();
		String eRandomStr = encrypt(randomStr);
		
		Writer writer;
		try {
			writer = new FileWriter(configFile);
			writer.write(eRandomStr);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean checkPassphrase(String passphrase) {
		String eRandomStr;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(configFile));
			eRandomStr = reader.readLine();
			reader.close();
			
			generateKey(passphrase);
			decrypt(eRandomStr);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			return false; // incorrect passphrase
		}
		return true;
	}

	private static String generateRandomString() {
		SecureRandom random = new SecureRandom();
		return new BigInteger(130, random).toString(32);
	}
}
