package example.company.tox.java.security.cert;

import java.io.StringWriter;
import java.nio.charset.Charset;

import javax.xml.bind.annotation.XmlAttribute;

import example.company.tox.common.Bytes;

public class AsnUtcTime extends AsnElement {

	private String value;

	public AsnUtcTime() {
	}

	public AsnUtcTime(Bytes bytes) {
		super(bytes);

		String rawvalue = new String(contentBytes.toByteArray(), Charset.forName("UTF-8"));

		StringWriter sb = new StringWriter();

		int c = 0;
		sb.append(rawvalue.substring(c, c + 2));
		c += 2;
		sb.append("/");

		sb.append(rawvalue.substring(c, c + 2));
		c += 2;
		sb.append("/");

		sb.append(rawvalue.substring(c, c + 2));
		c += 2;
		sb.append(" ");

		sb.append(rawvalue.substring(c, c + 2));
		c += 2;
		sb.append(":");

		sb.append(rawvalue.substring(c, c + 2));
		c += 2;
		sb.append(":");

		sb.append(rawvalue.substring(c, c + 2));
		c+=2;
		sb.append(" ");
		
		sb.append(rawvalue.substring(c, c + 1));
		
		value=sb.toString();

	}

	@XmlAttribute
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
