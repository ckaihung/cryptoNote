package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.FileReader;

import org.junit.Before;
import org.junit.Test;

import cn.CipherNote;


public class CipherNoteTest {
	private String plainText;
	private String passphrase;
	private String wrongPassphrase;
	
	@Before
	public void setUp() throws Exception {
		this.plainText = "testing1234";
		this.passphrase = "paasswasd";
		this.wrongPassphrase = "peasswasd";
	}
	
	@Test
	public void testEncrypt() {
		String str = plainText;
		String estr;
		String dstr = null;
		
		CipherNote cipher = new CipherNote(passphrase);
		CipherNote cipher2 = new CipherNote(passphrase);
		
		estr = cipher.encrypt(str);
		try {
			dstr = cipher2.decrypt(estr);
		} catch (Exception e) {
			fail("Decrypt Failed");
			e.printStackTrace();
		}
		
		assertEquals(str, dstr);
	}
	
	@Test(expected= Exception.class)
	public void testExceptionEncrypt() throws Exception {
		String str = plainText;
		String estr;
		String dstr = null;
		
		CipherNote cipher = new CipherNote(passphrase);
		CipherNote cipher2 = new CipherNote(wrongPassphrase);
		
		estr = cipher.encrypt(str);
		dstr = cipher2.decrypt(estr);
		
		assertNotEquals(str, dstr);
	}
	
	@Test
	public void testSetupCipher() throws Exception {
		CipherNote cipher = new CipherNote(passphrase);
		cipher.setupCipher();
		BufferedReader reader = new BufferedReader(new FileReader("cnote.config"));
		String estr = reader.readLine();
		reader.close();
		
		cipher.decrypt(estr);
	}
	
	@Test
	public void testCheckPassphrase() {
		CipherNote cipher = new CipherNote(passphrase);
		cipher.setupCipher();
		
		assertTrue(cipher.checkPassphrase(passphrase));
	}
	
	@Test
	public void testCheckWrongPassphrase() {
		CipherNote cipher = new CipherNote(passphrase);
		cipher.setupCipher();
		
		assertFalse(cipher.checkPassphrase(wrongPassphrase));
	}
}

