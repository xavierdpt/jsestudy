package example.company.asn.elements;

import java.util.List;

import example.company.asn.AsnClass;
import example.company.asn.AsnEncoding;
import example.company.asn.AsnTag;
import example.company.asn.utils.AsnUtils;
import example.company.tox.common.Bytes;

public class AsnBoolean extends AsnElement {

	private boolean value;

	public AsnBoolean(boolean value) {
		this.value = value;
	}

	public AsnBoolean(Bytes bytes) {
		super(bytes);
		value = contentBytes.at(0) == 0;
	}

	public boolean getValue() {
		return value;
	}

	public void setValue(boolean value) {
		this.value = value;
	}

	@Override
	public void encode(List<Byte> bytes) {
		AsnUtils.addIdentifierBytes(bytes, AsnClass.UNIVERSAL, AsnEncoding.PRIMITIVE, AsnTag.BOOLEAN);
		AsnUtils.addLengthBytes(bytes, 1);
		AsnUtils.addBytes(bytes, new byte[] { (byte) (value ? 0 : 0xFF) });
	}

}
