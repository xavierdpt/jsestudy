package example.company.asn.elements;

import java.nio.charset.Charset;
import java.util.List;

import example.company.asn.AsnClass;
import example.company.asn.AsnEncoding;
import example.company.asn.AsnTag;
import example.company.asn.utils.AsnUtils;
import example.company.tox.common.Bytes;

public class AsnBMPString extends AsnElement {

	private String value;

	public AsnBMPString(Bytes identifierBytes, Bytes lengthBytes, Bytes contentBytes) {
		value = new String(contentBytes.toByteArray(), Charset.forName("UTF-16BE"));
	}

	public AsnBMPString(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public void encode(List<Byte> bytes) {
		AsnUtils.addIdentifierBytes(bytes, AsnClass.UNIVERSAL, AsnEncoding.PRIMITIVE, AsnTag.BMP_STRING);
		byte[] valueBytes = value.getBytes(Charset.forName("UTF-16BE"));
		AsnUtils.addLengthBytes(bytes, valueBytes.length);
		AsnUtils.addBytes(bytes, valueBytes);
	}
}
