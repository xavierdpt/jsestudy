package example.company.tox.java.security.cert;

import javax.xml.bind.annotation.XmlElement;

import example.company.tox.common.Bytes;

public class AsnOctetString extends AsnElement {

	private AsnElement element;

	public AsnOctetString() {
	}

	public AsnOctetString(Bytes bytes) {
		super(bytes);
		element = AsnUtils.parse(contentBytes);
	}

	@XmlElement(name = "octetStringElement")
	public AsnElement getElement() {
		return element;
	}

	public void setElement(AsnElement element) {
		this.element = element;
	}

}
