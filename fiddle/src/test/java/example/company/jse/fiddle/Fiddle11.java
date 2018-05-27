package example.company.jse.fiddle;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.Base64;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

import example.company.asn.elements.AsnElement;
import example.company.asn.utils.AsnObjectIdentifierUtils;
import example.company.asn.utils.AsnUtils;
import example.company.tox.asn.AsnTox;
import example.company.tox.common.Common;
import example.company.tox.common.Tox;

public class Fiddle11 {

	/**
	 * Analyze the certificate signing request client.crq
	 * 
	 * @throws IOException
	 */
	@Test
	public void fiddle() throws IOException {
		InputStream r = this.getClass().getResourceAsStream("/genclient/step3/client.crq");
		Assert.assertNotNull(r);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		IOUtils.copy(r, baos);
		byte[] bytes = baos.toByteArray();
		System.out.println(Common.bytesToString(bytes));
		String str = new String(bytes, Charset.forName("UTF-8"));
		String[] lines = str.split("[\\r\\n]");
		StringWriter sw = new StringWriter();
		for (int i = 1; i < lines.length - 1; ++i) {
			sw.append(lines[i]);
		}
		byte[] d = Base64.getDecoder().decode(sw.toString());
		System.out.println("-");
		System.out.println(Common.bytesToString(d));
		AsnElement asn = AsnUtils.parse(d);
		Tox.print(new AsnTox().tox(asn), System.out);
		AsnObjectIdentifierUtils.class.getName();

	}
}
