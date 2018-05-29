package example.company.asn.elements;

public class Asn {

	public static AsnSequence seq(AsnElement... elements) {
		AsnSequence seq = new AsnSequence();
		for (AsnElement element : elements) {
			seq.getElements().add(element);
		}
		return seq;
	}

	public static AsnSet set(AsnElement... elements) {
		AsnSet set = new AsnSet();
		for (AsnElement element : elements) {
			set.getElements().add(element);
		}
		return set;
	}

	public static AsnContextSpecific contextSpecific(int tag, byte[] value) {
		return new AsnContextSpecific(tag, value);
	}

	public static AsnElement contextSpecific(int tag, AsnElement element) {
		return contextSpecific(tag, element.encode());
	}

	public static AsnInteger integer(int value) {
		return new AsnInteger(value);
	}

	public static AsnObjectIdentifier oid(String oid) {
		return new AsnObjectIdentifier(oid);
	}

	public static AsnNull n() {
		return new AsnNull();
	}

	public static AsnPrintableString str(String value) {
		return new AsnPrintableString(value);
	}

	public static AsnUtcTime time(String value) {
		return new AsnUtcTime(value);
	}

	public static AsnBitString bitstring(byte[] bytes) {
		return new AsnBitString(bytes);
	}

	public static AsnOctetString octetString(byte[] bytes) {
		return new AsnOctetString(bytes);
	}

}
