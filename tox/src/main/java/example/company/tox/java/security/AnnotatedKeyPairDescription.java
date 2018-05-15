package example.company.tox.java.security;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class AnnotatedKeyPairDescription {

	private String provider;

	private KeyDescription privateKey;
	private KeyDescription publicKey;

	public AnnotatedKeyPairDescription() {
	}

	public AnnotatedKeyPairDescription(String provider, KeyPairDescription keyPair) {
		this.provider = provider;
		privateKey = keyPair.getPrivateKey();
		publicKey = keyPair.getPublicKey();
	}

	@XmlAttribute(name="provider")
	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	@XmlElement(name = "privateKey")
	public KeyDescription getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(KeyDescription privateKey) {
		this.privateKey = privateKey;
	}

	@XmlElement(name = "publicKey")
	public KeyDescription getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(KeyDescription publicKey) {
		this.publicKey = publicKey;
	}

}
