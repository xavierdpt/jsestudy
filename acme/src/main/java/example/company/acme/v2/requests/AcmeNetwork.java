package example.company.acme.v2.requests;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.fluent.Request;

import com.fasterxml.jackson.databind.ObjectMapper;

import example.company.acme.v2.AcmeResponse;
import example.company.acme.v2.account.AcmeError;

public class AcmeNetwork {

	private static <T> AcmeResponse<T> parseResponse(HttpResponse response, Class<T> clazz, ObjectMapper om,
			boolean raw) throws IOException {

		int code = response.getStatusLine().getStatusCode();
		InputStream is = response.getEntity().getContent();

		AcmeResponse<T> r = new AcmeResponse<T>();

		Header nonceHeader = response.getFirstHeader("Replay-Nonce");
		if (nonceHeader != null) {
			String nonce = nonceHeader.getValue();
			r.setNonce(nonce);
		}

		if (code >= 200 && code < 300) {
			if (code != 204) {
				try {
					T content = null;
					if (String.class.equals(clazz) && raw) {
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						IOUtils.copy(is, baos);
						content = String.class.asSubclass(clazz)
								.cast(new String(baos.toByteArray(), Charset.forName("UTF-8")));
					} else {
						content = om.readValue(is, clazz);
					}
					r.setContent(content);
				} catch (Exception ex) {
					r.setFailed(true);
					r.setFailureDetails(ex.getClass().getName() + " : " + ex.getMessage());
				}
			}
		} else {
			r.setFailed(true);
			try {
				AcmeError error = om.readValue(is, AcmeError.class);
				r.setFailureDetails(error.getDetail());
			} catch (Exception ex) {
				r.setFailureDetails(ex.getClass().getName() + " : " + ex.getMessage());
			}
		}

		return r;
	}

	public static <T> AcmeResponse<T> post(String url, byte[] body, ObjectMapper om, Class<T> clazz, boolean raw)
			throws ClientProtocolException, IOException {

		Request request = Request.Post(url)

				.setHeader("Content-Type", "application/jose+json")

				.bodyByteArray(body);

		ResponseHandler<AcmeResponse<T>> responseHandler = new ResponseHandler<AcmeResponse<T>>() {

			@Override
			public AcmeResponse<T> handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
				return AcmeNetwork.parseResponse(response, clazz, om, raw);
			}

		};

		return request.execute().handleResponse(responseHandler);
	}

}
