package com.ge.hc.healthlink;

import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.PropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.SecureRandom;
import java.util.Base64;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HealthlinkApplicationTests {

	private String jasyptPassword;

	@Autowired
	private StringEncryptor encryptor;

	@Test
	public void generateJasyptPassword() {
		String password = "Jan2019";
		try {
			KeyGenerator kg = KeyGenerator.getInstance("AES");
			kg.init(128, new SecureRandom(password.getBytes()));
			SecretKey sk = kg.generateKey();
			byte[] keyBytes = sk.getEncoded();
			jasyptPassword = Base64.getEncoder().encodeToString(keyBytes);
			System.out.println("jasypt.encryptor.password: " + jasyptPassword);

//			Key key = new SecretKeySpec(keyBytes, "AES");
			//encrypt algorithm/mode/padding
//			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
//			cipher.init(Cipher.ENCRYPT_MODE, key);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Test
	public void encryptInfo4Broker() {
		String username = encryptor.encrypt("artemisBroker");
		String password = encryptor.encrypt("GehcDi9it@l");
		System.out.println("broker server username: " + username);
		System.out.println("broker server password: " + password);
	}

	@Test
	public void encryptInfo4Database() {
		String username = encryptor.encrypt("postgres");
		String password = encryptor.encrypt("root");
		System.out.println("database username: " + username);
		System.out.println("database password: " + password);
	}

}

