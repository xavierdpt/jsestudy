package example.company.acme.v2;

import java.io.IOException;
import java.io.InputStream;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.ECPublicKey;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.ECParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.fluent.Request;

import com.fasterxml.jackson.databind.ObjectMapper;

import example.company.tox.common.Common;
import example.company.tox.common.JsonUtils;

public class Acme2 {

	private static final String DIRECTORY = "/directory";
	public static final String ACME_STAGING_V2 = "https://acme-staging-v02.api.letsencrypt.org";

	public static AcmeDirectoryInfos2 directory(ObjectMapper om, boolean debug)
			throws ClientProtocolException, IOException {

		Request request = Request.Get(ACME_STAGING_V2 + "/" + DIRECTORY).setHeader("Accept", "application/json");

		ResponseHandler<AcmeDirectoryInfos2> responseHandler = new ResponseHandler<AcmeDirectoryInfos2>() {
			@Override
			public AcmeDirectoryInfos2 handleResponse(HttpResponse response)
					throws ClientProtocolException, IOException {

				InputStream responseContent = response.getEntity().getContent();

				return JsonUtils.convert(om, responseContent, AcmeDirectoryInfos2.class, debug);
			}

		};

		return request.execute().handleResponse(responseHandler);
	}

	public static AcmeDirectoryInfos2 directory(ObjectMapper om) throws ClientProtocolException, IOException {
		return directory(om, false);
	}

	public static String nonce64(AcmeDirectoryInfos2 infos, boolean debug) throws ClientProtocolException, IOException {

		String url = infos.getNewNonce();

		Request request = Request.Head(url);

		ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

			@Override
			public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
				String value = response.getFirstHeader("Replay-Nonce").getValue();
				if (debug) {
					System.out.println("New nonce : " + value);
				}
				return value;
			}

		};

		return request.execute().handleResponse(responseHandler);
	}

	public static String nonce64(AcmeDirectoryInfos2 infos) throws ClientProtocolException, IOException {
		return nonce64(infos, false);
	}

	public static byte[] nonceBytes(AcmeDirectoryInfos2 infos, boolean debug)
			throws ClientProtocolException, IOException {
		byte[] nonce = JWSBase64.decode(nonce64(infos, debug));
		System.out.println(Common.bytesToString(nonce));
		return nonce;
	}

	public static byte[] nonceBytes(AcmeDirectoryInfos2 infos) throws ClientProtocolException, IOException {
		return JWSBase64.decode(nonce64(infos, false));
	}

	public static AcmeAccount newAccount(AcmeDirectoryInfos2 infos, String nonce, ObjectMapper om, String contact) throws AcmeException {

		try {
			KeyPair keyPair = newKeyPair();

			ECPublicKey publicKey = (ECPublicKey) keyPair.getPublic();

			String x64 = JWSBase64.encode(Common.bigIntegerToBytes(publicKey.getW().getAffineX()));
			String y64 = JWSBase64.encode(Common.bigIntegerToBytes(publicKey.getW().getAffineY()));

			Map<String, Object> newAccountJWS = AcmeNewAccount.newAccountJWS(infos, keyPair, nonce, om,
					getJWK(x64, y64), contact);

			AcmeAccount account = AcmeNewAccount.newAccount(infos, newAccountJWS, om);

			account.setUrl(getUrl(infos, account));

			return account;
		} catch (Exception ex) {
			throw new AcmeException(ex);
		}
	}

	private static KeyPair newKeyPair()
			throws NoSuchAlgorithmException, InvalidParameterSpecException, InvalidAlgorithmParameterException {
		AlgorithmParameterSpec algorithmParameterSpec = new ECGenParameterSpec(ECCurves.NIST_P_256);

		AlgorithmParameters algorithmParameters = AlgorithmParameters.getInstance("EC");
		algorithmParameters.init(algorithmParameterSpec);
		ECParameterSpec ecParameterSpec = algorithmParameters.getParameterSpec(ECParameterSpec.class);

		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC");
		keyPairGenerator.initialize(ecParameterSpec);
		return keyPairGenerator.generateKeyPair();
	}

	private static String getUrl(AcmeDirectoryInfos2 infos, AcmeAccount account) {

		String url = infos.getNewAccountURL();
		int lastSlash = url.lastIndexOf('/');

		return url.substring(0, lastSlash) + "/acct/" + account.getId();
	}

	private static Map<String, String> getJWK(String x64, String y64) {
		Map<String, String> jwk = new HashMap<>();
		jwk.put("kty", "EC");
		jwk.put("crv", "P-256");
		jwk.put("x", x64);
		jwk.put("y", y64);
		return jwk;
	}
}
