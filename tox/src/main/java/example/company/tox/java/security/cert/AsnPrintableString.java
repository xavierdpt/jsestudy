package example.company.tox.java.security.cert;

import java.nio.charset.Charset;

import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlValue;

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
