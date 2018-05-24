package example.company.asn.elements;

import org.junit.Assert;
import org.junit.Test;

import example.company.asn.utils.AsnUtils;
import example.company.tox.common.Bytes;
import example.company.tox.common.Common;

public class AsnContextSpecificTest {

	private byte[] encoded = Common.bytes(0xA3, 0x02, 0x05, 0x00);

	@Test
	public void testEncode() {
		AsnContextSpecific contextSpecific = new AsnContextSpecific(3);
		contextSpecific.setElement(new AsnNull());

		byte[] actual = AsnUtils.encode(contextSpecific);
		Assert.assertArrayEquals(encoded, actual);
	}

	@Test
	public void testDecode() {
		AsnContextSpecific acs = (AsnContextSpecific) AsnUtils.parse(new Bytes(encoded));
		Assert.assertEquals(3, acs.getTag());
		Assert.assertTrue(acs.getElement() instanceof AsnNull);
	}

}
