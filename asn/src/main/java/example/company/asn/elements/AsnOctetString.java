package example.company.asn.elements;

import java.util.List;

import example.company.asn.AsnClass;
import example.company.asn.AsnEncoding;
import example.company.asn.AsnTag;
import example.company.asn.utils.AsnUtils;
import example.company.tox.common.Bytes;

public class AsnOctetString extends AsnElement {

	private AsnElement element;
	private byte[] rawBytes;

	public AsnOctetString() {
	}

	public AsnOctetString(Bytes bytes) {
		super(bytes);
		rawBytes = contentBytes.toByteArray();
//		element = AsnUtils.parse(contentBytes);
	}

	public AsnElement getElement() {
		return element;
	}

	public void setElement(AsnElement element) {
		this.element = element;
	}

	public byte[] getRawBytes() {
		return rawBytes;
	}

	public void setRawBytes(byte[] rawBytes) {
		this.rawBytes = rawBytes;
	}

	@Override
	public void encode(List<Byte> bytes) {
		AsnUtils.addIdentifierBytes(bytes, AsnClass.UNIVERSAL, AsnEncoding.PRIMITIVE, AsnTag.OCTET_STRING);
		AsnUtils.addLengthBytes(bytes, rawBytes.length);
		AsnUtils.addBytes(bytes, rawBytes);
	}
}
