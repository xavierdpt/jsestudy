package example.company.jse.fiddle;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import example.company.acme.v2.Acme2;
import example.company.acme.v2.AcmeDirectoryInfos2;
import example.company.tox.common.Common;

public class Fiddle29 {

	@Test
	public void fiddle() throws ClientProtocolException, IOException {

		Common.disableHCLogging();

		ObjectMapper om = new ObjectMapper();

		AcmeDirectoryInfos2 infos = Acme2.directory(om);

		String nonce = Acme2.nonce64(infos);

		Assert.assertFalse(StringUtils.isBlank(nonce));

	}
}
