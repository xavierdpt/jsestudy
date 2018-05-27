package example.company.asn.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import example.company.tox.common.Bytes;

public class AsnObjectIdentifierUtils {

	public static final String COUNTRY_NAME_OID = "2.5.4.6";
	public static final String STATE_OR_PROVINCE_NAME_OID = "2.5.4.8";
	public static final String LOCALITY_NAME_OID = "2.5.4.7";
	public static final String ORGANIZATION_NAME_OID = "2.5.4.10";
	public static final String ORGANIZATIONAL_UNIT_NAME_OID = "2.5.4.11";
	public static final String COMMON_NAME_OID = "2.5.4.3";
	public static final String KEY_USAGE_OID = "2.5.29.15";
	public static final String BASIC_CONSTRAINTS_OID = "2.5.29.19";

	private static Map<String, String> oidLabels = new HashMap<>();
	static {
		oidLabels.put("1.2.840.113549.1.1.11", "SHA256withRSA");
		oidLabels.put("1.2.840.113549.1.1.1", "rsaEncryption");
		oidLabels.put("2.5.29.14", "subjectKeyIdentifier");
		oidLabels.put(COUNTRY_NAME_OID, "countryName");
		oidLabels.put(STATE_OR_PROVINCE_NAME_OID, "stateOrProvinceName");
		oidLabels.put(LOCALITY_NAME_OID, "localityName");
		oidLabels.put(ORGANIZATION_NAME_OID, "organizationName");
		oidLabels.put(ORGANIZATIONAL_UNIT_NAME_OID, "organizationalUnitName");
		oidLabels.put(COMMON_NAME_OID, "commonName");
		oidLabels.put("2.5.29.14", "subjectKeyIdentifier");
		oidLabels.put(KEY_USAGE_OID, "keyUsage");
		oidLabels.put(BASIC_CONSTRAINTS_OID, "basicConstraints");
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
