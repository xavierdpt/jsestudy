package example.company.jse.fiddle;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SignatureException;
import java.security.interfaces.ECPublicKey;
import java.security.spec.InvalidParameterSpecException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import example.company.acme.v2.Acme2;
import example.company.acme.v2.AcmeDirectoryInfos2;
import example.company.acme.v2.JWSBase64;
import example.company.acme.v2.NewAccountRequest;
import example.company.acme.v2.NewAccountResponse;
import example.company.tox.common.Common;

public class Fiddle36 {

	@Test
	public void fiddle() throws ClientProtocolException, IOException, NoSuchAlgorithmException,
			InvalidParameterSpecException, InvalidAlgorithmParameterException, InvalidKeyException, SignatureException {

		KeyPair keys = EC.genKeyPair(EC.NIST_P_256);
		ECPublicKey publik = (ECPublicKey) keys.getPublic();

		Common.disableHCLogging();

		ObjectMapper om = new ObjectMapper();
		om.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

		AcmeDirectoryInfos2 infos = Acme2.directory(om);

		String nonce = Acme2.nonce(infos);

		String url = infos.getNewAccountURL();

		JWK jwk = new JWK();
		jwk.setX(JWSBase64.encode(publik.getW().getAffineX().toByteArray()));
		jwk.setY(JWSBase64.encode(publik.getW().getAffineY().toByteArray()));

		JWKProtected jwkProtected = new JWKProtected();
		jwkProtected.setAlg("ES256");
		jwkProtected.setNonce(nonce);
		jwkProtected.setUrl(url);
		jwkProtected.setJwk(jwk);

		Payload payload = new Payload();

		byte[] protectedBytes = om.writeValueAsBytes(jwkProtected);
		byte[] payloadBytes = om.writeValueAsBytes(payload);

		NewAccountRequest request = new NewAccountRequest();
		request.setProtected(JWSBase64.encode(protectedBytes));
		request.setPayload(JWSBase64.encode(payloadBytes));

		byte[] tbs = (request.getProtected() + "." + request.getPayload()).getBytes();
		request.setSignature(JWSBase64.encode(sign(keys.getPrivate(), tbs, EC.SHA256WITH_ECDSA)));

		System.out.println(om.writeValueAsString(request));
		HttpEntity entity = new ByteArrayEntity(om.writeValueAsBytes(request));
		NewAccountResponse response = Request.Post(url).body(entity).setHeader("Content-Type", "application/jose+json")
				.bodyString(om.writeValueAsString(request), ContentType.create("application/jose+json")).execute()
				.handleResponse(new ResponseHandler<NewAccountResponse>() {

					@Override
					public NewAccountResponse handleResponse(HttpResponse response)
							throws ClientProtocolException, IOException {
						System.out.println(response.getStatusLine().getStatusCode());
						String nonce2 = response.getFirstHeader("Replay-Nonce").getValue();
						String location = response.getFirstHeader("Location").getValue();
						return new NewAccountResponse(nonce2, location);
					}

				});

		System.out.println(nonce);
		System.out.println(response.getNonce());
		System.out.println(response.getLocation());

	}

	private byte[] sign(PrivateKey privateKey, byte[] tbs, String algorithm)
			throws InvalidKeyException, NoSuchAlgorithmException, SignatureException {
		return EC.sign(privateKey, tbs, algorithm);
	}

}
