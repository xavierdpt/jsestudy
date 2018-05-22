package example.company.asn.elements;

import java.nio.charset.Charset;

import example.company.tox.common.Bytes;

public class AsnPrintableString extends AsnElement {

	private String value;

	public AsnPrintableString() {
	}

	public AsnPrintableString(Bytes bytes) {
		super(bytes);
		value = new String(contentBytes.toByteArray(), Charset.forName("UTF-8"));
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
