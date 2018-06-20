package example.company.acme.v2;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.fluent.Request;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GetAuthorization {

	public static JsonNode sendRequest(String url, ObjectMapper om) throws ClientProtocolException, IOException {

		Request request = Request.Get(url);

		ResponseHandler<JsonNode> responseHandler = new ResponseHandler<JsonNode>() {

			@Override
			public JsonNode handleResponse(HttpResponse response) throws ClientProtocolException, IOException {

				System.out.println(response.getStatusLine().getStatusCode());

				InputStream content = response.getEntity().getContent();

				JsonNode responseJson = om.readValue(content, JsonNode.class);

				return responseJson;
			}

		};

		return request.execute().handleResponse(responseHandler);
	}

}
