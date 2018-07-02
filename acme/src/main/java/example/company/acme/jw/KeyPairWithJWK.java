package example.company.acme.jw;

import java.math.BigInteger;
import java.security.AlgorithmParameters;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;
import java.security.spec.ECPrivateKeySpec;
import java.security.spec.ECPublicKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.HashMap;
import java.util.Map;

import example.company.acme.crypto.ECCurves;
import example.company.acme.v2.AcmeException;
import example.company.tox.common.Common;

public class KeyPairWithJWK {

	private KeyPair keyPair;
	private Map<String, String> publicJwk = new HashMap<>();
	private Map<String, String> privateJwk = new HashMap<>();

	private KeyPairWithJWK(KeyPair keyPair, Map<String, String> publicJwk, Map<String, String> privateJwk) {
		this.keyPair = keyPair;
		this.publicJwk = publicJwk;
		this.privateJwk = privateJwk;
	}

	public KeyPairWithJWK(Map<String, String> jwk) {
		this.publicJwk = jwk;
	}

	public KeyPair getKeyPair() {
		return keyPair;
	}

	public Map<String, String> getPublicJwk() {
		return publicJwk;
	}

	public Map<String, String> getPrivateJwk() {
		return privateJwk;
	}

	public static KeyPairWithJWK fromKeyPair(KeyPair keyPair) {

		Map<String, String> publicJwk = getPublicJWK(keyPair.getPublic());
		Map<String, String> privateJwk = getPrivateJWK(keyPair.getPrivate());

		return new KeyPairWithJWK(keyPair, publicJwk, privateJwk);
	}

	public static KeyPairWithJWK fromJWK(Map<String, String> jwk) throws AcmeException {

		Map<String, String> publicProps = new HashMap<>();
		Map<String, String> privateProps = new HashMap<>();

		jwk.forEach((p, v) -> {
			if ("x".equals(p) || "y".equals(p)) {
				publicProps.put(p, v);
			} else if ("d".equals(p)) {
				privateProps.put(p, v);
			} else {
				publicProps.put(p, v);
				privateProps.put(p, v);
			}
		});

		return new KeyPairWithJWK(createKeyPair(jwk), publicProps, privateProps);
	}

	private static Map<String, String> getPublicJWK(PublicKey publicKey) {

		Map<String, String> jwk = new HashMap<>();

		if (publicKey instanceof ECPublicKey) {
			ECPublicKey key = (ECPublicKey) publicKey;
			String x64 = JWBase64.encode(Common.bigIntegerToBytes(key.getW().getAffineX()));
			String y64 = JWBase64.encode(Common.bigIntegerToBytes(key.getW().getAffineY()));

			jwk.put("kty", "EC");
			jwk.put("crv", "P-256");
			jwk.put("x", x64);
			jwk.put("y", y64);
		} else if (publicKey instanceof RSAPublicKey) {

			RSAPublicKey key = (RSAPublicKey) publicKey;

			String m64 = JWBase64.encode(Common.bigIntegerToBytes(key.getModulus()));
			String e64 = JWBase64.encode(Common.bigIntegerToBytes(key.getPublicExponent()));

			jwk.put("kty", "RSA");
			jwk.put("n", m64);
			jwk.put("e", e64);
		}
		return jwk;
	}

	private static Map<String, String> getPrivateJWK(PrivateKey privateKey) {

		Map<String, String> jwk = new HashMap<>();

		if (privateKey instanceof ECPrivateKey) {

			ECPrivateKey key = (ECPrivateKey) privateKey;

			String d64 = JWBase64.encode(Common.bigIntegerToBytes(key.getS()));

			jwk.put("kty", "EC");
			jwk.put("crv", "P-256");
			jwk.put("d", d64);
		} else if (privateKey instanceof RSAPrivateKey) {
			
			RSAPrivateKey key = (RSAPrivateKey) privateKey;

			String m64 = JWBase64.encode(Common.bigIntegerToBytes(key.getModulus()));
			String pe64 = JWBase64.encode(Common.bigIntegerToBytes(key.getPrivateExponent()));

			jwk.put("kty", "RSA");
			jwk.put("d", pe64);
			jwk.put("n", m64);
		}
		return jwk;
	}

	public Map<String, String> getFullJWK() {
		Map<String, String> map = new HashMap<>();
		map.putAll(publicJwk);
		map.putAll(privateJwk);
		return map;
	}

	private static KeyPair createKeyPair(Map<String, String> jwk) throws AcmeException {
		try {

			if ("EC".equals(jwk.get("kty"))) {

				BigInteger d = Common.bigInteger(JWBase64.decode(jwk.get("d")));
				BigInteger x = Common.bigInteger(JWBase64.decode(jwk.get("x")));
				BigInteger y = Common.bigInteger(JWBase64.decode(jwk.get("y")));

				AlgorithmParameters parametersFactory = AlgorithmParameters.getInstance("EC");
				parametersFactory.init(new ECGenParameterSpec(ECCurves.NIST_P_256));
				ECParameterSpec parameters = parametersFactory.getParameterSpec(ECParameterSpec.class);

				KeyFactory keyFactory = KeyFactory.getInstance("EC");
				PrivateKey generatePrivate = keyFactory.generatePrivate(new ECPrivateKeySpec(d, parameters));
				PublicKey generatePublic = keyFactory
						.generatePublic(new ECPublicKeySpec(new ECPoint(x, y), parameters));

				return new KeyPair(generatePublic, generatePrivate);
			} else if ("RSA".equals(jwk.get("kty"))) {
				BigInteger modulus = Common.bigInteger(JWBase64.decode(jwk.get("n")));
				BigInteger privateExponent = Common.bigInteger(JWBase64.decode(jwk.get("d")));
				BigInteger publicExponent = Common.bigInteger(JWBase64.decode(jwk.get("e")));

				KeyFactory keyFactory = KeyFactory.getInstance("RSA");
				PrivateKey generatePrivate = keyFactory
						.generatePrivate(new RSAPrivateKeySpec(modulus, privateExponent));
				PublicKey generatePublic = keyFactory.generatePublic(new RSAPublicKeySpec(modulus, publicExponent));

				return new KeyPair(generatePublic, generatePrivate);
			} else {
				throw new IllegalArgumentException(
						"Invalid kty : " + (jwk.containsKey("kty") ? jwk.get("kty") : "null"));
			}

		} catch (Exception ex) {
			throw new AcmeException(ex);
		}
	}
}
