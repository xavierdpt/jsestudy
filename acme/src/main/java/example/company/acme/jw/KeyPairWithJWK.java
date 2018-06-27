package example.company.acme.jw;

import java.math.BigInteger;
import java.security.AlgorithmParameters;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;
import java.security.spec.ECPrivateKeySpec;
import java.security.spec.ECPublicKeySpec;
import java.util.HashMap;
import java.util.Map;

import example.company.acme.crypto.ECCurves;
import example.company.acme.v2.AcmeException;
import example.company.tox.common.Common;

public class KeyPairWithJWK {

	private KeyPair keyPair;
	private JWK publicJwk = new JWK();
	private JWK privateJwk = new JWK();

	private KeyPairWithJWK(KeyPair keyPair, Map<String, String> publicJwk, Map<String, String> privateJwk) {
		this.keyPair = keyPair;
		this.publicJwk = new JWK(publicJwk);
		this.privateJwk = new JWK(privateJwk);
	}

	public KeyPairWithJWK(Map<String, String> jwk) {
		this.publicJwk = new JWK(jwk);
	}

	public KeyPair getKeyPair() {
		return keyPair;
	}

	public JWK getPublicJwk() {
		return publicJwk;
	}

	public JWK getPrivateJwk() {
		return privateJwk;
	}

	public static KeyPairWithJWK fromKeyPair(KeyPair keyPair) {

		Map<String, String> publicJwk = getPublicJWK((ECPublicKey) keyPair.getPublic());
		Map<String, String> privateJwk = getPrivateJWK((ECPrivateKey) keyPair.getPrivate());

		return new KeyPairWithJWK(keyPair, publicJwk, privateJwk);
	}

	public static KeyPairWithJWK fromJWK(JWK jwk) throws AcmeException {

		Map<String, String> publicProps = new HashMap<>();
		Map<String, String> privateProps = new HashMap<>();

		jwk.getJwk().forEach((p, v) -> {
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

	public JWK getFullJWK() {
		Map<String, String> map = new HashMap<>();
		map.putAll(publicJwk.getJwk());
		map.putAll(privateJwk.getJwk());
		return new JWK(map);
	}

	private static KeyPair createKeyPair(JWK jwk) throws AcmeException {
		try {

			BigInteger d = Common.bigInteger(JWBase64.decode(jwk.getJwk().get("d")));
			BigInteger x = Common.bigInteger(JWBase64.decode(jwk.getJwk().get("x")));
			BigInteger y = Common.bigInteger(JWBase64.decode(jwk.getJwk().get("y")));
			
			AlgorithmParameters parametersFactory = AlgorithmParameters.getInstance("EC");
			parametersFactory.init(new ECGenParameterSpec(ECCurves.NIST_P_256));
			ECParameterSpec parameters = parametersFactory.getParameterSpec(ECParameterSpec.class);

			KeyFactory keyFactory = KeyFactory.getInstance("EC");
			PrivateKey generatePrivate = keyFactory.generatePrivate(new ECPrivateKeySpec(d, parameters));
			PublicKey generatePublic = keyFactory.generatePublic(new ECPublicKeySpec(new ECPoint(x, y), parameters));

			return new KeyPair(generatePublic, generatePrivate);

		} catch (Exception ex) {
			throw new AcmeException(ex);
		}
	}
}
