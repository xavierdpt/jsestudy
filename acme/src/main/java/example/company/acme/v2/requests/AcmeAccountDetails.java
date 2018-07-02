package example.company.acme.v2.requests;

import java.util.HashMap;
import java.util.Map;

import example.company.acme.AcmeSession;
import example.company.acme.v2.AcmeResponse;
import example.company.acme.v2.JWSBuilder;

public class AcmeAccountDetails {

	public static AcmeResponse<Void> sendRequest(AcmeSession session) throws Exception {

		HashMap<Object, Object> payload = new HashMap<>();
		String url = session.getAccount().getUrl();
		Map<String, Object> jws = JWSBuilder.build(session, payload, url);

		byte[] body = session.getOm().writeValueAsBytes(jws);

		return AcmeNetwork.post(url, body, Void.class, session);

	}

}
