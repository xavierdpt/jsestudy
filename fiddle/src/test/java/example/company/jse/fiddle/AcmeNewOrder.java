package example.company.jse.fiddle;

import java.io.IOException;
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

import example.company.acme.v2.AcmeDirectoryInfos2;
import example.company.acme.v2.JWSBase64;
import example.company.tox.common.Common;

public class AcmeNewOrder {

	public static Map<String, Object> newOrderJWS(String kid, String nonce, String url, ObjectMapper om,
			KeyPair keyPair, Map<String, String> jwk) throws JsonProcessingException, InvalidKeyException,
			NoSuchAlgorithmException, InvalidParameterSpecException, InvalidKeySpecException, SignatureException {

		ECPrivateKey privateKey = (ECPrivateKey) keyPair.getPrivate();

		Map<String, Object> protekted = new HashMap<>();
		protekted.put("alg", JWA.ES256);
		protekted.put("kid", kid);
		protekted.put("nonce", nonce);
		protekted.put("url", url);
		String protekted64 = JWSBase64.encode(om.writeValueAsBytes(protekted));

		Map<String, Object> identifier = new HashMap<>();
		identifier.put("type", "dns");
		identifier.put("value", "choupiproject.hopto.org");

		List<Map<String, Object>> identifiers = new ArrayList<>();
		identifiers.add(identifier);

		Map<String, Object> payload = new HashMap<>();
		payload.put("identifiers", identifiers);
//		payload.put("notBefore", "2018-06-01T00:00:00Z");
//		payload.put("notAfter", "2018-09-01T00:00:00Z");
		String payload64 = JWSBase64.encode(om.writeValueAsBytes(payload));

		byte[] tbs = (protekted64 + "." + payload64).getBytes();

		ECSignature signature = ECSigner.sign(tbs, ECCurves.NIST_P_256, privateKey.getS());
		String signature64 = JWSBase64.encode(Common.concatenate(Common.bigIntegerToBytes(signature.getR()),
				Common.bigIntegerToBytes(signature.getS())));

		Map<String, Object> jws = new HashMap<>();
		jws.put("protected", protekted64);
		jws.put("payload", payload64);
		jws.put("signature", signature64);

		return jws;
	}

	public static Object newOrder(AcmeDirectoryInfos2 infos, Map<String, Object> jws, ObjectMapper om)
			throws ClientProtocolException, IOException {

		String url = infos.getNewOrder();
		byte[] body = om.writeValueAsBytes(jws);

		Request request = Request.Post(url)

				.setHeader("Content-Type", "application/jose+json")

				.bodyByteArray(body);

		ResponseHandler<Object> responseHandler = new ResponseHandler<Object>() {

			@Override
			public Object handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
				response.getEntity().writeTo(System.out);
				return null;
			}

		};

		return request.execute().handleResponse(responseHandler);

	}

}
