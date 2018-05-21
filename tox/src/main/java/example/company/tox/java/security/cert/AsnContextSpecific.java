package example.company.tox.java.security.cert;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

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

	@XmlAttribute
	public long getTag() {
		return tag;
	}

	public void setTag(long tag) {
		this.tag = tag;
	}

	@XmlElement(name="contextSpecificContent")
	public AsnElement getElement() {
		return element;
	}

	public void setElement(AsnElement element) {
		this.element = element;
	}

	@Override
	protected boolean isGood() {
		return true;
	}
	
	
}
