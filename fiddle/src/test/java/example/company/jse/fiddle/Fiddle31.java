package example.company.jse.fiddle;

import java.security.AlgorithmParameterGenerator;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Policy;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.cert.CertPathBuilder;
import java.security.cert.CertPathValidator;
import java.security.cert.CertStore;
import java.security.cert.CertStoreParameters;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import javax.security.auth.login.Configuration;
import javax.security.auth.login.Configuration.Parameters;
import javax.security.sasl.Sasl;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;

import org.junit.Test;

import example.company.tox.common.Common;

public class Fiddle31 {

	private static final String DOM = "DOM";
	private static final String PKIX = "PKIX";
	private static final String ECDSA = "ECDSA";
	private static final String NONE = "NONE";
	private static final String AND2 = "and";
	private static final String WITH2 = "with";
	private static final String PBKDF2 = "PBKDF2";
	private static final String MD2 = "MD2";
	private static final String ECDH = "ECDH";
	private static final String PADDING = "Padding";
	private static final String PKCS1 = "PKCS1";
	private static final String RSA = "RSA";
	private static final String ARCFOUR = "ARCFOUR";
	private static final String NO_PADDING = "NoPadding";
	private static final String OFB = "OFB";
	private static final String ECB = "ECB";
	private static final String CFB = "CFB";
	private static final String CBC = "CBC";
	private static final String SLASH = "/";
	private static final String _192 = "192";
	private static final String WRAP = "Wrap";
	private static final String _40 = "40";
	private static final String RC4 = "RC4";
	private static final String RC2 = "RC2";
	private static final String TRIPLE = "Triple";
	private static final String MD5 = "MD5";
	private static final String _512 = "512";
	private static final String _384 = "384";
	private static final String _224 = "224";
	private static final String SHA = "SHA";
	private static final String _256 = "256";
	private static final String _128 = "128";
	private static final String B = "_";
	private static final String AND = "And";
	private static final String SHA1 = "SHA1";
	private static final String HMAC = "Hmac";
	private static final String WITH = "With";
	private static final String S2 = "S2";
	private static final String PBE = "PBE";
	private static final String OAEP = "OAEP";
	private static final String GCM = "GCM";
	private static final String EC = "EC";
	private static final String EDE = "ede";
	private static final String DES = "DES";
	private static final String BLOWFISH = "Blowfish";
	private static final String AES = "AES";
	private static final String DIFFIE_HELLMAN = "DiffieHellman";
	private static final String DSA = "DSA";

	public void fiddle() throws NoSuchAlgorithmException, InvalidAlgorithmParameterException, CertificateException,
			NoSuchPaddingException, KeyStoreException {

		AlgorithmParameterGenerator.getInstance(DSA);
		AlgorithmParameterGenerator.getInstance(DIFFIE_HELLMAN);

		AlgorithmParameters.getInstance(AES);
		AlgorithmParameters.getInstance(BLOWFISH);
		AlgorithmParameters.getInstance(DES);
		AlgorithmParameters.getInstance(DES + EDE);
		AlgorithmParameters.getInstance(DSA);
		AlgorithmParameters.getInstance(DIFFIE_HELLMAN);
		AlgorithmParameters.getInstance(EC);
		AlgorithmParameters.getInstance(GCM);
		AlgorithmParameters.getInstance(OAEP);
		AlgorithmParameters.getInstance(PBE);
		AlgorithmParameters.getInstance(PBE + S2);
		AlgorithmParameters.getInstance(PBE + WITH + HMAC + SHA1 + AND + AES + B + _128);
		AlgorithmParameters.getInstance(PBE + WITH + HMAC + SHA1 + AND + AES + B + _256);
		AlgorithmParameters.getInstance(PBE + WITH + HMAC + SHA + _224 + AND + AES + B + _128);
		AlgorithmParameters.getInstance(PBE + WITH + HMAC + SHA + _224 + AND + AES + B + _256);
		AlgorithmParameters.getInstance(PBE + WITH + HMAC + SHA + _256 + AND + AES + B + _128);
		AlgorithmParameters.getInstance(PBE + WITH + HMAC + SHA + _256 + AND + AES + B + _256);
		AlgorithmParameters.getInstance(PBE + WITH + HMAC + SHA + _384 + AND + AES + B + _128);
		AlgorithmParameters.getInstance(PBE + WITH + HMAC + SHA + _384 + AND + AES + B + _256);
		AlgorithmParameters.getInstance(PBE + WITH + HMAC + SHA + _512 + AND + AES + B + _128);
		AlgorithmParameters.getInstance(PBE + WITH + HMAC + SHA + _512 + AND + AES + B + _256);
		AlgorithmParameters.getInstance(PBE + WITH + MD5 + AND + DES);
		AlgorithmParameters.getInstance(PBE + WITH + MD5 + AND + TRIPLE + DES);
		AlgorithmParameters.getInstance(PBE + WITH + SHA1 + AND + DES + EDE);
		AlgorithmParameters.getInstance(PBE + WITH + SHA1 + AND + RC2 + B + _128);
		AlgorithmParameters.getInstance(PBE + WITH + SHA1 + AND + RC2 + B + _40);
		AlgorithmParameters.getInstance(PBE + WITH + SHA1 + AND + RC4 + B + _128);
		AlgorithmParameters.getInstance(PBE + WITH + SHA1 + AND + RC4 + B + _40);
		AlgorithmParameters.getInstance(RC2);

		CertPathBuilder.getInstance(PKIX);

		CertPathValidator.getInstance(PKIX);

		CertStoreParameters params = null;
		CertStore.getInstance("Collection", params);
		CertStore.getInstance("LDAP", params);
		CertStore.getInstance("com.sun.security.IndexedCollection", params);

		CertificateFactory.getInstance("X.509");

		Cipher.getInstance(AES);
		Cipher.getInstance(AES + WRAP);
		Cipher.getInstance(AES + WRAP + B + _128);
		Cipher.getInstance(AES + WRAP + B + _192);
		Cipher.getInstance(AES + WRAP + B + _256);
		Cipher.getInstance(AES + B + _128 + SLASH + CBC + SLASH + NO_PADDING);
		Cipher.getInstance(AES + B + _128 + SLASH + CFB + SLASH + NO_PADDING);
		Cipher.getInstance(AES + B + _128 + SLASH + ECB + SLASH + NO_PADDING);
		Cipher.getInstance(AES + B + _128 + SLASH + GCM + SLASH + NO_PADDING);
		Cipher.getInstance(AES + B + _128 + SLASH + OFB + SLASH + NO_PADDING);
		Cipher.getInstance(AES + B + _192 + SLASH + CBC + SLASH + NO_PADDING);
		Cipher.getInstance(AES + B + _192 + SLASH + CFB + SLASH + NO_PADDING);
		Cipher.getInstance(AES + B + _192 + SLASH + ECB + SLASH + NO_PADDING);
		Cipher.getInstance(AES + B + _192 + SLASH + GCM + SLASH + NO_PADDING);
		Cipher.getInstance(AES + B + _192 + SLASH + OFB + SLASH + NO_PADDING);
		Cipher.getInstance(AES + B + _256 + SLASH + CBC + SLASH + NO_PADDING);
		Cipher.getInstance(AES + B + _256 + SLASH + CFB + SLASH + NO_PADDING);
		Cipher.getInstance(AES + B + _256 + SLASH + ECB + SLASH + NO_PADDING);
		Cipher.getInstance(AES + B + _256 + SLASH + GCM + SLASH + NO_PADDING);
		Cipher.getInstance(AES + B + _256 + SLASH + OFB + SLASH + NO_PADDING);
		Cipher.getInstance(ARCFOUR);
		Cipher.getInstance(BLOWFISH);
		Cipher.getInstance(DES);
		Cipher.getInstance(DES + EDE);
		Cipher.getInstance(DES + EDE + WRAP);
		Cipher.getInstance(PBE + WITH + HMAC + SHA1 + AND + AES + B + _128);
		Cipher.getInstance(PBE + WITH + HMAC + SHA1 + AND + AES + B + _256);
		Cipher.getInstance(PBE + WITH + HMAC + SHA + _224 + AND + AES + B + _128);
		Cipher.getInstance(PBE + WITH + HMAC + SHA + _224 + AND + AES + B + _256);
		Cipher.getInstance(PBE + WITH + HMAC + SHA + _256 + AND + AES + B + _128);
		Cipher.getInstance(PBE + WITH + HMAC + SHA + _256 + AND + AES + B + _256);
		Cipher.getInstance(PBE + WITH + HMAC + SHA + _384 + AND + AES + B + _128);
		Cipher.getInstance(PBE + WITH + HMAC + SHA + _384 + AND + AES + B + _256);
		Cipher.getInstance(PBE + WITH + HMAC + SHA + _512 + AND + AES + B + _128);
		Cipher.getInstance(PBE + WITH + HMAC + SHA + _512 + AND + AES + B + _256);
		Cipher.getInstance(PBE + WITH + MD5 + AND + DES);
		Cipher.getInstance(PBE + WITH + MD5 + AND + TRIPLE + DES);
		Cipher.getInstance(PBE + WITH + SHA1 + AND + DES + EDE);
		Cipher.getInstance(PBE + WITH + SHA1 + AND + RC2 + B + _128);
		Cipher.getInstance(PBE + WITH + SHA1 + AND + RC2 + B + _40);
		Cipher.getInstance(PBE + WITH + SHA1 + AND + RC4 + B + _128);
		Cipher.getInstance(PBE + WITH + SHA1 + AND + RC4 + B + _40);
		Cipher.getInstance(RC2);
		Cipher.getInstance(RSA);
		Cipher.getInstance(RSA + SLASH + ECB + SLASH + PKCS1 + PADDING);

		Parameters paramss = null;
		Configuration.getInstance("JavaLoginConfig", paramss);

		KeyAgreement.getInstance(DIFFIE_HELLMAN);
		KeyAgreement.getInstance(ECDH);

		KeyFactory.getInstance(DSA);
		KeyFactory.getInstance(DIFFIE_HELLMAN);
		KeyFactory.getInstance(EC);
		KeyFactory.getInstance(RSA);

		KeyGenerator.getInstance(AES);
		KeyGenerator.getInstance(ARCFOUR);
		KeyGenerator.getInstance(BLOWFISH);
		KeyGenerator.getInstance(DES);
		KeyGenerator.getInstance(DES + EDE);
		KeyGenerator.getInstance(HMAC + MD5);
		KeyGenerator.getInstance(HMAC + SHA1);
		KeyGenerator.getInstance(HMAC + SHA + _224);
		KeyGenerator.getInstance(HMAC + SHA + _256);
		KeyGenerator.getInstance(HMAC + SHA + _384);
		KeyGenerator.getInstance(HMAC + SHA + _512);
		KeyGenerator.getInstance(RC2);
		KeyGenerator.getInstance("Sun" + "Tls12" + "Prf");
		KeyGenerator.getInstance("Sun" + "Tls" + "Key" + "Material");
		KeyGenerator.getInstance("Sun" + "Tls" + "Master" + "Secret");
		KeyGenerator.getInstance("Sun" + "Tls" + "Prf");
		KeyGenerator.getInstance("Sun" + "Tls" + "Rsa" + "Premaster" + "Secret");

		KeyInfoFactory.getInstance(DOM);

		KeyManagerFactory.getInstance("New" + "Sun" + "X509");
		KeyManagerFactory.getInstance("Sun" + "X509");

		KeyStore.getInstance("CaseExact" + "JKS");
		KeyStore.getInstance("DKS");
		KeyStore.getInstance("JCEKS");
		KeyStore.getInstance("JKS");
		KeyStore.getInstance("PKCS12");
		KeyStore.getInstance("Windows" + "-" + "MY");
		KeyStore.getInstance("Windows" + "-" + "ROOT");

		Mac.getInstance(HMAC + MD5);
		Mac.getInstance(HMAC + PBE + SHA1);
		Mac.getInstance(HMAC + SHA1);
		Mac.getInstance(HMAC + SHA + _224);
		Mac.getInstance(HMAC + SHA + _256);
		Mac.getInstance(HMAC + SHA + _384);
		Mac.getInstance(HMAC + SHA + _512);
		Mac.getInstance(PBE + WITH + HMAC + SHA1);
		Mac.getInstance(PBE + WITH + HMAC + SHA + _224);
		Mac.getInstance(PBE + WITH + HMAC + SHA + _256);
		Mac.getInstance(PBE + WITH + HMAC + SHA + _384);
		Mac.getInstance(PBE + WITH + HMAC + SHA + _512);
		Mac.getInstance("Ssl" + "Mac" + MD5);
		Mac.getInstance("Ssl" + "Mac" + SHA1);

		java.security.Policy.Parameters pparams = null;
		Policy.getInstance("JavaPolicy", pparams);

		SSLContext.getInstance("Default");
		SSLContext.getInstance("TLS");
		SSLContext.getInstance("TLS" + "v1");
		SSLContext.getInstance("TLS" + "v1.1");
		SSLContext.getInstance("TLS" + "v1.2");

		Sasl.getSaslClientFactories();
		Sasl.getSaslServerFactories();

		SecretKeyFactory.getInstance(DES);
		SecretKeyFactory.getInstance(DES + EDE);
		SecretKeyFactory.getInstance(PBE + WITH + HMAC + SHA1 + AND + AES + B + _128);
		SecretKeyFactory.getInstance(PBE + WITH + HMAC + SHA1 + AND + AES + B + _256);
		SecretKeyFactory.getInstance(PBE + WITH + HMAC + SHA + _224 + AND + AES + B + _128);
		SecretKeyFactory.getInstance(PBE + WITH + HMAC + SHA + _224 + AND + AES + B + _256);
		SecretKeyFactory.getInstance(PBE + WITH + HMAC + SHA + _256 + AND + AES + B + _128);
		SecretKeyFactory.getInstance(PBE + WITH + HMAC + SHA + _256 + AND + AES + B + _256);
		SecretKeyFactory.getInstance(PBE + WITH + HMAC + SHA + _384 + AND + AES + B + _128);
		SecretKeyFactory.getInstance(PBE + WITH + HMAC + SHA + _384 + AND + AES + B + _256);
		SecretKeyFactory.getInstance(PBE + WITH + HMAC + SHA + _512 + AND + AES + B + _128);
		SecretKeyFactory.getInstance(PBE + WITH + HMAC + SHA + _512 + AND + AES + B + _256);
		SecretKeyFactory.getInstance(PBE + WITH + MD5 + AND + DES);
		SecretKeyFactory.getInstance(PBE + WITH + MD5 + AND + TRIPLE + DES);
		SecretKeyFactory.getInstance(PBE + WITH + SHA1 + AND + DES + EDE);
		SecretKeyFactory.getInstance(PBE + WITH + SHA1 + AND + RC2 + B + _128);
		SecretKeyFactory.getInstance(PBE + WITH + SHA1 + AND + RC2 + B + _40);
		SecretKeyFactory.getInstance(PBE + WITH + SHA1 + AND + RC4 + B + _128);
		SecretKeyFactory.getInstance(PBE + WITH + SHA1 + AND + RC4 + B + _40);
		SecretKeyFactory.getInstance(PBKDF2 + WITH + HMAC + SHA1);
		SecretKeyFactory.getInstance(PBKDF2 + WITH + HMAC + SHA + _224);
		SecretKeyFactory.getInstance(PBKDF2 + WITH + HMAC + SHA + _256);
		SecretKeyFactory.getInstance(PBKDF2 + WITH + HMAC + SHA + _384);
		SecretKeyFactory.getInstance(PBKDF2 + WITH + HMAC + SHA + _512);

		SecureRandom.getInstance(SHA1 + "PRNG");
		SecureRandom.getInstance("Windows" + "-" + "PRNG");

		TrustManagerFactory.getInstance(PKIX);
		TrustManagerFactory.getInstance("Sun" + "X509");

		XMLSignatureFactory.getInstance(DOM);
	}

	@Test
	public void messageDigestFiddle() throws NoSuchAlgorithmException {

		byte[] input = new byte[] {};

		MessageDigest md2 = MessageDigest.getInstance(MD2);
		byte[] md2output = md2.digest(input);

		MessageDigest md5 = MessageDigest.getInstance(MD5);
		byte[] md5output = md5.digest(input);

		MessageDigest sha = MessageDigest.getInstance(SHA);
		byte[] shaoutput = sha.digest(input);

		MessageDigest sha224 = MessageDigest.getInstance(SHA + "-" + _224);
		byte[] sha224output = sha224.digest(input);

		MessageDigest sha256 = MessageDigest.getInstance(SHA + "-" + _256);
		byte[] sha256output = sha256.digest(input);

		MessageDigest sha384 = MessageDigest.getInstance(SHA + "-" + _384);
		byte[] sha384output = sha384.digest(input);

		MessageDigest sha512 = MessageDigest.getInstance(SHA + "-" + _512);
		byte[] sha512output = sha512.digest(input);

		System.out.println(md2output.length * 8);
		System.out.println(Common.bytesToString(md2output));

		System.out.println(md5output.length * 8);
		System.out.println(Common.bytesToString(md5output));

		System.out.println(shaoutput.length * 8);
		System.out.println(Common.bytesToString(shaoutput));

		System.out.println(sha224output.length * 8);
		System.out.println(Common.bytesToString(sha224output));

		System.out.println(sha256output.length * 8);
		System.out.println(Common.bytesToString(sha256output));

		System.out.println(sha384output.length * 8);
		System.out.println(Common.bytesToString(sha384output));

		System.out.println(sha512output.length * 8);
		System.out.println(Common.bytesToString(sha512output));
	}

	@Test
	public void fiddle1() throws NoSuchAlgorithmException {

		Signature md2rsa = Signature.getInstance(MD2 + WITH2 + RSA);
		Signature md5sha1rsa = Signature.getInstance(MD5 + AND2 + SHA1 + WITH2 + RSA);
		Signature md5rsa = Signature.getInstance(MD5 + WITH2 + RSA);
		Signature dsa = Signature.getInstance(NONE + WITH2 + DSA);
		Signature ecdsa = Signature.getInstance(NONE + WITH2 + ECDSA);
		Signature rsa = Signature.getInstance(NONE + WITH2 + RSA);
		Signature sha1dsa = Signature.getInstance(SHA1 + WITH2 + DSA);
		Signature sha1ecdsa = Signature.getInstance(SHA1 + WITH2 + ECDSA);
		Signature sha1rsa = Signature.getInstance(SHA1 + WITH2 + RSA);
		Signature sha224dsa = Signature.getInstance(SHA + _224 + WITH2 + DSA);
		Signature sha224ecdsa = Signature.getInstance(SHA + _224 + WITH2 + ECDSA);
		Signature sha224rsa = Signature.getInstance(SHA + _224 + WITH2 + RSA);
		Signature sha256dsa = Signature.getInstance(SHA + _256 + WITH2 + DSA);
		Signature sha256ecdsa = Signature.getInstance(SHA + _256 + WITH2 + ECDSA);
		Signature sha256rsa = Signature.getInstance(SHA + _256 + WITH2 + RSA);
		Signature sha384ecdsa = Signature.getInstance(SHA + _384 + WITH2 + ECDSA);
		Signature sha384rsa = Signature.getInstance(SHA + _384 + WITH2 + RSA);
		Signature sha512ecdsa = Signature.getInstance(SHA + _512 + WITH2 + ECDSA);
		Signature sha512rsa = Signature.getInstance(SHA + _512 + WITH2 + RSA);

	}

}
