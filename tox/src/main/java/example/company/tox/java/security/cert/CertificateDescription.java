package example.company.tox.java.security.cert;

import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import example.company.tox.java.lang.ExceptionDescription;
import example.company.tox.java.security.KeyDescription;

@XmlRootElement(name = "certificate")
@XmlType(propOrder = { "type", "publicKey", "encoded", "encodedException", "asnElement" })
public class CertificateDescription {

	private String type;
	private KeyDescription publicKey;
	private byte[] encoded;
	private ExceptionDescription encodedException;
	private AsnElement asnElement;

	public CertificateDescription() {
	}

	public CertificateDescription(Certificate certificate) {
		type = certificate.getType();
		publicKey = new KeyDescription(certificate.getPublicKey());
		try {
			encoded = certificate.getEncoded();
			asnElement = AsnUtils.parse(new Bytes(encoded));
		} catch (CertificateEncodingException e) {
			encodedException = new ExceptionDescription(e);
		}

	}

	@XmlAttribute
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@XmlElement(name = "publicKey")
	public KeyDescription getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(KeyDescription publicKey) {
		this.publicKey = publicKey;
	}

	@XmlJavaTypeAdapter(value = HexBinaryAdapter.class)
	public byte[] getEncoded() {
		return encoded;
	}

	public void setEncoded(byte[] encoded) {
		this.encoded = encoded;
	}

	public ExceptionDescription getEncodedException() {
		return encodedException;
	}

	public void setEncodedException(ExceptionDescription encodedException) {
		this.encodedException = encodedException;
	}

	public AsnElement getAsnElement() {
		return asnElement;
	}

	public void setAsnElement(AsnElement asnElement) {
		this.asnElement = asnElement;
	}

}
