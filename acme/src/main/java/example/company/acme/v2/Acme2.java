package example.company.acme.v2;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import com.fasterxml.jackson.databind.ObjectMapper;

import example.company.acme.AcmeSession;
import example.company.acme.v2.account.AcmeAccount;
import example.company.acme.v2.account.AcmeNewAccount;
import example.company.acme.v2.requests.AcmeAccountDetails;
import example.company.acme.v2.requests.AcmeNetwork;
import example.company.acme.v2.requests.AcmeNewOrder;

public class Acme2 {

	private static final String DIRECTORY = "/directory";
	public static final String ACME_STAGING_V2 = "https://acme-staging-v02.api.letsencrypt.org";

	public static AcmeResponse<AcmeDirectoryInfos2> directory(String url, ObjectMapper om, AcmeSession session)
			throws ClientProtocolException, IOException {
		String directoryUrl = url + "/" + DIRECTORY;
		return AcmeNetwork.get(directoryUrl, AcmeDirectoryInfos2.class, session);

	}

	public static AcmeResponse<String> nonce(AcmeSession session) throws ClientProtocolException, IOException {
		return AcmeNetwork.nonce(session);
	}

	public static AcmeResponse<AcmeAccount> newAccount(AcmeSession session, String contact) {
		try {
			return AcmeNewAccount.sendRequest(session, contact);
		} catch (Exception ex) {
			return new AcmeResponse<>(ex);
		}
	}

	public static AcmeResponse<AcmeOrder> newOrder(AcmeSession session, String site) {
		try {

			return AcmeNewOrder.sendRequest(session, site);
		} catch (Exception exception) {
			return new AcmeResponse<>(exception);
		}
	}

	public static AcmeResponse<Authorization> getAuthorization(AcmeSession session, String url) {
		try {
			return GetAuthorization.sendRequest(session, url);
		} catch (Exception ex) {
			return new AcmeResponse<>(ex);
		}
	}

	public static AcmeResponse<Challenge> challenge(AcmeSession session, String url) {
		try {
			return GetChallenge.sendRequest(session, url);
		} catch (Exception e) {
			return new AcmeResponse<>(e);
		}
	}

	public static AcmeResponse<Void> accountDetails(AcmeSession session) {
		try {
			return AcmeAccountDetails.sendRequest(session);
		} catch (Exception e) {
			return new AcmeResponse<>(e);
		}
	}

	public static AcmeResponse<Boolean> deactivateAccount(AcmeSession session) throws AcmeException {
		try {
			return AcmeDeactivateAccount.sendRequest(session);
		} catch (Exception e) {
			throw new AcmeException(e);
		}
	}

}
