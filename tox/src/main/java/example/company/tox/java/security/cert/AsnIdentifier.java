package example.company.tox.java.security.cert;

import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

public class AsnIdentifier {

	@XmlTransient
	private byte[] encoded;

	@XmlTransient
	private int index;

	private AsnClass asnClass;
	private AsnEncoding encoding;

	private int tagNumber;
	private boolean longIdentifier;
	private AsnTag tag;

	private boolean longLength;

	private int length;

	private byte[] lengthBytes;

	private int totalNumberOfBytes;

	private Integer integerValue;

	public AsnIdentifier(byte[] encoded, int index) {

		this.encoded = encoded;
		this.index = index;

		asnClass = AsnClass.fromByte(encoded[index]);
		encoding = AsnEncoding.fromByte(encoded[index]);
		tagNumber = encoded[index] & 0x1F;
		longIdentifier = tagNumber == 0x1F;

		tag = AsnTag.fromTagNumber(tagNumber);

		int lengthOffset = 1;

		byte lengthByte = encoded[index + lengthOffset];
		longLength = (lengthByte & 0x80) == 0x80;
		length = lengthByte & 0x7f;

		totalNumberOfBytes = 0;

		if (length != 0 && longLength) {
			lengthBytes = new byte[length];
			for (int i = 0; i < length; ++i) {
				byte b = encoded[index + lengthOffset + 1 + i];
				lengthBytes[i] = b;
				totalNumberOfBytes = (totalNumberOfBytes << 8) + (b & 0xFF);
			}
		} else {
			totalNumberOfBytes = length;
		}
		if (tag == AsnTag.INTEGER) {
			int value = 0;
			for (int i = 0; i < totalNumberOfBytes; ++i) {
				value = (value << 8) + (encoded[index + 2 + i] & 0xFF);
			}
			integerValue = value;
		}

	}

	public AsnClass getAsnClass() {
		return asnClass;
	}

	public void setAsnClass(AsnClass asnClass) {
		this.asnClass = asnClass;
	}

	public AsnEncoding getEncoding() {
		return encoding;
	}

	public void setEncoding(AsnEncoding encoding) {
		this.encoding = encoding;
	}

	public int getTagNumber() {
		return tagNumber;
	}

	public void setTagNumber(int tagNumber) {
		this.tagNumber = tagNumber;
	}

	public boolean isLongIdentifier() {
		return longIdentifier;
	}

	public void setLongIdentifier(boolean longIdentifier) {
		this.longIdentifier = longIdentifier;
	}

	public AsnTag getTag() {
		return tag;
	}

	public void setTag(AsnTag tag) {
		this.tag = tag;
	}

	public boolean isLongLength() {
		return longLength;
	}

	public void setLongLength(boolean longLength) {
		this.longLength = longLength;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getTotalNumberOfBytes() {
		return totalNumberOfBytes;
	}

	public void setTotalNumberOfBytes(int totalNumberOfBytes) {
		this.totalNumberOfBytes = totalNumberOfBytes;
	}

	@XmlJavaTypeAdapter(value = HexBinaryAdapter.class)
	public byte[] getLengthBytes() {
		return lengthBytes;
	}

	public void setLengthBytes(byte[] lengthBytes) {
		this.lengthBytes = lengthBytes;
	}

	public Integer getIntegerValue() {
		return integerValue;
	}

	public void setIntegerValue(Integer integerValue) {
		this.integerValue = integerValue;
	}

}
