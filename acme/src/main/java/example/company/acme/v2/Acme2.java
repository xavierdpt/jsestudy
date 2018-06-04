package example.company.acme.v2;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.fluent.Request;

import com.fasterxml.jackson.databind.ObjectMapper;

import example.company.tox.common.JsonUtils;

public class Acme2 {

	private static final String DIRECTORY = "/directory";
	private static final String ACME_STAGING_V2 = "https://acme-staging-v02.api.letsencrypt.org";

	public static AcmeDirectoryInfos2 directory(ObjectMapper om) throws ClientProtocolException, IOException {
		return Request.Get(ACME_STAGING_V2 + "/" + DIRECTORY).setHeader("Accept", "application/json").execute()
				.handleResponse(new ResponseHandler<AcmeDirectoryInfos2>() {
					@Override
					public AcmeDirectoryInfos2 handleResponse(HttpResponse response)
							throws ClientProtocolException, IOException {
						return JsonUtils.convert(om, response.getEntity().getContent(), AcmeDirectoryInfos2.class,
								false);
					}

				});
	}

	public static String nonce(AcmeDirectoryInfos2 infos) throws ClientProtocolException, IOException {

		String url = infos.getNewNonce();

		return Request.Head(url).execute().handleResponse(new ResponseHandler<String>() {

			@Override
			public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
				return response.getFirstHeader("Replay-Nonce").getValue();
			}

		});
	}

}
