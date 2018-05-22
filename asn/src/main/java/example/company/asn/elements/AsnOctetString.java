package example.company.asn.elements;

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
		element = AsnUtils.parse(contentBytes);
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

}
