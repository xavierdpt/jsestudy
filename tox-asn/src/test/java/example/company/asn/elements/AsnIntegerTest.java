package example.company.asn.elements;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import example.company.asn.utils.AsnUtils;
import example.company.tox.common.Bytes;
import example.company.tox.common.Common;

public class AsnIntegerTest {

	private static class SpecCase {
		public int value;
		public byte[] encoded;

		public SpecCase(int value, byte[] expected) {
			this.value = value;
			this.encoded = expected;
		}

	}

	static List<SpecCase> specCases = new ArrayList<>();
	static {
		specCases.add(new SpecCase(0, Common.bytes(0x02, 0x01, 0x00)));
		specCases.add(new SpecCase(0x7F, Common.bytes(0x02, 0x01, 0x7F)));
		specCases.add(new SpecCase(0x80, Common.bytes(0x02, 0x02, 0x00, 0x80)));
		specCases.add(new SpecCase(0x100, Common.bytes(0x02, 0x02, 0x01, 0x00)));
		specCases.add(new SpecCase(-128, Common.bytes(0x02, 0x01, 0x80)));
		specCases.add(new SpecCase(-129, Common.bytes(0x02, 0x02, 0xFF, 0x7F)));
	}

	@Test
	public void encodingTest() {
		specCases.forEach(tc -> {
			AsnInteger integer = new AsnInteger(tc.value);
			byte[] actual = AsnUtils.encode(integer);
			Assert.assertArrayEquals(tc.encoded, actual);
		});
	}

	@Test
	public void decodingTest() {
		specCases.forEach(tc -> {
			AsnInteger actual = (AsnInteger) AsnUtils.parse(new Bytes(tc.encoded));
			Assert.assertEquals(tc.value, actual.getValue());
		});
	}
}
