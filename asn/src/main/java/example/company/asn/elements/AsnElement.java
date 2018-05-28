package example.company.asn.elements;

import java.util.List;

import example.company.tox.common.NotSupportedException;

public class AsnElement {

	@SuppressWarnings("unchecked")
	public <T> T as(Class<T> clazz) {
		return (T) this;
	}

	public AsnSequence asSequence() {
		return (AsnSequence) this;
	}

	public AsnContextSpecific asContextSpecific() {
		return (AsnContextSpecific) this;
	}

	public AsnObjectIdentifier asObjectIdentifier() {
		return (AsnObjectIdentifier) this;
	}

	public AsnBitString asBitString() {
		return (AsnBitString) this;
	}

	public AsnOctetString asOctetString() {
		return (AsnOctetString) this;
	}

	public void encode(List<Byte> bytes) {
		throw new NotSupportedException(this.getClass().getName());
	}

}
