package example.company.jse.fiddle.java.security;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;

import example.company.jse.something.java.security.KeyPairGeneratorSomething;

public class KeyStoreFiddle {

	public void fiddle1() throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {

		KeyPair keyPair = KeyPairGeneratorSomething.createKeyPair();

		KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
		char[] password = "password".toCharArray();
		char[] password2 = "password2".toCharArray();
		char[] keyPassword = "keyPassword".toCharArray();
		ks.load(null, password);

		Certificate certificate = new Certificate("type") {

			@Override
			public void verify(PublicKey key, String sigProvider) throws CertificateException, NoSuchAlgorithmException,
					InvalidKeyException, NoSuchProviderException, SignatureException {
			}

			@Override
			public void verify(PublicKey key) throws CertificateException, NoSuchAlgorithmException,
					InvalidKeyException, NoSuchProviderException, SignatureException {
			}

			@Override
			public String toString() {
				return null;
			}

			@Override
			public PublicKey getPublicKey() {
				return keyPair.getPublic();
			}

			@Override
			public byte[] getEncoded() throws CertificateEncodingException {
				return new byte[] {};
			}
		};
		Certificate[] certificates = new Certificate[] { certificate };
		String keyAlias = "keyAlias";
		ks.setKeyEntry(keyAlias, keyPair.getPrivate(), keyPassword, certificates);

		ks.store(System.out, password2);

	}
}
