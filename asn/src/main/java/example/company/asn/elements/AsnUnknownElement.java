package example.company.asn.elements;

import example.company.tox.common.Bytes;

public class AsnUnknownElement extends AsnElement {

	private Bytes identifierBytes;
	private Bytes lengthBytes;
	private Bytes contentBytes;

	public AsnUnknownElement(Bytes identifierBytes, Bytes lengthBytes, Bytes contentBytes) {
		this.identifierBytes = identifierBytes;
		this.lengthBytes = lengthBytes;
		this.contentBytes = contentBytes;
	}

	public Bytes getIdentifierBytes() {
		return identifierBytes;
	}

	public void setIdentifierBytes(Bytes identifierBytes) {
		this.identifierBytes = identifierBytes;
	}

	public Bytes getLengthBytes() {
		return lengthBytes;
	}

	public void setLengthBytes(Bytes lengthBytes) {
		this.lengthBytes = lengthBytes;
	}

	public Bytes getContentBytes() {
		return contentBytes;
	}

	public void setContentBytes(Bytes contentBytes) {
		this.contentBytes = contentBytes;
	}

}
