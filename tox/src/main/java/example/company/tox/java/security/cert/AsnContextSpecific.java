package example.company.tox.java.security.cert;

import javax.xml.bind.annotation.XmlAttribute;

public class AsnContextSpecific extends AsnElement {

	private long tag;
	private AsnElement element;

	public AsnContextSpecific() {
	}

	public AsnContextSpecific(Bytes bytes) {
		super(bytes);
		tag = AsnUtils.parseTag(getIdentifierBytes());
		element = AsnUtils.parse(getContentBytes());
	}

	@XmlAttribute
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
