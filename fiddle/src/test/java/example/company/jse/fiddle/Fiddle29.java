package example.company.jse.fiddle;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import example.company.tox.common.Common;
import xdptdr.acme.v2.Acme2;
import xdptdr.acme.v2.AcmeDirectoryInfos2;
import xdptdr.acme.v2.AcmeResponse;
import xdptdr.acme.v2.AcmeSession;

public class Fiddle29 {

	@Test
	public void fiddle() throws ClientProtocolException, IOException {

		Common.disableHCLogging();

		ObjectMapper om = new ObjectMapper();

		AcmeSession session = new AcmeSession();
		AcmeDirectoryInfos2 infos = Acme2.directory(Acme2.ACME_STAGING_V2, om, session).getContent();

		AcmeResponse<String> nonce2 = Acme2.nonce(session);
		String nonce = session.getNonce();

		Assert.assertFalse(StringUtils.isBlank(nonce));

	}
}
