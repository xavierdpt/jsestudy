package example.company.tox.java.security;

import java.security.KeyPair;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "keyPair")
@XmlType(propOrder = { "privateKey", "publicKey" })
public class KeyPairDescription {

	private KeyDescription privateKey;
	private KeyDescription publicKey;

	public KeyPairDescription() {
	}

	public KeyPairDescription(KeyPair keypair) {
		privateKey = new KeyDescription(keypair.getPrivate());
		publicKey = new KeyDescription(keypair.getPublic());
	}

	public KeyDescription getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(KeyDescription privateKey) {
		this.privateKey = privateKey;
	}

	public KeyDescription getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(KeyDescription publicKey) {
		this.publicKey = publicKey;
	}

}
