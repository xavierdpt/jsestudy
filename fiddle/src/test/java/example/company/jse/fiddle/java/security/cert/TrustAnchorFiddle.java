package example.company.jse.fiddle.java.security.cert;

import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.cert.TrustAnchor;

import org.junit.Test;

import example.company.jse.something.java.security.KeyPairGeneratorSomething;

public class TrustAnchorFiddle {

	@Test
	public void fiddle1() throws NoSuchAlgorithmException {
		String caName = "caName";
		PublicKey pubKey = KeyPairGeneratorSomething.createKeyPair().getPublic();
		byte[] nameConstraints = new byte[] {};
		TrustAnchor trustAnchor = new TrustAnchor(caName, pubKey, nameConstraints);
	}
}
