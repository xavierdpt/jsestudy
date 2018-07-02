package example.company.acme.v2;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import example.company.acme.AcmeSession;
import example.company.acme.v2.requests.AcmeNetwork;

public class GetAuthorization {

	public static AcmeResponse<Authorization> sendRequest(AcmeSession session, String url)
			throws ClientProtocolException, IOException {
		return AcmeNetwork.get(url, Authorization.class, session);
	}

}
