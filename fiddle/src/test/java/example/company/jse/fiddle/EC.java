package example.company.jse.fiddle;

import java.math.BigInteger;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;
import java.security.spec.ECPrivateKeySpec;
import java.security.spec.ECPublicKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;

public class EC {

	public static final String NIST_P_256 = "NIST P-256";
	public static final String SHA256WITH_ECDSA = "SHA256withECDSA";

	private static ECParameterSpec getECParams(String name)
			throws NoSuchAlgorithmException, InvalidParameterSpecException {
		AlgorithmParameters ap = AlgorithmParameters.getInstance("EC");
		ap.init(new ECGenParameterSpec(name));
		return ap.getParameterSpec(ECParameterSpec.class);
	}

	public static PublicKey getPublicKey(KeyFactory keyFactory, ECParameterSpec params, BigInteger x, BigInteger y)
			throws InvalidKeySpecException {
		ECPublicKeySpec key = new ECPublicKeySpec(new ECPoint(x, y), params);
		return keyFactory.generatePublic(key);

	}

	public static PrivateKey getPrivateKey(KeyFactory keyFactory, ECParameterSpec params, BigInteger s)
			throws InvalidKeySpecException {
		ECPrivateKeySpec key = new ECPrivateKeySpec(s, params);
		return keyFactory.generatePrivate(key);
	}

	public static KeyPair getKeyPair(String name, BigInteger s, BigInteger x, BigInteger y)
			throws NoSuchAlgorithmException, InvalidParameterSpecException, InvalidAlgorithmParameterException,
			InvalidKeySpecException {

		ECParameterSpec params = getECParams(name);
		KeyFactory keyFactory = KeyFactory.getInstance("EC");

		PublicKey publicKey = getPublicKey(keyFactory, params, x, y);
		PrivateKey privateKey = getPrivateKey(keyFactory, params, s);
		return new KeyPair(publicKey, privateKey);

	}

	public static KeyPair genKeyPair(String name)
			throws NoSuchAlgorithmException, InvalidParameterSpecException, InvalidAlgorithmParameterException {

		ECParameterSpec params = getECParams(name);

		KeyPairGenerator kpg = KeyPairGenerator.getInstance("EC");
		kpg.initialize(params);
		return kpg.generateKeyPair();

	}

	public static byte[] sign(PrivateKey privateKey, byte[] tbs, String algorithm)
			throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
		Signature signature = Signature.getInstance(algorithm);
		signature.initSign(privateKey);
		signature.update(tbs);
		return signature.sign();
	}

}
