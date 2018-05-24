package example.company.asn.elements;

import org.junit.Assert;
import org.junit.Test;

import example.company.asn.utils.AsnUtils;
import example.company.tox.common.Bytes;
import example.company.tox.common.Common;

public class AsnUtcTimeTest {

	String value = "18/05/16 08:29:59 Z";
	byte[] encoded = Common.bytes(0x17, 0x0D, 0x31, 0x38, 0x30, 0x35, 0x31, 0x36, 0x30, 0x38, 0x32, 0x39, 0x35, 0x39,
			0x5A);

	@Test
	public void testDecode() {
		AsnUtcTime utcTime = (AsnUtcTime) AsnUtils.parse(new Bytes(encoded));
		Assert.assertEquals(value, utcTime.getValue());
	}

	@Test
	public void testEncode() {
		AsnUtcTime utcTime = new AsnUtcTime(value);
		byte[] actual = AsnUtils.encode(utcTime);
		Assert.assertArrayEquals(encoded, actual);
	}
}
