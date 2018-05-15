package example.company.tox.java.security;

import java.security.Key;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlType(propOrder = { "algorithm", "format", "encoded" })
public class KeyDescription {

	private String algorithm;
	private String format;
	private byte[] encoded;

	public KeyDescription() {
	}

	public KeyDescription(Key key) {
		algorithm = key.getAlgorithm();
		format = key.getFormat();
		encoded = key.getEncoded();
	}

	@XmlAttribute
	public String getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

	@XmlAttribute
	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	@XmlValue
	@XmlJavaTypeAdapter(value = HexBinaryAdapter.class)
	public byte[] getEncoded() {
		return encoded;
	}

	public void setEncoded(byte[] encoded) {
		this.encoded = encoded;
	}

}
