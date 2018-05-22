package example.company.asn;

public enum AsnTag {
	INTEGER(0x2), BIT_STRING(0x3), OCTET_STRING(0x4), NULL(0x5), OBJECT_IDENTIFIER(0x6), SEQUENCE(0x10), SET(
			0x11), PRINTABLE_STRING(0x13), UTC_TIME(0x17),

	SUBJECT_IDENTIFIER(0xD);

	private int tagNumber;

	private AsnTag(int tagNumber) {
		this.tagNumber = tagNumber;
	}

	public int getTagNumber() {
		return tagNumber;
	}

	public static AsnTag fromTagNumber(int tagNumber) {
		AsnTag[] candidates = values();
		for (int i = 0; i < candidates.length; ++i) {
			AsnTag candidate = candidates[i];
			if (candidate.getTagNumber() == tagNumber) {
				return candidate;
			}
		}
		return null;
	}

}
