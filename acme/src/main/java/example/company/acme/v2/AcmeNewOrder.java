package example.company.acme.v2;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.InvalidKeyException;
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
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import example.company.acme.crypto.ECCurves;
import example.company.acme.crypto.ECSignature;
import example.company.acme.crypto.ECSigner;
import example.company.acme.jw.JWA;
import example.company.acme.jw.JWBase64;
import example.company.tox.common.Common;

public class AcmeNewOrder {

	public static Map<String, Object> createJWS(AcmeDirectoryInfos2 infos, String kid, String nonce, ObjectMapper om,
			ECPrivateKey privateKey) throws JsonProcessingException, InvalidKeyException, NoSuchAlgorithmException,
			InvalidParameterSpecException, InvalidKeySpecException, SignatureException {

		Map<String, Object> protekted = new HashMap<>();
		protekted.put("alg", JWA.ES256);
		protekted.put("kid", kid);
		protekted.put("nonce", nonce);
		protekted.put("url", infos.getNewOrder());
		String protected64 = JWBase64.encode(om.writeValueAsBytes(protekted));

		Map<String, String> identifier = new HashMap<>();
		identifier.put("type", "dns");
		identifier.put("value", "example.com");

		List<Map<String, String>> identifiers = new ArrayList<>();
		identifiers.add(identifier);

		Map<String, Object> payload = new HashMap<>();
		payload.put("identifiers", identifiers);
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

	public static AcmeOrderWithNonce sendRequest(AcmeDirectoryInfos2 infos, ObjectMapper om, Map<String, Object> jws)
			throws ClientProtocolException, IOException {

		String url = infos.getNewOrder();
		byte[] body = om.writeValueAsBytes(jws);

		Request request = Request.Post(url)

				.setHeader("Content-Type", "application/jose+json")

				.bodyByteArray(body);

		ResponseHandler<AcmeOrderWithNonce> responseHandler = new ResponseHandler<AcmeOrderWithNonce>() {

			@Override
			public AcmeOrderWithNonce handleResponse(HttpResponse response)
					throws ClientProtocolException, IOException {

				InputStream content = response.getEntity().getContent();

				JsonNode responseJson = om.readValue(content, JsonNode.class);

				if (response.getStatusLine().getStatusCode() != 200) {
					if (responseJson.has("detail")) {
						throw new ClientProtocolException(responseJson.get("detail").asText());
					}
				}

				AcmeOrderWithNonce order = new AcmeOrderWithNonce();
				order.setContent(om.treeToValue(responseJson, AcmeOrder.class));
				order.setNonce(response.getFirstHeader("Replay-Nonce").getValue());
				return order;
			}

		};

		return request.execute().handleResponse(responseHandler);

	}

}
