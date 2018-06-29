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
import com.fasterxml.jackson.databind.ObjectMapper;

import example.company.acme.AcmeSession;
import example.company.acme.crypto.ECCurves;
import example.company.acme.crypto.ECSignature;
import example.company.acme.crypto.ECSigner;
import example.company.acme.jw.JWA;
import example.company.acme.jw.JWBase64;
import example.company.acme.v2.account.AcmeError;
import example.company.tox.common.Common;

public class AcmeNewOrder {

	public static Map<String, Object> createJWS(AcmeSession session, String site)
			throws JsonProcessingException, InvalidKeyException, NoSuchAlgorithmException,
			InvalidParameterSpecException, InvalidKeySpecException, SignatureException {

		AcmeDirectoryInfos2 infos = session.getInfos();
		String kid = session.getAccount().getUrl();
		String nonce = session.getNonce();
		ObjectMapper om = session.getOm();
		ECPrivateKey privateKey = (ECPrivateKey) session.getKeyPairWithJWK().getKeyPair().getPrivate();

		Map<String, Object> protekted = new HashMap<>();
		protekted.put("alg", JWA.ES256);
		protekted.put("kid", kid);
		protekted.put("nonce", nonce);
		protekted.put("url", infos.getNewOrder());
		String protected64 = JWBase64.encode(om.writeValueAsBytes(protekted));

		Map<String, String> identifier = new HashMap<>();
		identifier.put("type", "dns");
		identifier.put("value", site);

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

	public static AcmeResponse<AcmeOrder> sendRequest(AcmeSession session, Map<String, Object> jws)
			throws ClientProtocolException, IOException {

		AcmeDirectoryInfos2 infos = session.getInfos();
		ObjectMapper om = session.getOm();

		String url = infos.getNewOrder();
		byte[] body = om.writeValueAsBytes(jws);

		Request request = Request.Post(url)

				.setHeader("Content-Type", "application/jose+json")

				.bodyByteArray(body);

		ResponseHandler<AcmeResponse<AcmeOrder>> responseHandler = new ResponseHandler<AcmeResponse<AcmeOrder>>() {

			@Override
			public AcmeResponse<AcmeOrder> handleResponse(HttpResponse response)
					throws ClientProtocolException, IOException {

				AcmeResponse<AcmeOrder> acmeResponse = new AcmeResponse<AcmeOrder>();

				int code = response.getStatusLine().getStatusCode();
				InputStream content = response.getEntity().getContent();

				String nonce = response.getFirstHeader("Replay-Nonce").getValue();
				acmeResponse.setNonce(nonce);

				if (code == 201) {
					AcmeOrder order = om.readValue(content, AcmeOrder.class);
					acmeResponse.setContent(order);
				} else {
					acmeResponse.setFailed(true);
					AcmeError error = om.readValue(content, AcmeError.class);
					acmeResponse.setFailureDetails(error.getDetail());
				}
				return acmeResponse;
			}

		};

		return request.execute().handleResponse(responseHandler);

	}

}
