package example.company.tox.java.security.cert;

import javax.xml.bind.annotation.XmlAttribute;

public class AsnObjectIdentifier extends AsnElement {

	private String value;
	private String name;

	public AsnObjectIdentifier() {
	}

	public AsnObjectIdentifier(Bytes bytes) {
		super(bytes);
		this.value = AsnObjectIdentifierUtils.parsePayload(contentBytes);
		this.name = AsnObjectIdentifierUtils.getLabel(value);

	}

	@XmlAttribute
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@XmlAttribute
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	protected boolean isGood() {
		return true;
	}

}
