package example.company.asn.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import example.company.tox.common.Bytes;

public class AsnObjectIdentifierUtils {

	private static Map<String, String> oidLabels = new HashMap<>();
	static {
		oidLabels.put(OIDS.SHA256RSA_OID, "SHA256withRSA");
		oidLabels.put("1.2.840.113549.1.1.1", "RSA");
		oidLabels.put("2.5.29.14", "subjectKeyIdentifier");
		oidLabels.put(OIDS.COUNTRY_NAME_OID, "countryName");
		oidLabels.put(OIDS.STATE_OR_PROVINCE_NAME_OID, "stateOrProvinceName");
		oidLabels.put(OIDS.LOCALITY_NAME_OID, "localityName");
		oidLabels.put(OIDS.ORGANIZATION_NAME_OID, "organizationName");
		oidLabels.put(OIDS.ORGANIZATIONAL_UNIT_NAME_OID, "organizationalUnitName");
		oidLabels.put(OIDS.COMMON_NAME_OID, "commonName");
		oidLabels.put(OIDS.SUBJECT_KEY_IDENTIFIER_OID, "subjectKeyIdentifier");
		oidLabels.put(OIDS.KEY_USAGE_OID, "keyUsage");
		oidLabels.put(OIDS.BASIC_CONSTRAINTS_OID, "basicConstraints");
		oidLabels.put(OIDS.EXTENSION_REQUEST_OID, "extensionRequest");
	}

	public static String parsePayload(Bytes payload) {

		List<Long> values = new ArrayList<>();

		long value = 0;
		for (int i = 0; i < payload.length(); ++i) {
			byte b = payload.at(i);
			value = (value << 7) + (b & 0x7F);
			if ((b & 0x80) != 0x80) {
				values.add(value);
				value = 0;
			}
		}

		StringBuilder b = new StringBuilder();
		boolean first = true;
		for (Long v : values) {
			String p = "";
			if (first) {
				if (v <= 39) {
					p = "0";
				} else if (v <= 79) {
					p = "1";
					v -= 40;
				} else {
					p = "2";
					v -= 80;
				}
			}
			b.append(p);
			b.append(".");
			b.append(v);
			first = false;
		}

		return b.toString();

	}

	public static String getLabel(String oid) {
		String label = oidLabels.get(oid);
		if (label == null) {
			System.out.println("No label found for oid " + oid);
			return oid;
		}
		return label;
	}

	public static void encodeSubIdentifier(List<Byte> bytes, int value) {
		List<Integer> parts = new ArrayList<>();
		while (value > 0x7F) {
			parts.add((value & 0x7F));
			value >>= 7;
		}
		parts.add(value);
		for (int i = parts.size() - 1; i >= 0; --i) {
			bytes.add((byte) (parts.get(i) + (i == 0 ? 0 : 0x80)));
		}
	}
}
