package example.company.acme.v2;

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

	public static AcmeResponse<AcmeDirectoryInfos2> directory(String url, ObjectMapper om, AcmeSession session) {
		String directoryUrl = url + "/" + DIRECTORY;
		try {
			return AcmeNetwork.get(directoryUrl, AcmeDirectoryInfos2.class, session);
		} catch (Exception exception) {
			return new AcmeResponse<>(exception);
		}

	}

	public static AcmeResponse<String> nonce(AcmeSession session) {
		try {
			return AcmeNetwork.nonce(session);
		} catch (Exception exception) {
			return new AcmeResponse<>(exception);
		}
	}

	public static AcmeResponse<AcmeAccount> newAccount(AcmeSession session, String contact) {
		try {
			return AcmeNewAccount.sendRequest(session, contact);
		} catch (Exception exception) {
			return new AcmeResponse<>(exception);
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
		} catch (Exception exception) {
			return new AcmeResponse<>(exception);
		}
	}

	public static AcmeResponse<Challenge> challenge(AcmeSession session, String url) {
		try {
			return GetChallenge.sendRequest(session, url);
		} catch (Exception exception) {
			return new AcmeResponse<>(exception);
		}
	}

	public static AcmeResponse<Void> accountDetails(AcmeSession session) {
		try {
			return AcmeAccountDetails.sendRequest(session);
		} catch (Exception exception) {
			return new AcmeResponse<>(exception);
		}
	}

	public static AcmeResponse<Boolean> deactivateAccount(AcmeSession session) {
		try {
			return AcmeDeactivateAccount.sendRequest(session);
		} catch (Exception exception) {
			return new AcmeResponse<>(exception);
		}
	}

	public static AcmeResponse<Void> respondToChallenge(AcmeSession session) {
		return respondToChallenge(session, session.getChallenge());
	}

	public static AcmeResponse<Void> respondToChallenge(AcmeSession session, Challenge challenge) {
		try {
			return AcmeRespondToChallenge.sendRequest(session,challenge);
		} catch (Exception exception) {
			return new AcmeResponse<>(exception);
		}
	}

	public static AcmeResponse<Void> finalize(AcmeSession session, byte[] csrBytes) {
		try {
			return AcmeFinalize.sendRequest(session, csrBytes);
		} catch (Exception exception) {
			return new AcmeResponse<>(exception);
		}
	}

}
