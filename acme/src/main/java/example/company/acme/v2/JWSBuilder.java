package example.company.acme.v2;

import java.math.BigInteger;
import java.security.interfaces.ECPrivateKey;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import example.company.acme.AcmeSession;
import example.company.acme.crypto.ECCurves;
import example.company.acme.crypto.ECSignature;
import example.company.acme.crypto.ECSigner;
import example.company.acme.jw.JWA;
import example.company.acme.jw.JWBase64;
import example.company.tox.common.Common;

public class JWSBuilder {

	public static Map<String, Object> build(AcmeSession session, Object payload, String url) throws Exception {

		String nonce = session.getNonce();
		ObjectMapper om = session.getOm();
		ECPrivateKey privateKey = (ECPrivateKey) session.getKeyPairWithJWK().getKeyPair().getPrivate();

		Map<String, Object> protekted = new HashMap<>();
		protekted.put("alg", JWA.ES256);
		if (session.getAccount() == null) {
			protekted.put("jwk", session.getKeyPairWithJWK().getPublicJwk());
		} else {
			String kid = session.getAccount().getUrl();
			protekted.put("kid", kid);
		}
		protekted.put("nonce", nonce);
		protekted.put("url", url);
		String protected64 = JWBase64.encode(om.writeValueAsBytes(protekted));

		String payload64 = JWBase64.encode(om.writeValueAsBytes(payload));

		byte[] tbs = (protected64 + "." + payload64).getBytes();
		BigInteger s = privateKey.getS();
		ECSignature signature = ECSigner.sign(tbs, ECCurves.NIST_P_256, s);

		byte[] rBytes = Common.bigIntegerToBytes(signature.getR());
		byte[] sBytes = Common.bigIntegerToBytes(signature.getS());
		String signature64 = JWBase64.encode(Common.concatenate(rBytes, sBytes));

		Map<String, Object> jws = new HashMap<>();
		jws.put("protected", protected64);
		jws.put("payload", payload64);
		jws.put("signature", signature64);
		return jws;
	}

}
