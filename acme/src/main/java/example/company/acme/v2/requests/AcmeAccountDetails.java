package example.company.acme.v2.requests;

import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.interfaces.ECPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import example.company.acme.AcmeSession;
import example.company.acme.crypto.ECCurves;
import example.company.acme.crypto.ECSignature;
import example.company.acme.crypto.ECSigner;
import example.company.acme.jw.JWA;
import example.company.acme.jw.JWBase64;
import example.company.acme.v2.AcmeException;
import example.company.acme.v2.AcmeResponse;
import example.company.tox.common.Common;

public class AcmeAccountDetails {

	private static Map<String, Object> createJWS(AcmeSession session, String url)
			throws JsonProcessingException, InvalidKeyException, NoSuchAlgorithmException,
			InvalidParameterSpecException, InvalidKeySpecException, SignatureException {

		String kid = session.getAccount().getUrl();
		String nonce = session.getNonce();
		ObjectMapper om = session.getOm();
		ECPrivateKey privateKey = (ECPrivateKey) session.getKeyPairWithJWK().getKeyPair().getPrivate();

		Map<String, Object> protekted = new HashMap<>();
		protekted.put("alg", JWA.ES256);
		protekted.put("kid", kid);
		protekted.put("nonce", nonce);
		protekted.put("url", url);
		String protected64 = JWBase64.encode(om.writeValueAsBytes(protekted));

		Map<String, Object> payload = new HashMap<>();
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

	public static AcmeResponse<Void> sendRequest(AcmeSession session)
			throws ClientProtocolException, IOException, AcmeException {

		String url = session.getAccount().getUrl();

		Map<String, Object> jws;
		try {
			jws = createJWS(session, url);
		} catch (Exception ex) {
			throw new AcmeException(ex);
		}

		byte[] body = session.getOm().writeValueAsBytes(jws);

		return AcmeNetwork.post(url, body, Void.class, session);

	}

}
