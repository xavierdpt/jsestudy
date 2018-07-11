package example.company.jse.fiddle;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.junit.Assert;
import org.junit.Test;

import xdptdr.acme.v2.AcmeSession;
import xdptdr.common.Common;

public class Fiddle29 {

	@Test
	public void fiddle() throws ClientProtocolException, IOException {

		Common.disableHCLogging();

		AcmeSession session = new AcmeSession();

		String nonce = session.getNonce();

		Assert.assertFalse(StringUtils.isBlank(nonce));

	}
}
