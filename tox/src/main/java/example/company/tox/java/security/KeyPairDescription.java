package example.company.tox.java.security;

import java.security.KeyPair;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "keyPair")
@XmlType(propOrder = { "privateKey", "publicKey" })
public class KeyPairDescription {

	private KeyMarshaller privateKey;
	private KeyMarshaller publicKey;

	public KeyPairDescription() {
	}

	public KeyPairDescription(KeyPair keypair) {
		privateKey = new KeyMarshaller(keypair.getPrivate());
		publicKey = new KeyMarshaller(keypair.getPublic());
	}

	public KeyMarshaller getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(KeyMarshaller privateKey) {
		this.privateKey = privateKey;
	}

	public KeyMarshaller getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(KeyMarshaller publicKey) {
		this.publicKey = publicKey;
	}

}
