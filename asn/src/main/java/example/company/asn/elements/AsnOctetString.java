package example.company.asn.elements;

import example.company.asn.utils.AsnUtils;
import example.company.tox.common.Bytes;

public class AsnOctetString extends AsnElement {

	private AsnElement element;

	public AsnOctetString() {
	}

	public AsnOctetString(Bytes bytes) {
		super(bytes);
		element = AsnUtils.parse(contentBytes);
	}

	public AsnElement getElement() {
		return element;
	}

	public void setElement(AsnElement element) {
		this.element = element;
	}

}
