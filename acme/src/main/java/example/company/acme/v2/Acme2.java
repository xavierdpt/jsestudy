package example.company.acme.v2;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyPair;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.fluent.Request;

import com.fasterxml.jackson.databind.ObjectMapper;

import example.company.acme.AcmeSession;
import example.company.acme.crypto.KPG;
import example.company.acme.jw.JWBase64;
import example.company.acme.v2.account.AcmeAccount;
import example.company.acme.v2.account.AcmeNewAccount;
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

	public static String nonce(AcmeDirectoryInfos2 infos, boolean debug) throws ClientProtocolException, IOException {

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

	public static String nonce(AcmeDirectoryInfos2 infos) throws ClientProtocolException, IOException {
		return nonce(infos, false);
	}

	public static byte[] nonceBytes(AcmeDirectoryInfos2 infos, boolean debug)
			throws ClientProtocolException, IOException {
		byte[] nonce = JWBase64.decode(nonce(infos, debug));
		System.out.println(Common.bytesToString(nonce));
		return nonce;
	}

	public static byte[] nonceBytes(AcmeDirectoryInfos2 infos) throws ClientProtocolException, IOException {
		return JWBase64.decode(nonce(infos, false));
	}

	public static AcmeSession newAccount(AcmeSession session, String contact) throws AcmeException {

		try {
			KeyPair keyPair = KPG.newECP256KeyPair();
			keyPair = session.getKeyPair();

			ECPublicKey publicKey = (ECPublicKey) keyPair.getPublic();

			String x64 = JWBase64.encode(Common.bigIntegerToBytes(publicKey.getW().getAffineX()));
			String y64 = JWBase64.encode(Common.bigIntegerToBytes(publicKey.getW().getAffineY()));

			Map<String, Object> newAccountJWS = AcmeNewAccount.createJWS(session.getInfos(), keyPair,
					session.getNonce(), session.getOm(), getJWK(x64, y64), contact);

			AcmeAccount account = AcmeNewAccount.sendRequest(session, newAccountJWS);

			account.setUrl(getUrl(session.getInfos(), account));

			account.setPrivateKey(keyPair.getPrivate());

			session.setAccount(account);

			return session;
		} catch (Exception ex) {
			throw new AcmeException(ex);
		}
	}

	public static AcmeOrderWithNonce newOrder(AcmeDirectoryInfos2 infos, String kid, String nonce, ObjectMapper om,
			ECPrivateKey privateKey) throws AcmeException {
		try {
			Map<String, Object> jws = AcmeNewOrder.createJWS(infos, kid, nonce, om, privateKey);
			return AcmeNewOrder.sendRequest(infos, om, jws);

		} catch (Exception exception) {
			throw new AcmeException(exception);
		}
	}

	public static Authorization getAuthorization(String url, ObjectMapper om) throws AcmeException {
		try {
			return GetAuthorization.sendRequest(url, om);
		} catch (Exception ex) {
			throw new AcmeException(ex);
		}
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

	public static Challenge challenge(AcmeSession session, String url) throws AcmeException {
		try {
			return GetChallenge.sendRequest(session, url);
		} catch (IOException e) {
			throw new AcmeException(e);
		}
	}

}
