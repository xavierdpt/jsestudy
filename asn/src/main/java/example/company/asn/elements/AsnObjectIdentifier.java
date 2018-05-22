package example.company.asn.elements;

import example.company.asn.utils.AsnObjectIdentifierUtils;
import example.company.tox.common.Bytes;

public class AsnObjectIdentifier extends AsnElement {

	private String value;

	public AsnObjectIdentifier() {
	}

	public AsnObjectIdentifier(Bytes bytes) {
		super(bytes);
		this.value = AsnObjectIdentifierUtils.parsePayload(contentBytes);

	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
