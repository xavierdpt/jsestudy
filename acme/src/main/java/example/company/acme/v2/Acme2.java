package example.company.acme.v2;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.fluent.Request;

import com.fasterxml.jackson.databind.ObjectMapper;

import example.company.acme.AcmeSession;
import example.company.acme.jw.JWBase64;
import example.company.acme.v2.account.AcmeAccount;
import example.company.acme.v2.account.AcmeNewAccount;
import example.company.acme.v2.requests.AcmeAccountDetails;
import example.company.acme.v2.requests.AcmeNewOrder;
import example.company.tox.common.Common;
import example.company.tox.common.JsonUtils;

public class Acme2 {

	private static final String DIRECTORY = "/directory";
	public static final String ACME_STAGING_V2 = "https://acme-staging-v02.api.letsencrypt.org";

	public static AcmeDirectoryInfos2 directory(String url, ObjectMapper om, boolean debug)
			throws ClientProtocolException, IOException {

		Request request = Request.Get(url + "/" + DIRECTORY).setHeader("Accept", "application/json");

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

	public static AcmeDirectoryInfos2 directory(String url, ObjectMapper om)
			throws ClientProtocolException, IOException {
		return directory(url, om, false);
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

	public static AcmeResponse<AcmeAccount> newAccount(AcmeSession session, String contact) throws AcmeException {

		try {

			Map<String, Object> newAccountJWS = AcmeNewAccount.createJWS(session, contact);

			return AcmeNewAccount.sendRequest(session, newAccountJWS);

		} catch (Exception ex) {
			throw new AcmeException(ex);
		}
	}

	public static AcmeResponse<AcmeOrder> newOrder(AcmeSession session, String site) throws AcmeException {
		try {
			Map<String, Object> jws = AcmeNewOrder.createJWS(session, site);
			return AcmeNewOrder.sendRequest(session, jws);

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

	public static Challenge challenge(AcmeSession session, String url) throws AcmeException {
		try {
			return GetChallenge.sendRequest(session, url);
		} catch (IOException e) {
			throw new AcmeException(e);
		}
	}

	public static AcmeResponse<String> accountDetails(AcmeSession session) throws AcmeException {
		try {
			return AcmeAccountDetails.sendRequest(session);
		} catch (IOException e) {
			throw new AcmeException(e);
		}
	}

}
