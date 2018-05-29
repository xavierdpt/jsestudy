package example.company.asn.utils;

import java.util.HashMap;
import java.util.Map;

public class OIDS {

	public static final String RSA = "1.2.840.113549.1.1.1";
	public static final String SHA256RSA = "1.2.840.113549.1.1.11";
	public static final String EXTENSION_REQUEST = "1.2.840.113549.1.9.14";
	public static final String CLIENT_AUTH = "1.3.6.1.5.5.7.3.2";

	public static final String COMMON_NAME = "2.5.4.3";
	public static final String COUNTRY_NAME = "2.5.4.6";
	public static final String LOCALITY_NAME = "2.5.4.7";
	public static final String STATE_OR_PROVINCE_NAME = "2.5.4.8";
	public static final String ORGANIZATION_NAME = "2.5.4.10";
	public static final String ORGANIZATIONAL_UNIT_NAME = "2.5.4.11";
	public static final String SUBJECT_KEY_IDENTIFIER = "2.5.29.14";
	public static final String KEY_USAGE = "2.5.29.15";
	public static final String BASIC_CONSTRAINTS = "2.5.29.19";
	public static final String AUTHORITY_KEY_IDENTIFIER = "2.5.29.35";
	public static final String EXT_KEY_USAGE = "2.5.29.37";

	private static Map<String, String> oidLabels = new HashMap<>();
	static {
		oidLabels.put(OIDS.SHA256RSA, "SHA256withRSA");
		oidLabels.put(OIDS.RSA, "RSA");
		oidLabels.put(OIDS.SUBJECT_KEY_IDENTIFIER, "subjectKeyIdentifier");
		oidLabels.put(OIDS.COUNTRY_NAME, "countryName");
		oidLabels.put(OIDS.STATE_OR_PROVINCE_NAME, "stateOrProvinceName");
		oidLabels.put(OIDS.LOCALITY_NAME, "localityName");
		oidLabels.put(OIDS.ORGANIZATION_NAME, "organizationName");
		oidLabels.put(OIDS.ORGANIZATIONAL_UNIT_NAME, "organizationalUnitName");
		oidLabels.put(OIDS.COMMON_NAME, "commonName");
		oidLabels.put(OIDS.SUBJECT_KEY_IDENTIFIER, "subjectKeyIdentifier");
		oidLabels.put(OIDS.KEY_USAGE, "keyUsage");
		oidLabels.put(OIDS.BASIC_CONSTRAINTS, "basicConstraints");
		oidLabels.put(OIDS.EXTENSION_REQUEST, "extensionRequest");
		oidLabels.put(OIDS.AUTHORITY_KEY_IDENTIFIER, "authorityKeyIdentifier");
		oidLabels.put(OIDS.EXT_KEY_USAGE, "extKeyUsage");
		oidLabels.put(OIDS.CLIENT_AUTH, "clientAuth");
	}

	public static String getLabel(String oid) {
		String label = oidLabels.get(oid);
		if (label == null) {
			System.out.println("No label found for oid " + oid);
			return oid;
		}
		return label;
	}

}
