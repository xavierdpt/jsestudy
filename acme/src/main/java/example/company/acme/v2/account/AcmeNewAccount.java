package example.company.acme.v2.account;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.interfaces.ECPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.fluent.Request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import example.company.acme.AcmeSession;
import example.company.acme.crypto.ECCurves;
import example.company.acme.crypto.ECSignature;
import example.company.acme.crypto.ECSigner;
import example.company.acme.jw.JWA;
import example.company.acme.jw.JWBase64;
import example.company.acme.v2.AcmeDirectoryInfos2;
import example.company.tox.common.Common;

public class AcmeNewAccount {

	public static Map<String, Object> createJWS(AcmeDirectoryInfos2 infos, KeyPair keyPair, String nonce64,
			ObjectMapper om, Map<String, String> jwk, String contact)
			throws JsonProcessingException, InvalidKeyException, NoSuchAlgorithmException,
			InvalidParameterSpecException, InvalidKeySpecException, SignatureException {

		ECPrivateKey privateKey = (ECPrivateKey) keyPair.getPrivate();

		Map<String, Object> protekted = new HashMap<>();
		protekted.put("alg", JWA.ES256);
		protekted.put("jwk", jwk);
		protekted.put("nonce", nonce64);
		protekted.put("url", infos.getNewAccountURL());
		String protected64 = JWBase64.encode(om.writeValueAsBytes(protekted));

		List<String> contacts = new ArrayList<>();
		String prefix = "";
		if (!contact.startsWith("mailto:")) {
			prefix = "mailto:";
		}
		contacts.add(prefix + contact);

		Map<String, Object> payload = new HashMap<>();
		payload.put("termsOfServiceAgreed", true);
		payload.put("contact", contacts);
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

	public static AcmeAccount sendRequest(AcmeSession session, Map<String, Object> jws)
			throws ClientProtocolException, IOException {

		String url = session.getInfos().getNewAccountURL();
		byte[] body = session.getOm().writeValueAsBytes(jws);

		Request request = Request.Post(url)

				.setHeader("Content-Type", "application/jose+json")

				.bodyByteArray(body);

		ResponseHandler<AcmeAccount> responseHandler = new ResponseHandler<AcmeAccount>() {

			@Override
			public AcmeAccount handleResponse(HttpResponse response) throws ClientProtocolException, IOException {

				InputStream content = response.getEntity().getContent();

				AcmeAccount account = session.getOm().readValue(content, AcmeAccount.class);

				session.setNonce(response.getFirstHeader("Replay-Nonce").getValue());

				return account;
			}

		};

		return request.execute().handleResponse(responseHandler);

	}

}
