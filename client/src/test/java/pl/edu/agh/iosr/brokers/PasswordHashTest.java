package pl.edu.agh.iosr.brokers;

import static org.junit.Assert.*;

import org.junit.Test;

public class PasswordHashTest {

	@Test
	public void testCompareToPassword() {
		assertPasswordEqual("hello");
		assertPasswordEqual("a password that is correct090@#$@$@#$)()\n");
		assertPasswordsNotEqual("a","b");
	}

	public void assertPasswordEqual(String p1) {
		assertPasswords(p1, p1, true);
	}
	
	public void assertPasswordsNotEqual(String p1, String p2) {
		assertPasswords(p1, p2, false);
		assertPasswords(p2, p1, false);
	}
	
	public void assertPasswords(String p1, String p2, boolean equal) {
		PasswordHash hash = PasswordHash.fromPassword(p1);
		assertEquals(hash.compareToPassword(p2), equal);
		PasswordHash hashCopy = PasswordHash.fromHash(hash.toString());
		assertEquals(hashCopy.compareToPassword(p2), equal);
		
		assertTrue(hashCopy.toString().equals(hash.toString()));
		PasswordHash anotherHash = PasswordHash.fromPassword(p1);
		assertFalse(anotherHash.toString().equals(hash.toString()));
		
	}
	
}
