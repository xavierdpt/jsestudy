package example.company.jse.fiddle;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.fluent.Request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import example.company.acme.v2.AcmeDirectoryInfos2;
import example.company.acme.v2.JWSBase64;
import example.company.tox.common.Common;

public class AcmeNewAccount {

	public static Map<String, Object> newAccountJWS(AcmeDirectoryInfos2 infos, KeyPair keyPair, String nonce64,
			ObjectMapper om, Map<String, String> jwk) throws JsonProcessingException, InvalidKeyException,
			NoSuchAlgorithmException, InvalidParameterSpecException, InvalidKeySpecException, SignatureException {

		ECPrivateKey privateKey = (ECPrivateKey) keyPair.getPrivate();

		Map<String, Object> protekted = new HashMap<>();
		protekted.put("alg", JWA.ES256);
		protekted.put("jwk", jwk);
		protekted.put("nonce", nonce64);
		protekted.put("url", infos.getNewAccountURL());
		String protected64 = JWSBase64.encode(om.writeValueAsBytes(protekted));

		List<String> contacts = new ArrayList<>();
		contacts.add("mailto:xavierdpt@gmail.com");

		Map<String, Object> payload = new HashMap<>();
		payload.put("termsOfServiceAgreed", true);
		payload.put("contact", contacts);
		String payload64 = JWSBase64.encode(om.writeValueAsBytes(payload));

		byte[] tbs = (protected64 + "." + payload64).getBytes();
		BigInteger s = privateKey.getS();
		ECSignature signature = ECSigner.sign(tbs, ECCurves.NIST_P_256, s);

		byte[] rBytes = Common.bigIntegerToBytes(signature.getR());
		byte[] sBytes = Common.bigIntegerToBytes(signature.getS());
		String signature64 = JWSBase64.encode(Common.concatenate(rBytes, sBytes));

		Map<String, Object> jws = new HashMap<>();
		jws.put("protected", protected64);
		jws.put("payload", payload64);
		jws.put("signature", signature64);
		return jws;
	}

	public static AcmeAccount newAccount(AcmeDirectoryInfos2 infos, Map<String, Object> jws, ObjectMapper om)
			throws ClientProtocolException, IOException {

		String url = infos.getNewAccountURL();
		byte[] body = om.writeValueAsBytes(jws);

		Request request = Request.Post(url)

				.setHeader("Content-Type", "application/jose+json")

				.bodyByteArray(body);

		ResponseHandler<AcmeAccount> responseHandler = new ResponseHandler<AcmeAccount>() {

			@Override
			public AcmeAccount handleResponse(HttpResponse response) throws ClientProtocolException, IOException {

				InputStream content = response.getEntity().getContent();

				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				IOUtils.copy(content, baos);
				byte[] bytes = baos.toByteArray();
				IOUtils.copy(new ByteArrayInputStream(bytes), System.out);
				System.out.println();

				AcmeAccount account = om.readValue(bytes, AcmeAccount.class);
				account.setNonce(response.getFirstHeader("Replay-Nonce").getValue());
				return account;
			}

		};

		return request.execute().handleResponse(responseHandler);

	}
}
