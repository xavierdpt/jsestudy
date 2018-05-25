package example.company.asn.elements;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import example.company.asn.utils.AsnIA5String;
import example.company.asn.utils.AsnUtils;
import example.company.tox.common.Bytes;
import example.company.tox.common.Common;

public class AsnSetTest {

	byte[] encoded = Common.bytes(0x31, 0x0A, 0x16, 0x05, 0x53, 0x6D, 0x69, 0x74, 0x68, 0x01, 0x01, 0xFF);

	@Test
	public void testDecode() {
		AsnSet sequence = (AsnSet) AsnUtils.parse(new Bytes(encoded));
		List<AsnElement> elements = sequence.getElements();
		Assert.assertEquals(2, elements.size());

		AsnIA5String first = (AsnIA5String) elements.get(0);
		Assert.assertEquals("Smith", first.getValue());

		AsnBoolean second = (AsnBoolean) elements.get(1);
		Assert.assertEquals(false, second.getValue());
	}

	@Test
	public void testEncode() {
		AsnSet sequence = new AsnSet();
		List<AsnElement> sequenceElements = sequence.getElements();
		sequenceElements.add(new AsnIA5String("Smith"));
		sequenceElements.add(new AsnBoolean(false));
		byte[] actual = AsnUtils.encode(sequence);
		System.out.println(Common.bytesToString(encoded));
		System.out.println(Common.bytesToString(actual));
		Assert.assertArrayEquals(encoded, actual);
	}

}