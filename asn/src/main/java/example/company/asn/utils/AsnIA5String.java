package example.company.asn.utils;

import java.nio.charset.Charset;
import java.util.List;

import example.company.asn.AsnClass;
import example.company.asn.AsnEncoding;
import example.company.asn.AsnTag;
import example.company.asn.elements.AsnElement;
import example.company.tox.common.Bytes;

public class AsnIA5String extends AsnElement {

	private String value;

	public AsnIA5String(String value) {
		this.value = value;
	}

	public AsnIA5String(Bytes identifierBytes, Bytes lengthBytes, Bytes contentBytes) {
		value = new String(contentBytes.toByteArray(), Charset.forName("UTF8"));
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public void encode(List<Byte> bytes) {
		AsnUtils.addIdentifierBytes(bytes, AsnClass.UNIVERSAL, AsnEncoding.PRIMITIVE, AsnTag.IA5STRING);
		AsnUtils.addLengthBytes(bytes, value.length());
		AsnUtils.addBytes(bytes, value.getBytes());
	}

}
