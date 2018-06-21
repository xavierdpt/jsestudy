package example.company.acme.v2;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.fluent.Request;

import com.fasterxml.jackson.databind.ObjectMapper;

public class GetAuthorization {

	public static Authorization sendRequest(String url, ObjectMapper om) throws ClientProtocolException, IOException {

		Request request = Request.Get(url);

		ResponseHandler<Authorization> responseHandler = new ResponseHandler<Authorization>() {

			@Override
			public Authorization handleResponse(HttpResponse response) throws ClientProtocolException, IOException {

				System.out.println(response.getStatusLine().getStatusCode());

				InputStream content = response.getEntity().getContent();

				Authorization authorization = om.readValue(content, Authorization.class);

				return authorization;
			}

		};

		return request.execute().handleResponse(responseHandler);
	}

}
