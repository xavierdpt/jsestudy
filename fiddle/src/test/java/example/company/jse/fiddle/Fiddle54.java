package example.company.jse.fiddle;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.DSAPrivateKey;
import java.security.interfaces.DSAPublicKey;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.DSAPrivateKeySpec;
import java.security.spec.DSAPublicKeySpec;
import java.security.spec.ECField;
import java.security.spec.ECFieldFp;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;
import java.security.spec.ECPrivateKeySpec;
import java.security.spec.ECPublicKeySpec;
import java.security.spec.EllipticCurve;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.RSAMultiPrimePrivateCrtKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.DHPrivateKeySpec;
import javax.crypto.spec.DHPublicKeySpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.junit.Assert;
import org.junit.Test;

import xdptdr.common.Common;

public class Fiddle54 {

	@Test
	public void messageDigests() throws NoSuchAlgorithmException {

		Map<String, String> expectations = new HashMap<>();

		expectations.put("MD2", "8350E5A3E24C153DF2275C9F80692773");

		expectations.put("MD5", "D41D8CD98F00B204E9800998ECF8427E");

		expectations.put("SHA", "DA39A3EE5E6B4B0D3255BFEF95601890AFD80709");

		expectations.put("SHA-224", "D14A028C2A3A2BC9476102BB288234C415A2B01F828EA62AC5B3E42F");

		expectations.put("SHA-256", "E3B0C44298FC1C149AFBF4C8996FB92427AE41E4649B934CA495991B7852B855");

		expectations.put("SHA-384",
				"38B060A751AC96384CD9327EB1B1E36A21FDB71114BE07434C0CC7BF63F6E1DA274EDEBFE76F65FBD51AD2F14898B95B");

		expectations.put("SHA-512",
				"CF83E1357EEFB8BDF1542850D66D8007D620E4050B5715DC83F4A921D36CE9CE47D0D13C5D85F2B0FF8318D2877EEC2F63B931BD47417A81A538327AF927DA3E");

		byte[] empty = new byte[] {};

		for (Entry<String, String> e : expectations.entrySet()) {

			String algorithm = e.getKey();
			String expected = e.getValue();

			MessageDigest md = MessageDigest.getInstance(algorithm);
			Assert.assertArrayEquals(Common.bytes(expected), md.digest(empty));
			Assert.assertArrayEquals(Common.bytes(expected), md.digest(empty));

		}

	}

	@Test
	public void keyFactories() throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchProviderException {

		Class<?>[] classes = new Class<?>[] { DESedeKeySpec.class, DESKeySpec.class, EncodedKeySpec.class,
				PBEKeySpec.class, RSAMultiPrimePrivateCrtKeySpec.class, SecretKeySpec.class };

		/*-
		com.sun.crypto.provider.DHPrivateKey
		com.sun.crypto.provider.DHPublicKey
		java.io.Serializable
		java.lang.Object
		
		java.security.interfaces.RSAPrivateCrtKey
		
		java.security.Key
		javax.crypto.interfaces.DHKey
		javax.crypto.interfaces.DHPrivateKey
		javax.crypto.interfaces.DHPublicKey
		javax.security.auth.Destroyable
		sun.security.ec.ECPrivateKeyImpl
		sun.security.ec.ECPublicKeyImpl
		sun.security.mscapi.Key
		sun.security.mscapi.RSAPrivateKey
		sun.security.mscapi.RSAPublicKey
		sun.security.pkcs.PKCS8Key
		sun.security.provider.DSAPrivateKey
		sun.security.provider.DSAPublicKey
		sun.security.provider.DSAPublicKeyImpl
		sun.security.rsa.RSAPrivateCrtKeyImpl
		sun.security.rsa.RSAPublicKeyImpl
		sun.security.util.Length
		sun.security.x509.X509Key
		
		 */

		/*-
		KeyPairGenerator
		- DSA: SUN, 
		- DiffieHellman: SunJCE, 
		- EC: SunEC, 
		- RSA: SunRsaSign, SunJSSE, SunMSCAPI,
		 */

		KeyPairGenerator dsakpg = KeyPairGenerator.getInstance("DSA");
		KeyPair dsakp = dsakpg.generateKeyPair();
		DSAPrivateKey dsapr = (DSAPrivateKey) dsakp.getPrivate();
		DSAPublicKey dsapu = (DSAPublicKey) dsakp.getPublic();

		KeyPairGenerator dhkpg = KeyPairGenerator.getInstance("DiffieHellman");
		KeyPair dhkp = dhkpg.generateKeyPair();

		KeyPairGenerator eckpg = KeyPairGenerator.getInstance("EC");
		KeyPair eckp = eckpg.generateKeyPair();
		ECPrivateKey ecpr = (ECPrivateKey) eckp.getPrivate();
		ECPublicKey ecpu = (ECPublicKey) eckp.getPublic();

		KeyPairGenerator rsakpg = KeyPairGenerator.getInstance("RSA", "SunRsaSign");
		KeyPair rsakp = rsakpg.generateKeyPair();
		RSAPrivateKey rsapr = (RSAPrivateKey) rsakp.getPrivate();
		RSAPublicKey rsapu = (RSAPublicKey) rsakp.getPublic();

		KeyPairGenerator rsajssekpg = KeyPairGenerator.getInstance("RSA", "SunJSSE");
		KeyPair rsajssekp = rsajssekpg.generateKeyPair();
		RSAPrivateKey rsajssepr = (RSAPrivateKey) rsajssekp.getPrivate();
		RSAPublicKey rsajssepu = (RSAPublicKey) rsajssekp.getPublic();

		KeyPairGenerator rsamskpg = KeyPairGenerator.getInstance("RSA", "SunMSCAPI");
		KeyPair rsamskp = rsamskpg.generateKeyPair();
		RSAPrivateKey rsamspr = (RSAPrivateKey) rsamskp.getPrivate();
		RSAPublicKey rsamspu = (RSAPublicKey) rsamskp.getPublic();

		for (KeyPair yata : new KeyPair[] { dsakp, dhkp, eckp, rsakp, rsajssekp, rsamskp }) {
			detail(yata.getPublic().getClass());
			detail(yata.getPrivate().getClass());
		}

		if (System.currentTimeMillis() > 0)
			return;

		KeyFactory dsa = KeyFactory.getInstance("DSA");
		try {
			BigInteger y = null;
			BigInteger p = null;
			BigInteger q = null;
			BigInteger g = null;
			KeySpec dsaPublicKeySpec = new DSAPublicKeySpec(y, p, q, g);
			PublicKey dsaPublic = dsa.generatePublic(dsaPublicKeySpec);
		} catch (Exception ex) {
			youpi(ex);
		}
		try {
			BigInteger x = null;
			BigInteger p = null;
			BigInteger q = null;
			BigInteger g = null;
			KeySpec dsaPrivateKeySpec = new DSAPrivateKeySpec(x, p, q, g);
			PrivateKey dsaPrivate = dsa.generatePrivate(dsaPrivateKeySpec);
		} catch (Exception ex) {
			youpi(ex);
		}

		KeyFactory dh = KeyFactory.getInstance("DiffieHellman");
		try {
			BigInteger y = null;
			BigInteger p = null;
			BigInteger g = null;
			KeySpec dhPublicKeySpec = new DHPublicKeySpec(y, p, g);
			PublicKey dhPublic = dh.generatePublic(dhPublicKeySpec);
		} catch (Exception ex) {
			youpi(ex);
		}
		try {
			BigInteger x = null;
			BigInteger p = null;
			BigInteger g = null;
			KeySpec dhPrivateKeySpec = new DHPrivateKeySpec(x, p, g);
			PrivateKey dhPrivate = dh.generatePrivate(dhPrivateKeySpec);
		} catch (Exception ex) {
			youpi(ex);
		}
		{
			BigInteger x = null;
			BigInteger y = null;
			BigInteger n = null;
			BigInteger a = null;
			BigInteger b = null;
			byte[] seed = new byte[] {};
			ECPoint g = new ECPoint(x, y);
			BigInteger p = null;
			ECField field = new ECFieldFp(p);
			int h = 0;
			EllipticCurve curve = new EllipticCurve(field, a, b, seed);
			ECParameterSpec params = new ECParameterSpec(curve, g, n, h);

			KeyFactory ec = KeyFactory.getInstance("EC");
			try {
				ECPoint w = new ECPoint(x, y);
				KeySpec ecPublicKeySpec = new ECPublicKeySpec(w, params); // also X509EncodedKeySpec
				PublicKey ecPublic = ec.generatePublic(ecPublicKeySpec);
			} catch (Exception ex) {
				youpi(ex);
			}
			try {
				BigInteger s = null;
				KeySpec ecPrivateKeySpec = new ECPrivateKeySpec(s, params); // also PKCS8EncodedKeySpec
				PrivateKey ecPrivate = ec.generatePrivate(ecPrivateKeySpec);
			} catch (Exception ex) {
				youpi(ex);
			}
		}

		KeyFactory rsa = KeyFactory.getInstance("RSA");
		try {
			BigInteger modulus = null;
			BigInteger publicExponent = null;
			KeySpec rsaPublicKeySpec = new RSAPublicKeySpec(modulus, publicExponent); // also X509EncodedKeySpec
			PublicKey rsaPublic = rsa.generatePublic(rsaPublicKeySpec);
		} catch (Exception ex) {
			youpi(ex);
		}
		try {
			BigInteger modulus = null;
			BigInteger privateExponent = null;
			KeySpec rsaPrivateKeySpec = new RSAPrivateKeySpec(modulus, privateExponent); // also PKCS8EncodedKeySpec and
																							// RSAPrivateCrtKeySpec
			PrivateKey rsaPrivate = rsa.generatePrivate(rsaPrivateKeySpec);
		} catch (Exception ex) {
			youpi(ex);
		}

	}

	private void detail(Class<?> o) {
		System.out.println(o.getName());
		if (o.getSuperclass() != null) {
			detail(o.getSuperclass());
		}
		for (Class<?> i : o.getInterfaces()) {
			detail(i);
		}
	}

	private void youpi(Exception ex) {
		System.out.println(ex.getClass().getName() + " : " + ex.getMessage());

	}

	/*-
	AlgorithmParameterGenerator
	 - DSA: SUN, 
	 - DiffieHellman: SunJCE, 
	AlgorithmParameters
	 - AES: SunJCE, 
	 - Blowfish: SunJCE, 
	 - DES: SunJCE, 
	 - DESede: SunJCE, 
	 - DSA: SUN, 
	 - DiffieHellman: SunJCE, 
	 - EC: SunEC, 
	 - GCM: SunJCE, 
	 - OAEP: SunJCE, 
	 - PBE: SunJCE, 
	 - PBES2: SunJCE, 
	 - PBEWithHmacSHA1AndAES_128: SunJCE, 
	 - PBEWithHmacSHA1AndAES_256: SunJCE, 
	 - PBEWithHmacSHA224AndAES_128: SunJCE, 
	 - PBEWithHmacSHA224AndAES_256: SunJCE, 
	 - PBEWithHmacSHA256AndAES_128: SunJCE, 
	 - PBEWithHmacSHA256AndAES_256: SunJCE, 
	 - PBEWithHmacSHA384AndAES_128: SunJCE, 
	 - PBEWithHmacSHA384AndAES_256: SunJCE, 
	 - PBEWithHmacSHA512AndAES_128: SunJCE, 
	 - PBEWithHmacSHA512AndAES_256: SunJCE, 
	 - PBEWithMD5AndDES: SunJCE, 
	 - PBEWithMD5AndTripleDES: SunJCE, 
	 - PBEWithSHA1AndDESede: SunJCE, 
	 - PBEWithSHA1AndRC2_128: SunJCE, 
	 - PBEWithSHA1AndRC2_40: SunJCE, 
	 - PBEWithSHA1AndRC4_128: SunJCE, 
	 - PBEWithSHA1AndRC4_40: SunJCE, 
	 - RC2: SunJCE, 
	CertPathBuilder
	 - PKIX: SUN, 
	CertPathValidator
	 - PKIX: SUN, 
	CertStore
	 - Collection: SUN, 
	 - LDAP: SUN, 
	 - com.sun.security.IndexedCollection: SUN, 
	CertificateFactory
	 - X.509: SUN, 
	Cipher
	 - AES: SunJCE, 
	 - AESWrap: SunJCE, 
	 - AESWrap_128: SunJCE, 
	 - AESWrap_192: SunJCE, 
	 - AESWrap_256: SunJCE, 
	 - AES_128/CBC/NoPadding: SunJCE, 
	 - AES_128/CFB/NoPadding: SunJCE, 
	 - AES_128/ECB/NoPadding: SunJCE, 
	 - AES_128/GCM/NoPadding: SunJCE, 
	 - AES_128/OFB/NoPadding: SunJCE, 
	 - AES_192/CBC/NoPadding: SunJCE, 
	 - AES_192/CFB/NoPadding: SunJCE, 
	 - AES_192/ECB/NoPadding: SunJCE, 
	 - AES_192/GCM/NoPadding: SunJCE, 
	 - AES_192/OFB/NoPadding: SunJCE, 
	 - AES_256/CBC/NoPadding: SunJCE, 
	 - AES_256/CFB/NoPadding: SunJCE, 
	 - AES_256/ECB/NoPadding: SunJCE, 
	 - AES_256/GCM/NoPadding: SunJCE, 
	 - AES_256/OFB/NoPadding: SunJCE, 
	 - ARCFOUR: SunJCE, 
	 - Blowfish: SunJCE, 
	 - DES: SunJCE, 
	 - DESede: SunJCE, 
	 - DESedeWrap: SunJCE, 
	 - PBEWithHmacSHA1AndAES_128: SunJCE, 
	 - PBEWithHmacSHA1AndAES_256: SunJCE, 
	 - PBEWithHmacSHA224AndAES_128: SunJCE, 
	 - PBEWithHmacSHA224AndAES_256: SunJCE, 
	 - PBEWithHmacSHA256AndAES_128: SunJCE, 
	 - PBEWithHmacSHA256AndAES_256: SunJCE, 
	 - PBEWithHmacSHA384AndAES_128: SunJCE, 
	 - PBEWithHmacSHA384AndAES_256: SunJCE, 
	 - PBEWithHmacSHA512AndAES_128: SunJCE, 
	 - PBEWithHmacSHA512AndAES_256: SunJCE, 
	 - PBEWithMD5AndDES: SunJCE, 
	 - PBEWithMD5AndTripleDES: SunJCE, 
	 - PBEWithSHA1AndDESede: SunJCE, 
	 - PBEWithSHA1AndRC2_128: SunJCE, 
	 - PBEWithSHA1AndRC2_40: SunJCE, 
	 - PBEWithSHA1AndRC4_128: SunJCE, 
	 - PBEWithSHA1AndRC4_40: SunJCE, 
	 - RC2: SunJCE, 
	 - RSA: SunJCE, SunMSCAPI, 
	 - RSA/ECB/PKCS1Padding: SunMSCAPI, 
	Configuration
	 - JavaLoginConfig: SUN, 
	GssApiMechanism
	 - 1.2.840.113554.1.2.2: SunJGSS, 
	 - 1.3.6.1.5.5.2: SunJGSS, 
	KeyAgreement
	 - DiffieHellman: SunJCE, 
	 - ECDH: SunEC, 
	
	KeyGenerator
	 - AES: SunJCE, 
	 - ARCFOUR: SunJCE, 
	 - Blowfish: SunJCE, 
	 - DES: SunJCE, 
	 - DESede: SunJCE, 
	 - HmacMD5: SunJCE, 
	 - HmacSHA1: SunJCE, 
	 - HmacSHA224: SunJCE, 
	 - HmacSHA256: SunJCE, 
	 - HmacSHA384: SunJCE, 
	 - HmacSHA512: SunJCE, 
	 - RC2: SunJCE, 
	 - SunTls12Prf: SunJCE, 
	 - SunTlsKeyMaterial: SunJCE, 
	 - SunTlsMasterSecret: SunJCE, 
	 - SunTlsPrf: SunJCE, 
	 - SunTlsRsaPremasterSecret: SunJCE, 
	KeyInfoFactory
	 - DOM: XMLDSig, 
	KeyManagerFactory
	 - NewSunX509: SunJSSE, 
	 - SunX509: SunJSSE, 
	 
	KeyStore
	 - CaseExactJKS: SUN, 
	 - DKS: SUN, 
	 - JCEKS: SunJCE, 
	 - JKS: SUN, 
	 - PKCS12: SunJSSE, 
	 - Windows-MY: SunMSCAPI, 
	 - Windows-ROOT: SunMSCAPI, 
	Mac
	 - HmacMD5: SunJCE, 
	 - HmacPBESHA1: SunJCE, 
	 - HmacSHA1: SunJCE, 
	 - HmacSHA224: SunJCE, 
	 - HmacSHA256: SunJCE, 
	 - HmacSHA384: SunJCE, 
	 - HmacSHA512: SunJCE, 
	 - PBEWithHmacSHA1: SunJCE, 
	 - PBEWithHmacSHA224: SunJCE, 
	 - PBEWithHmacSHA256: SunJCE, 
	 - PBEWithHmacSHA384: SunJCE, 
	 - PBEWithHmacSHA512: SunJCE, 
	 - SslMacMD5: SunJCE, 
	 - SslMacSHA1: SunJCE, 
	 
	Policy
	 - JavaPolicy: SUN, 
	SSLContext
	 - Default: SunJSSE, 
	 - TLS: SunJSSE, 
	 - TLSv1: SunJSSE, 
	 - TLSv1.1: SunJSSE, 
	 - TLSv1.2: SunJSSE, 
	SaslClientFactory
	 - CRAM-MD5: SunSASL, 
	 - DIGEST-MD5: SunSASL, 
	 - EXTERNAL: SunSASL, 
	 - GSSAPI: SunSASL, 
	 - NTLM: SunSASL, 
	 - PLAIN: SunSASL, 
	SaslServerFactory
	 - CRAM-MD5: SunSASL, 
	 - DIGEST-MD5: SunSASL, 
	 - GSSAPI: SunSASL, 
	 - NTLM: SunSASL, 
	SecretKeyFactory
	 - DES: SunJCE, 
	 - DESede: SunJCE, 
	 - PBEWithHmacSHA1AndAES_128: SunJCE, 
	 - PBEWithHmacSHA1AndAES_256: SunJCE, 
	 - PBEWithHmacSHA224AndAES_128: SunJCE, 
	 - PBEWithHmacSHA224AndAES_256: SunJCE, 
	 - PBEWithHmacSHA256AndAES_128: SunJCE, 
	 - PBEWithHmacSHA256AndAES_256: SunJCE, 
	 - PBEWithHmacSHA384AndAES_128: SunJCE, 
	 - PBEWithHmacSHA384AndAES_256: SunJCE, 
	 - PBEWithHmacSHA512AndAES_128: SunJCE, 
	 - PBEWithHmacSHA512AndAES_256: SunJCE, 
	 - PBEWithMD5AndDES: SunJCE, 
	 - PBEWithMD5AndTripleDES: SunJCE, 
	 - PBEWithSHA1AndDESede: SunJCE, 
	 - PBEWithSHA1AndRC2_128: SunJCE, 
	 - PBEWithSHA1AndRC2_40: SunJCE, 
	 - PBEWithSHA1AndRC4_128: SunJCE, 
	 - PBEWithSHA1AndRC4_40: SunJCE, 
	 - PBKDF2WithHmacSHA1: SunJCE, 
	 - PBKDF2WithHmacSHA224: SunJCE, 
	 - PBKDF2WithHmacSHA256: SunJCE, 
	 - PBKDF2WithHmacSHA384: SunJCE, 
	 - PBKDF2WithHmacSHA512: SunJCE, 
	SecureRandom
	 - SHA1PRNG: SUN, 
	 - Windows-PRNG: SunMSCAPI, 
	Signature
	 - MD2withRSA: SunRsaSign, SunJSSE, SunMSCAPI, 
	 - MD5andSHA1withRSA: SunJSSE, 
	 - MD5withRSA: SunRsaSign, SunJSSE, SunMSCAPI, 
	 - NONEwithDSA: SUN, 
	 - NONEwithECDSA: SunEC, 
	 - NONEwithRSA: SunMSCAPI, 
	 - SHA1withDSA: SUN, 
	 - SHA1withECDSA: SunEC, 
	 - SHA1withRSA: SunRsaSign, SunJSSE, SunMSCAPI, 
	 - SHA224withDSA: SUN, 
	 - SHA224withECDSA: SunEC, 
	 - SHA224withRSA: SunRsaSign, 
	 - SHA256withDSA: SUN, 
	 - SHA256withECDSA: SunEC, 
	 - SHA256withRSA: SunRsaSign, SunMSCAPI, 
	 - SHA384withECDSA: SunEC, 
	 - SHA384withRSA: SunRsaSign, SunMSCAPI, 
	 - SHA512withECDSA: SunEC, 
	 - SHA512withRSA: SunRsaSign, SunMSCAPI, 
	TerminalFactory
	 - PC/SC: SunPCSC, 
	TransformService
	 - http://www.w3.org/2000/09/xmldsig#base64: XMLDSig, 
	 - http://www.w3.org/2000/09/xmldsig#enveloped-signature: XMLDSig, 
	 - http://www.w3.org/2001/10/xml-exc-c14n#: XMLDSig, 
	 - http://www.w3.org/2001/10/xml-exc-c14n#WithComments: XMLDSig, 
	 - http://www.w3.org/2002/06/xmldsig-filter2: XMLDSig, 
	 - http://www.w3.org/2006/12/xml-c14n11: XMLDSig, 
	 - http://www.w3.org/2006/12/xml-c14n11#WithComments: XMLDSig, 
	 - http://www.w3.org/TR/1999/REC-xpath-19991116: XMLDSig, 
	 - http://www.w3.org/TR/1999/REC-xslt-19991116: XMLDSig, 
	 - http://www.w3.org/TR/2001/REC-xml-c14n-20010315: XMLDSig, 
	 - http://www.w3.org/TR/2001/REC-xml-c14n-20010315#WithComments: XMLDSig, 
	TrustManagerFactory
	 - PKIX: SunJSSE, 
	 - SunX509: SunJSSE, 
	XMLSignatureFactory
	 - DOM: XMLDSig, 
	
	 */
}
