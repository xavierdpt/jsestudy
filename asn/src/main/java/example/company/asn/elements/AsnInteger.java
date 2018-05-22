package example.company.asn.elements;

import example.company.tox.common.Bytes;

public class AsnInteger extends AsnElement {

	private long value;

	public AsnInteger() {
		value = 0;
		for (int i = 0; i < getLength(); ++i) {
			value = (value << 8) + (getContentBytes().at(i) & 0xFF);
		}
	}

	public AsnInteger(Bytes bytes) {
		super(bytes);
	}

	public long getValue() {
		return value;
	}

	public void setValue(long value) {
		this.value = value;
	}

}
