package example.company.jse.fiddle;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import xdptdr.acme.v1.Acme1;
import xdptdr.acme.v1.AcmeDirectoryInfos1;
import xdptdr.acme.v2.Acme2;
import xdptdr.acme.v2.AcmeSession;
import xdptdr.acme.v2.model.AcmeDirectoryInfos2;
import xdptdr.common.Common;

public class Fiddle28 {

	@Test
	public void fiddleV1() throws ClientProtocolException, IOException {

		Common.disableHCLogging();

		ObjectMapper om = new ObjectMapper();

		AcmeDirectoryInfos1 infos = Acme1.directory(om);

		Assert.assertEquals("https://acme-staging.api.letsencrypt.org/acme/new-authz", infos.getNewAuthz());
		Assert.assertEquals("https://acme-staging.api.letsencrypt.org/acme/new-cert", infos.getNewCert());
		Assert.assertEquals("https://acme-staging.api.letsencrypt.org/acme/new-reg", infos.getNewReg());
		Assert.assertEquals("https://acme-staging.api.letsencrypt.org/acme/revoke-cert", infos.getRevokeCert());

	}

	@Test
	public void fiddleV2() throws ClientProtocolException, IOException {

		Common.disableHCLogging();

		ObjectMapper om = new ObjectMapper();

		AcmeSession session = new AcmeSession();
		AcmeDirectoryInfos2 infos = Acme2.directory(Acme2.ACME_STAGING_V2, om, session).getContent();

		Assert.assertEquals("https://acme-staging-v02.api.letsencrypt.org/acme/key-change", infos.getKeyChange());
		Assert.assertEquals("https://acme-staging-v02.api.letsencrypt.org/acme/new-acct", infos.getNewAccountURL());
		Assert.assertEquals("https://acme-staging-v02.api.letsencrypt.org/acme/new-nonce", infos.getNewNonce());
		Assert.assertEquals("https://acme-staging-v02.api.letsencrypt.org/acme/new-order", infos.getNewOrder());
		Assert.assertEquals("https://acme-staging-v02.api.letsencrypt.org/acme/revoke-cert", infos.getRevokeCert());

	}

}
