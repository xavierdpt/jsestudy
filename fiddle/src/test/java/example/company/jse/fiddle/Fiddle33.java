package example.company.jse.fiddle;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.ECPublicKey;

import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;

import example.company.jse.fiddle.tox.KPTOX;
import example.company.tox.common.Tox;

public class Fiddle33 {

	/* Dump key pairs to XML */

	@Test
	public void fiddle() throws NoSuchAlgorithmException {

		Document document = Tox.createDocument();

		rsa(document);
		dsa(document);
		dh(document);
		ec(document);

		Tox.print(document, System.out);
	}

	private void rsa(Document document) throws NoSuchAlgorithmException {

		KeyPairGenerator rsa = KeyPairGenerator.getInstance("RSA");

		KeyPair pair = rsa.generateKeyPair();
		KPTOX.rsa(document, pair);

	}

	private void dsa(Document document) throws NoSuchAlgorithmException {

		KeyPairGenerator dsa = KeyPairGenerator.getInstance("DSA");
		KeyPair pair = dsa.generateKeyPair();

		KPTOX.dsa(document, pair);

	}

	private void dh(Document document) throws NoSuchAlgorithmException {

		KeyPairGenerator dh = KeyPairGenerator.getInstance("DiffieHellman");
		KeyPair pair = dh.generateKeyPair();

		KPTOX.dh(document, pair);

	}

	private void ec(Document document) throws NoSuchAlgorithmException {

		KeyPairGenerator ec = KeyPairGenerator.getInstance("EC");
		KeyPair pair = ec.generateKeyPair();

		KPTOX.ec(document, pair);

		Assert.assertEquals("secp256r1 [NIST P-256, X9.62 prime256v1] (1.2.840.10045.3.1.7)",
				((ECPublicKey) (pair.getPublic())).getParams().toString());
	}

}
