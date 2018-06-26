package example.company.acme.jw;

import java.security.KeyPair;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.util.HashMap;
import java.util.Map;

import example.company.tox.common.Common;

public class KeyPairWithJWK {

	private KeyPair keyPair;
	private Map<String, String> publicJwk;
	private Map<String, String> privateJwk;

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

		Map<String, String> publicJwk = getPublicJWK((ECPublicKey) keyPair.getPublic());
		Map<String, String> privateJwk = getPrivateJWK((ECPrivateKey) keyPair.getPrivate());

		return new KeyPairWithJWK(keyPair, publicJwk, privateJwk);
	}

	public static KeyPairWithJWK fromJWK(Map<String, String> jwk) {
		return new KeyPairWithJWK(jwk);
	}

	private static Map<String, String> getPublicJWK(ECPublicKey publicKey) {

		String x64 = JWBase64.encode(Common.bigIntegerToBytes(publicKey.getW().getAffineX()));
		String y64 = JWBase64.encode(Common.bigIntegerToBytes(publicKey.getW().getAffineY()));

		Map<String, String> jwk = new HashMap<>();
		jwk.put("kty", "EC");
		jwk.put("crv", "P-256");
		jwk.put("x", x64);
		jwk.put("y", y64);
		return jwk;
	}

	private static Map<String, String> getPrivateJWK(ECPrivateKey privateKey) {

		String d64 = JWBase64.encode(Common.bigIntegerToBytes(privateKey.getS()));

		Map<String, String> jwk = new HashMap<>();
		jwk.put("kty", "EC");
		jwk.put("crv", "P-256");
		jwk.put("d", d64);
		return jwk;
	}

	public Map<String,String> getFullJWK() {
		Map<String,String> map = new HashMap<>();
		map.putAll(publicJwk);
		map.putAll(privateJwk);
		return map;
	}

}
