package example.company.asn.elements;

import example.company.asn.utils.AsnObjectIdentifierUtils;
import example.company.tox.common.Bytes;

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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
