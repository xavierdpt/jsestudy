package example.company.asn.elements;

import example.company.asn.utils.AsnUtils;
import example.company.tox.common.Bytes;

public class AsnElement {

	protected Integer length;
	protected Bytes identifierBytes;
	protected Bytes lengthBytes;
	protected Bytes contentBytes;

	public AsnElement() {
	}

	public AsnElement(Bytes bytes) {
		identifierBytes = AsnUtils.getIdentifierBytes(bytes);
		lengthBytes = AsnUtils.getLengthBytes(bytes.offset(identifierBytes));
		length = AsnUtils.parseLengthBytes(lengthBytes);
		contentBytes = bytes.subStartLen(identifierBytes.length() + lengthBytes.length(), length);
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
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
