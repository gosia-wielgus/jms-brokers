package pl.edu.agh.iosr.brokers;

import java.security.MessageDigest;
import java.util.Formatter;
import java.util.Random;

public class PasswordHash {
	private String hash;
	private String salt;
	private PasswordHash(String hash, String salt) {
		this.hash = hash;
		this.salt = salt;
	}
	
	private static Random random = new Random();
	private synchronized static String newSalt() {
		byte[] bytes = new byte[8];
		random.nextBytes(bytes);
		return bytesToString(bytes);
	}
	
	public static String bytesToString(byte[] bytes) {
	    Formatter formatter = new Formatter();
	    for (byte b : bytes)
	    {
	        formatter.format("%02x", b);
	    }
	    return formatter.toString();
	}
	
	public static PasswordHash fromPassword(String pass) {
		String salt = newSalt();
		return new PasswordHash(hashSalted(pass, salt), salt);
	}
	
	public static PasswordHash fromHash(String hash) {
		String[] list = hash.split(":");
		
		if (list.length != 3)
			throw new IllegalArgumentException(" \""+hash+"\" is not a legal salted hash ");
		
		return new PasswordHash(list[2], list[1]);
	}
	
	public boolean compareToPassword(String pass) {
		return hash.equals(hashSalted(pass, salt));
	}
	
	public String toString() {
		return ":"+salt+":"+hash;
	}
	private static final int ITERATIONS = 1000;
	private static String hashSalted(String pass, String salt) {
		String key = salt + "$" + pass;
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-1");
			for (int i=0; i<ITERATIONS; i++) {
				md.reset();
				byte[] result = md.digest(key.getBytes("UTF-8"));
				key = bytesToString(result);
			}
			return key;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
