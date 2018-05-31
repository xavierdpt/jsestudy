package example.company.asn.elements;

import java.util.ArrayList;
import java.util.List;

import example.company.asn.AsnClass;
import example.company.asn.AsnEncoding;
import example.company.asn.AsnTag;
import example.company.asn.utils.AsnUtils;
import example.company.tox.common.Bytes;
import example.company.tox.common.Common;

public class AsnInteger extends AsnElement {

	private long value;

	public AsnInteger(Bytes identifierBytes, Bytes lengthBytes, Bytes contentBytes) {
		byte first = contentBytes.at(0);
		for (int i = 0; i < contentBytes.length(); ++i) {
			if (i == 0 && (first & 0x80) == 0x80) {
				value = -1;
			}
			value = (value << 8) + (contentBytes.at(i) & 0xFF);
		}
	}

	public AsnInteger(int value) {
		this.value = value;

	}

	public long getValue() {
		return value;
	}

	public void setValue(long value) {
		this.value = value;
	}

	@Override
	public void encode(List<Byte> bytes) {

		AsnUtils.addIdentifierBytes(bytes, AsnClass.UNIVERSAL, AsnEncoding.PRIMITIVE, AsnTag.INTEGER);

		List<Byte> cb = new ArrayList<>();

		long v = value;
		while (v > 0xFF) {
			cb.add(Common.bit(v & 0xFF));
			v >>= 8;
		}

		int lastByte = (int) (v & 0xFF);
		cb.add(Common.bit(lastByte));

		if (value > 0 && (lastByte & 0x80) == 0x80) {
			cb.add(Common.bit(0));
		} else if (value < 0 && (v & 0xFF) < 0x80) {
			cb.add(Common.bit(0xFF));
		}

		AsnUtils.addLengthBytes(bytes, cb.size());

		for (int i = cb.size() - 1; i >= 0; --i) {
			bytes.add(cb.get(i));
		}

	}

}
