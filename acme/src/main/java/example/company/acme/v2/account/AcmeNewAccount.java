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

import example.company.acme.AcmeSession;
import example.company.acme.crypto.ECCurves;
import example.company.acme.crypto.ECSignature;
import example.company.acme.crypto.ECSigner;
import example.company.acme.jw.JWA;
import example.company.acme.jw.JWBase64;
import example.company.acme.v2.AcmeDirectoryInfos2;
import example.company.acme.v2.AcmeResponse;
import example.company.tox.common.Common;

public class AcmeNewAccount {

	public static Map<String, Object> createJWS(AcmeSession session, String contact)
			throws JsonProcessingException, InvalidKeyException, NoSuchAlgorithmException,
			InvalidParameterSpecException, InvalidKeySpecException, SignatureException {

		KeyPair keyPair = session.getKeyPairWithJWK().getKeyPair();

		ECPrivateKey privateKey = (ECPrivateKey) keyPair.getPrivate();

		Map<String, Object> protekted = new HashMap<>();
		protekted.put("alg", JWA.ES256);
		protekted.put("jwk", session.getKeyPairWithJWK().getPublicJwk());
		protekted.put("nonce", session.getNonce());
		protekted.put("url", session.getInfos().getNewAccountURL());
		String protected64 = JWBase64.encode(session.getOm().writeValueAsBytes(protekted));

		List<String> contacts = new ArrayList<>();
		String prefix = "";
		if (!contact.startsWith("mailto:")) {
			prefix = "mailto:";
		}
		contacts.add(prefix + contact);

		Map<String, Object> payload = new HashMap<>();
		payload.put("termsOfServiceAgreed", true);
		payload.put("contact", contacts);
		String payload64 = JWBase64.encode(session.getOm().writeValueAsBytes(payload));

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

	public static AcmeResponse<AcmeAccount> sendRequest(AcmeSession session, Map<String, Object> jws)
			throws ClientProtocolException, IOException {

		String url = session.getInfos().getNewAccountURL();
		byte[] body = session.getOm().writeValueAsBytes(jws);

		Request request = Request.Post(url)

				.setHeader("Content-Type", "application/jose+json")

				.bodyByteArray(body);

		ResponseHandler<AcmeResponse<AcmeAccount>> responseHandler = new ResponseHandler<AcmeResponse<AcmeAccount>>() {

			@Override
			public AcmeResponse<AcmeAccount> handleResponse(HttpResponse response)
					throws ClientProtocolException, IOException {

				String nonce = response.getFirstHeader("Replay-Nonce").getValue();

				int code = response.getStatusLine().getStatusCode();
				InputStream content = response.getEntity().getContent();

				if (code == 400) {
					AcmeError error = session.getOm().readValue(content, AcmeError.class);
					throw new ClientProtocolException(error.getDetail());
				} else {
					AcmeAccount account = session.getOm().readValue(content, AcmeAccount.class);

					account.setUrl(getUrl(session.getInfos(), account));

					return new AcmeResponse<AcmeAccount>(nonce, account);
				}
			}

		};

		return request.execute().handleResponse(responseHandler);

	}

	private static String getUrl(AcmeDirectoryInfos2 infos, AcmeAccount account) {

		String url = infos.getNewAccountURL();
		int lastSlash = url.lastIndexOf('/');

		return url.substring(0, lastSlash) + "/acct/" + account.getId();
	}
}
