package example.company.asn.elements;

import java.nio.charset.Charset;
import java.util.List;

import example.company.asn.AsnClass;
import example.company.asn.AsnEncoding;
import example.company.asn.AsnTag;
import example.company.asn.utils.AsnUtils;
import example.company.tox.common.Bytes;

public class AsnPrintableString extends AsnElement {

	private String value;

	public AsnPrintableString() {
	}

	public AsnPrintableString(Bytes bytes) {
		super(bytes);
		value = new String(contentBytes.toByteArray(), Charset.forName("UTF-8"));
	}

	public AsnPrintableString(String value) {
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
		AsnUtils.addIdentifierBytes(bytes, AsnClass.UNIVERSAL, AsnEncoding.PRIMITIVE, AsnTag.PRINTABLE_STRING);
		AsnUtils.addLengthBytes(bytes, value.length());
		AsnUtils.addBytes(bytes, value.getBytes());
	}

}