package example.company.asn.elements;

import example.company.asn.utils.AsnUtils;
import example.company.tox.common.Bytes;

public class AsnContextSpecific extends AsnElement {

	private long tag;
	private AsnElement element;

	public AsnContextSpecific() {
	}

	public AsnContextSpecific(Bytes bytes) {
		super(bytes);
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

}
