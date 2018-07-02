package example.company.acme.v2;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import example.company.acme.AcmeSession;
import example.company.acme.v2.requests.AcmeNetwork;

public class GetChallenge {

	public static AcmeResponse<Challenge> sendRequest(AcmeSession session, String url)
			throws ClientProtocolException, IOException {
		return AcmeNetwork.get(url, Challenge.class, session);
	}

}
