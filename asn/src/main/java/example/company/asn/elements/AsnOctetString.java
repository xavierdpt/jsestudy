package example.company.asn.elements;

import java.util.List;

import example.company.asn.AsnClass;
import example.company.asn.AsnEncoding;
import example.company.asn.AsnTag;
import example.company.asn.utils.AsnUtils;
import example.company.tox.common.Bytes;

public class AsnOctetString extends AsnElement {

	private byte[] value;

	public AsnOctetString() {
	}

	public AsnOctetString(Bytes bytes) {
		super(bytes);
		value = contentBytes.toByteArray();
	}

	public AsnOctetString(byte[] value) {
		this.value = value;
	}

	public byte[] getValue() {
		return value;
	}

	public void setValue(byte[] value) {
		this.value = value;
	}

	@Override
	public void encode(List<Byte> bytes) {
		AsnUtils.addIdentifierBytes(bytes, AsnClass.UNIVERSAL, AsnEncoding.PRIMITIVE, AsnTag.OCTET_STRING);
		AsnUtils.addLengthBytes(bytes, value.length);
		AsnUtils.addBytes(bytes, value);
	}
}
