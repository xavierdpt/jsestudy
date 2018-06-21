package example.company.acme.v2;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.fluent.Request;

import example.company.acme.AcmeSession;

public class GetChallenge {

	public static Challenge sendRequest(AcmeSession session, String url) throws ClientProtocolException, IOException {

		Request request = Request.Get(url);

		ResponseHandler<Challenge> responseHandler = new ResponseHandler<Challenge>() {

			@Override
			public Challenge handleResponse(HttpResponse response) throws ClientProtocolException, IOException {

				InputStream content = response.getEntity().getContent();

				return session.getOm().readValue(content, Challenge.class);
			}

		};

		return request.execute().handleResponse(responseHandler);
	}

}
