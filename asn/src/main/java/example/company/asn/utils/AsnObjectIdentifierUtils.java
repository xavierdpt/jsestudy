package example.company.asn.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import example.company.tox.common.Bytes;

public class AsnObjectIdentifierUtils {

	private static Map<String, String> oidLabels = new HashMap<>();
	static {
		oidLabels.put("1.2.840.113549.1.1.11", "sha256WithRSAEncryption");
		oidLabels.put("1.2.840.113549.1.1.1", "rsaEncryption");
		oidLabels.put("2.5.29.14", "subjectKeyIdentifier");
		oidLabels.put("2.5.4.6", "countryName");
		oidLabels.put("2.5.4.8", "stateOrProvinceName");
		oidLabels.put("2.5.4.7", "localityName");
		oidLabels.put("2.5.4.10", "organizationName");
		oidLabels.put("2.5.4.11", "organizationalUnitName");
		oidLabels.put("2.5.4.3", "commonName");
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

	public static String getLabel(String value) {
		String label = oidLabels.get(value);
		if (label == null) {
			System.out.println("No label found for oid " + value);
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
