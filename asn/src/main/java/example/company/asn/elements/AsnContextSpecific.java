package example.company.asn.elements;

import java.util.List;

import example.company.asn.AsnClass;
import example.company.asn.AsnEncoding;
import example.company.asn.utils.AsnUtils;
import example.company.tox.common.Bytes;

public class AsnContextSpecific extends AsnElement {

	private long tag;
	private AsnElement element;

	public AsnContextSpecific(long tag) {
		this.tag = tag;
	}

	public AsnContextSpecific(Bytes identifierBytes, Bytes lengthBytes, Bytes contentBytes) {
		tag = AsnUtils.parseTag(identifierBytes);
		element = AsnUtils.parse(contentBytes);
	}

	public long getTag() {
		return tag;
	}

	public void setTag(long tag) {
		this.tag = tag;
	}

	public AsnElement getElement() {
		return element;
	}

	public void setElement(AsnElement element) {
		this.element = element;
	}

	@Override
	public void encode(List<Byte> bytes) {
		AsnUtils.addIdentifierBytes(bytes, AsnClass.CONTEXT_SPECIFIC, AsnEncoding.CONSTRUCTED, tag);
		byte[] elementBytes = AsnUtils.encode(element);
		AsnUtils.addLengthBytes(bytes, elementBytes.length);
		AsnUtils.addBytes(bytes, elementBytes);
	}

	public AsnElement get() {
		return element;
	}

	public AsnSequence getSequence() {
		return (AsnSequence) element;
	}
}
