package example.company.tox.java.security.cert;

public class AsnUtils {

	public static Bytes getIdentifierBytes(Bytes bytes) {
		int tag = bytes.at(0) & 0x1F;
		int count = 1;
		if (tag == 0x1F) {
			while ((bytes.at(count) & 0x80) == 0x80) {
				++count;
			}
		}
		return new Bytes(bytes, 0, count);
	}

	public static Bytes getLengthBytes(Bytes bytes) {
		int size = 1;
		boolean highByteSet = (bytes.at(0) & 0x80) == 0x80;
		if (highByteSet) {
			size = 1 + bytes.at(0) & 0x7F;
		}
		return new Bytes(bytes, 0, size);
	}

	public static long parseTag(Bytes idBytes) {
		if (idBytes.length() == 1) {
			return idBytes.at(0) & 0x1F;
		} else {
			long tag = 0;
			for (int i = 1; i < idBytes.length(); ++i) {
				tag = (tag << 8) + (idBytes.at(i) & 0x7F);

			}
			return tag;
		}
	}

	public static int parseLengthBytes(Bytes lBytes) {
		if (lBytes.length() == 1) {
			return lBytes.at(0) & 0x7F;
		} else {
			long length = 0;
			for (int i = 1; i < lBytes.length(); ++i) {
				length = (length << 8) + (lBytes.at(i) & 0xFF);
			}
			return (int) length;
		}
	}

	public static AsnEncoding parseEncoding(Bytes idBytes) {
		return (idBytes.at(0) & 0x20) == 20 ? AsnEncoding.CONSTRUCTED : AsnEncoding.PRIMITIVE;
	}

	public static AsnClass parseClass(Bytes identifierBytes) {
		return AsnClass.fromByte(identifierBytes.at(0));
	}

	public static Bytes getContentBytes(Bytes identifierBytes, Bytes lengthBytes, Bytes allBytes) {
		long length = parseLengthBytes(lengthBytes);
		if (length == 0) {
			throw new IllegalArgumentException("Elements with indefinite length not supproted");
		}
		int headerSize = identifierBytes.length() + lengthBytes.length();
		return new Bytes(allBytes, headerSize, (int) length);
	}

	public static AsnElement parse(Bytes bytes) {
		Bytes idBytes = getIdentifierBytes(bytes);
		AsnClass asnClass = parseClass(idBytes);
		long tag = parseTag(idBytes);
		if (asnClass == AsnClass.UNIVERSAL) {
			if (tag == AsnTag.SEQUENCE.getTagNumber()) {
				return new AsnSequence(bytes);
			} else if(tag == AsnTag.INTEGER.getTagNumber()) {
				return new AsnInteger(bytes);
			} else if(tag==AsnTag.OBJECT_IDENTIFIER.getTagNumber()) {
				return new AsnObjectIdentifier(bytes);
			} else if(tag==AsnTag.NULL.getTagNumber()) {
				return new AsnNull(bytes);
			} else if(tag==AsnTag.SET.getTagNumber()) {
				return new AsnSet(bytes);
			} else if(tag==AsnTag.PRINTABLE_STRING.getTagNumber()) {
				return new AsnPrintableString(bytes);
			} else {
				System.out.println("Warning : unknown " + asnClass + " tag " + tag);
				return new AsnElement(bytes);
			}
		} else if(asnClass==AsnClass.CONTEXT_SPECIFIC) {
			return new AsnContextSpecific(bytes);
		} else {
			System.out.println("Warning : unknown " + asnClass + " tag " + tag);
		}
		return null;
	}
}
