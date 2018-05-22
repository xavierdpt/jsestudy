package example.company.asn.elements;

import example.company.tox.common.Bytes;

public class AsnBitString extends AsnElement {

	private byte[] content;

	public AsnBitString() {
	}

	public AsnBitString(Bytes bytes) {
		super(bytes);
		content = contentBytes.toByteArray();
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

}