package example.company.asn.elements;

import org.junit.Assert;
import org.junit.Test;

import example.company.asn.elements.AsnPrintableString;
import example.company.asn.utils.AsnUtils;
import example.company.tox.common.Common;

public class AsnPrintableStringEncodingTest {

	@Test
	public void shortAsciiString() {
		AsnPrintableString string = new AsnPrintableString("FirstAndLastName");
		byte[] actual = AsnUtils.encode(string);
		byte[] expected = Common.bytes(0x13, 0x10, 0x46, 0x69, 0x72, 0x73, 0x74, 0x41, 0x6E, 0x64, 0x4C, 0x61, 0x73,
				0x74, 0x4E, 0x61, 0x6D, 0x65);
		Assert.assertArrayEquals(expected, actual);
	}

}
