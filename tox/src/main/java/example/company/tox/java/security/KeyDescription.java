package example.company.tox.java.security;

import java.security.Key;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import example.company.tox.common.Bytes;
import example.company.tox.java.security.cert.AsnElement;
import example.company.tox.java.security.cert.AsnUtils;

@XmlType(propOrder = { "algorithm", "format", "encoded", "asnElement" })
public class KeyDescription {

	private String algorithm;
	private String format;
	private byte[] encoded;
	private AsnElement asnElement;

	public KeyDescription() {
	}

	public KeyDescription(Key key) {
		algorithm = key.getAlgorithm();
		format = key.getFormat();
		encoded = key.getEncoded();
		asnElement = AsnUtils.parse(new Bytes(encoded));
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

	@XmlJavaTypeAdapter(value = HexBinaryAdapter.class)
	public byte[] getEncoded() {
		return encoded;
	}

	public void setEncoded(byte[] encoded) {
		this.encoded = encoded;
	}

	public AsnElement getAsnElement() {
		return asnElement;
	}

	public void setAsnElement(AsnElement asnElement) {
		this.asnElement = asnElement;
	}

}
