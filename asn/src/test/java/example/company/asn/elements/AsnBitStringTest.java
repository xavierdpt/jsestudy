package example.company.asn.elements;

import org.junit.Assert;
import org.junit.Test;

import example.company.asn.utils.AsnUtils;
import example.company.tox.common.Bytes;
import example.company.tox.common.Common;

public class AsnBitStringTest {

	byte[] encoded = Common.bytes(0x03, 0x82, 0x01, 0x01, 0x00, 0x76, 0xC1, 0xEB, 0xF7, 0x50, 0x2E, 0x2F, 0xDE, 0xCB,
			0xD0, 0x4C, 0xED, 0x9E, 0xC3, 0xAA, 0x09, 0x5E, 0x35, 0x7D, 0x6F, 0x88, 0xFA, 0x3E, 0xE8, 0xE0, 0xD2, 0x3A,
			0x6E, 0xEB, 0x4C, 0x31, 0x9A, 0x9E, 0x59, 0x57, 0xB0, 0x31, 0x84, 0xCC, 0x23, 0xAD, 0x2C, 0xEC, 0x5B, 0xA5,
			0x1F, 0xC1, 0x86, 0xF9, 0x66, 0xB3, 0x78, 0x98, 0xC6, 0x80, 0x63, 0x8A, 0xAB, 0xCB, 0xE7, 0xE8, 0x9F, 0xB4,
			0x27, 0xD9, 0x80, 0xD3, 0x5F, 0x5D, 0xDD, 0xE6, 0xD0, 0x65, 0xE3, 0x67, 0xBD, 0x13, 0x26, 0x75, 0x0F, 0xFC,
			0x15, 0xD2, 0x0D, 0x26, 0xF3, 0x6B, 0x4E, 0xAF, 0x46, 0x86, 0x78, 0x23, 0xC5, 0x3F, 0xEA, 0x5A, 0x68, 0x9C,
			0xC6, 0x63, 0x72, 0x30, 0x78, 0x00, 0x68, 0xB5, 0xD3, 0xFB, 0x5B, 0x8E, 0xEB, 0xFE, 0x21, 0xC2, 0x72, 0x2D,
			0x72, 0x17, 0xA3, 0xC4, 0x5C, 0xE3, 0x8D, 0x19, 0xE4, 0x9E, 0xBC, 0x8E, 0xF9, 0x7F, 0xDB, 0x0E, 0xCB, 0x2A,
			0x6E, 0x82, 0xCA, 0x2A, 0x65, 0x59, 0xEF, 0xD6, 0x6B, 0x7B, 0x1E, 0x61, 0xC9, 0x79, 0x27, 0xE8, 0x1D, 0x39,
			0xE3, 0xC6, 0xDE, 0x26, 0x11, 0x98, 0x10, 0x2E, 0x63, 0x30, 0xD1, 0x30, 0xC8, 0x7A, 0xEA, 0x1F, 0x20, 0x5E,
			0xE8, 0x27, 0x58, 0x2D, 0x72, 0xF8, 0x8D, 0x08, 0x68, 0x12, 0x9B, 0x4D, 0x90, 0x47, 0xD8, 0x92, 0x4A, 0x88,
			0x12, 0x24, 0x3C, 0x45, 0x27, 0x75, 0x1A, 0xD0, 0x67, 0x11, 0x75, 0x02, 0x1C, 0x54, 0xC8, 0x93, 0xD9, 0x24,
			0xE9, 0x55, 0x4A, 0xD0, 0xE4, 0xD0, 0x4B, 0x00, 0x5C, 0xA7, 0x35, 0xC0, 0xB3, 0xE3, 0x49, 0x79, 0xEB, 0x09,
			0x41, 0xC0, 0x56, 0xF5, 0x21, 0x4E, 0xE3, 0xC2, 0x5A, 0x07, 0xDE, 0x1D, 0x00, 0x8A, 0x13, 0x71, 0xD0, 0xEA,
			0xA7, 0x19, 0x2C, 0x39, 0x05, 0x94, 0x5F, 0x10, 0x5C, 0x16, 0xD9, 0x1F, 0xAF);

	byte[] value = Common.bytes(0x76, 0xC1, 0xEB, 0xF7, 0x50, 0x2E, 0x2F, 0xDE, 0xCB, 0xD0, 0x4C, 0xED, 0x9E, 0xC3,
			0xAA, 0x09, 0x5E, 0x35, 0x7D, 0x6F, 0x88, 0xFA, 0x3E, 0xE8, 0xE0, 0xD2, 0x3A, 0x6E, 0xEB, 0x4C, 0x31, 0x9A,
			0x9E, 0x59, 0x57, 0xB0, 0x31, 0x84, 0xCC, 0x23, 0xAD, 0x2C, 0xEC, 0x5B, 0xA5, 0x1F, 0xC1, 0x86, 0xF9, 0x66,
			0xB3, 0x78, 0x98, 0xC6, 0x80, 0x63, 0x8A, 0xAB, 0xCB, 0xE7, 0xE8, 0x9F, 0xB4, 0x27, 0xD9, 0x80, 0xD3, 0x5F,
			0x5D, 0xDD, 0xE6, 0xD0, 0x65, 0xE3, 0x67, 0xBD, 0x13, 0x26, 0x75, 0x0F, 0xFC, 0x15, 0xD2, 0x0D, 0x26, 0xF3,
			0x6B, 0x4E, 0xAF, 0x46, 0x86, 0x78, 0x23, 0xC5, 0x3F, 0xEA, 0x5A, 0x68, 0x9C, 0xC6, 0x63, 0x72, 0x30, 0x78,
			0x00, 0x68, 0xB5, 0xD3, 0xFB, 0x5B, 0x8E, 0xEB, 0xFE, 0x21, 0xC2, 0x72, 0x2D, 0x72, 0x17, 0xA3, 0xC4, 0x5C,
			0xE3, 0x8D, 0x19, 0xE4, 0x9E, 0xBC, 0x8E, 0xF9, 0x7F, 0xDB, 0x0E, 0xCB, 0x2A, 0x6E, 0x82, 0xCA, 0x2A, 0x65,
			0x59, 0xEF, 0xD6, 0x6B, 0x7B, 0x1E, 0x61, 0xC9, 0x79, 0x27, 0xE8, 0x1D, 0x39, 0xE3, 0xC6, 0xDE, 0x26, 0x11,
			0x98, 0x10, 0x2E, 0x63, 0x30, 0xD1, 0x30, 0xC8, 0x7A, 0xEA, 0x1F, 0x20, 0x5E, 0xE8, 0x27, 0x58, 0x2D, 0x72,
			0xF8, 0x8D, 0x08, 0x68, 0x12, 0x9B, 0x4D, 0x90, 0x47, 0xD8, 0x92, 0x4A, 0x88, 0x12, 0x24, 0x3C, 0x45, 0x27,
			0x75, 0x1A, 0xD0, 0x67, 0x11, 0x75, 0x02, 0x1C, 0x54, 0xC8, 0x93, 0xD9, 0x24, 0xE9, 0x55, 0x4A, 0xD0, 0xE4,
			0xD0, 0x4B, 0x00, 0x5C, 0xA7, 0x35, 0xC0, 0xB3, 0xE3, 0x49, 0x79, 0xEB, 0x09, 0x41, 0xC0, 0x56, 0xF5, 0x21,
			0x4E, 0xE3, 0xC2, 0x5A, 0x07, 0xDE, 0x1D, 0x00, 0x8A, 0x13, 0x71, 0xD0, 0xEA, 0xA7, 0x19, 0x2C, 0x39, 0x05,
			0x94, 0x5F, 0x10, 0x5C, 0x16, 0xD9, 0x1F, 0xAF);

	@Test
	public void testDecode() {

		AsnBitString bitString = (AsnBitString) AsnUtils.parse(new Bytes(encoded));
		Assert.assertArrayEquals(value, bitString.getContent());

	}

	@Test
	public void testEncode() {
		byte[] actual = AsnUtils.encode(new AsnBitString(value));
		System.out.println(Common.bytesToString(encoded));
		System.out.println(Common.bytesToString(actual));
		Assert.assertArrayEquals(encoded, actual);
	}

}
