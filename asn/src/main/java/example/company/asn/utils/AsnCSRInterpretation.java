package example.company.asn.utils;

import example.company.asn.elements.AsnElement;

public class AsnCSRInterpretation {

	private AsnElement asn;

	public AsnCSRInterpretation(AsnElement asn) {
		this.asn = asn;
	}

	public long getVersion() {
		return asn.asSequence().getSequence(0).getInteger(0).getValue().longValue();
	}

	public String getSubjectName() {
		return AsnInterpretationUtils.mapToNameString(asn.asSequence().getSequence(0).getSequence(1));
	}

	public KeyInfo getPublicKeyInfo() {
		return AsnInterpretationUtils.mapToKeyInfo(asn.asSequence().getSequence(0).getSequence(2));
	}

	public AsnElement getAdditionalInfo() {
		return asn.asSequence().getSequence(0).get(3);
	}

	public String getSignatureOID() {
		return asn.asSequence().getSequence(1).getObjectIdentifier(0).getValue();
	}

	public byte[] getSignature() {
		return asn.asSequence().getBitString(2).toByteArray();
	}

}
