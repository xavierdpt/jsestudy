package example.company.tox.java.security.cert;

import org.junit.Assert;
import org.junit.Test;

import example.company.tox.common.Bytes;
import example.company.tox.common.Common;

public class AsnObjectIdentifierTest {
	@Test
	public void test() {

		Bytes payload = new Bytes(Common.bytes(0x81, 0x34, 0x03));
		String result = AsnObjectIdentifierUtils.parsePayload(payload);
		Assert.assertEquals("2.100.3", result);
	}
}
