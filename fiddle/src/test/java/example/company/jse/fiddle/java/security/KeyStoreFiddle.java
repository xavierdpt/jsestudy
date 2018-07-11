package example.company.jse.fiddle.java.security;

import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import org.junit.Test;

public class KeyStoreFiddle {

	@Test
	public void fiddle1() throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {

		KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
		char[] password = "password".toCharArray();
		char[] password2 = "password2".toCharArray();
		ks.load(null, password);

		ks.store(System.out, password2);

	}

}
