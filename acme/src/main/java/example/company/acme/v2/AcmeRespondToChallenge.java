package example.company.acme.v2;

import java.util.HashMap;
import java.util.Map;

import example.company.acme.AcmeSession;
import example.company.acme.v2.requests.AcmeNetwork;

public class AcmeRespondToChallenge {

	public static AcmeResponse<Void> sendRequest(AcmeSession session, Challenge challenge) throws Exception {
		HashMap<Object, Object> payload = new HashMap<>();
		String url = challenge.getUrl();
		Map<String, Object> jws = JWSBuilder.build(session, payload, url);

		byte[] body = session.getOm().writeValueAsBytes(jws);

		return AcmeNetwork.post(url, body, Void.class, session);
	}

}
