package example.company.asn.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import example.company.asn.elements.AsnBitString;
import example.company.asn.elements.AsnContextSpecific;
import example.company.asn.elements.AsnElement;
import example.company.asn.elements.AsnInteger;
import example.company.asn.elements.AsnObjectIdentifier;
import example.company.asn.elements.AsnPrintableString;
import example.company.asn.elements.AsnSequence;
import example.company.asn.elements.AsnSet;
import example.company.asn.elements.AsnUtcTime;

public class AsnX509Interpretation {

	private AsnElement tbsAsn;

	public AsnX509Interpretation(AsnElement tbsAsn) {
		this.tbsAsn = tbsAsn;
	}

	private static String foo(AsnSequence nameSeq) {

		Map<String, String> parts = new HashMap<>();

		nameSeq.getElements().forEach(issuerSetElement -> {
			AsnSequence seq = issuerSetElement.as(AsnSet.class).getElements().get(0).as(AsnSequence.class);
			String oid = seq.getElements().get(0).as(AsnObjectIdentifier.class).getValue();
			String value = seq.getElements().get(1).as(AsnPrintableString.class).getValue();
			parts.put(oid, value);
		});

		String c = parts.get(AsnObjectIdentifierUtils.COUNTRY_NAME_OID);
		String st = parts.get(AsnObjectIdentifierUtils.STATE_OR_PROVINCE_NAME_OID);
		String l = parts.get(AsnObjectIdentifierUtils.LOCALITY_NAME_OID);
		String o = parts.get(AsnObjectIdentifierUtils.ORGANIZATION_NAME_OID);
		String ou = parts.get(AsnObjectIdentifierUtils.ORGANIZATIONAL_UNIT_NAME_OID);
		String cncommonName = parts.get(AsnObjectIdentifierUtils.COMMON_NAME_OID);

		return "CN=" + cncommonName + ", OU=" + ou + ", O=" + o + ", L=" + l + ", ST=" + st + ", C=" + c;

	}

	public static String getIssuerName(AsnElement tbs) {

		AsnSequence issuerNameSequence = tbs.as(AsnSequence.class).getElements().get(3).as(AsnSequence.class);
		return foo(issuerNameSequence);

	}

	public static String getSubjectName(AsnElement tbs) {
		AsnSequence subjectNameSequence = tbs.as(AsnSequence.class).getElements().get(5).as(AsnSequence.class);
		return foo(subjectNameSequence);
	}

	public static Date getNotBefore(AsnElement tbs) {
		return tbs.as(AsnSequence.class).getElements().get(4).as(AsnSequence.class).getElements().get(0)
				.as(AsnUtcTime.class).toDate();
	}

	public static Date getNotAfter(AsnElement tbs) {
		return tbs.as(AsnSequence.class).getElements().get(4).as(AsnSequence.class).getElements().get(1)
				.as(AsnUtcTime.class).toDate();
	}

	public static String getSigAlgOID(AsnElement tbs) {
		return tbs.as(AsnSequence.class).getElements().get(2).as(AsnSequence.class).getElements().get(0)
				.as(AsnObjectIdentifier.class).getValue();
	}

	public static Object getSigAlgName(AsnElement tbs) {
		return AsnObjectIdentifierUtils.getLabel(getSigAlgOID(tbs));
	}

	public static int getVersion(AsnElement tbs) {
		return (int) (tbs.as(AsnSequence.class).getElements().get(0).as(AsnContextSpecific.class).getElement()
				.as(AsnInteger.class).getValue() + 1);
	}

	public static long getSerialNumber(AsnElement tbs) {
		return tbs.as(AsnSequence.class).getElements().get(1).as(AsnInteger.class).getValue();
	}

	public static byte[] getSignature(AsnElement asn) {
		return asn.as(AsnSequence.class).getElements().get(2).as(AsnBitString.class).toByteArray();
	}

	public AsnContextSpecific getContextSpecific(int i) {
		if (i == 3) {
			return tbsAsn.asSequence().getContextSpecific(7);
		}
		return null;
	}

	public AsnElement getExtensionBytes(String oid) {
		AsnSequence asnExts = getContextSpecific(3).getSequence();
		for (AsnElement element : asnExts) {
			AsnSequence seq = element.asSequence();
			if (oid.equals(seq.getObjectIdentifier(0).getValue())) {
				return seq.get(1);
			}
		}
		return null;
	}

	public boolean[] getKeyUsage() {

		boolean[] keyUsage = new boolean[9];

		byte[] extensionBytes = getExtensionBytes(AsnObjectIdentifierUtils.KEY_USAGE_OID).asOctetString().getValue();
		AsnBitString bits = AsnUtils.parse(extensionBytes).asBitString();

		for (int i = 0; i < keyUsage.length; ++i) {
			keyUsage[i] = bits.get(i);
		}

		return keyUsage;
	}

	public int getBasicConstraints() {

		byte[] extensionBytes = getExtensionBytes(AsnObjectIdentifierUtils.BASIC_CONSTRAINTS_OID).asOctetString()
				.getValue();
		AsnSequence seq = AsnUtils.parse(extensionBytes).asSequence();
		boolean set = seq.getBoolean(0).getValue();

		if (set) {
			return Integer.MAX_VALUE;
		} else {
			return -1;
		}
	}

	public boolean isKeyCertSign() {
		return getKeyUsage()[5];
	}

}
