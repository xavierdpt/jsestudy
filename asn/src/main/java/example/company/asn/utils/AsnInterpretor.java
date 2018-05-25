package example.company.asn.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;

import example.company.asn.elements.AsnBitString;
import example.company.asn.elements.AsnContextSpecific;
import example.company.asn.elements.AsnElement;
import example.company.asn.elements.AsnInteger;
import example.company.asn.elements.AsnObjectIdentifier;
import example.company.asn.elements.AsnOctetString;
import example.company.asn.elements.AsnPrintableString;
import example.company.asn.elements.AsnSequence;
import example.company.asn.elements.AsnSet;
import example.company.asn.elements.AsnUtcTime;

public class AsnInterpretor {

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

	public static AsnContextSpecific getContextSpecific(AsnElement tbsAsn, int i) {
		if (i == 3) {
			return tbsAsn.asSequence().getContextSpecific(7);
		}
		return null;
	}

	public static boolean isKeyCertSign(AsnElement tbsAsn) {

		AsnSequence asnExts = getContextSpecific(tbsAsn, 3).getSequence();

		AsnObjectIdentifierUtils.class.getName();

		for (AsnElement element : asnExts) {
			AsnSequence seq = element.asSequence();
			if (AsnObjectIdentifierUtils.KEY_USAGE_OID.equals(seq.getObjectIdentifier(0).getValue())) {
				AsnElement bits = AsnUtils.parse(seq.getOctetString(1).getValue()).asBitString();
				return bits.as(AsnBitString.class).get(5);
			}
		}

		return false;
	}

}
