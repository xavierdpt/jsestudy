package example.company.tox.java.security;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class AnnotatedKeyPairDescription {

	private String provider;

	private KeyMarshaller privateKey;
	private KeyMarshaller publicKey;

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
	public KeyMarshaller getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(KeyMarshaller privateKey) {
		this.privateKey = privateKey;
	}

	@XmlElement(name = "publicKey")
	public KeyMarshaller getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(KeyMarshaller publicKey) {
		this.publicKey = publicKey;
	}

}
