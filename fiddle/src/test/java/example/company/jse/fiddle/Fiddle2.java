package example.company.jse.fiddle;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;

import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;

import example.company.tox.common.Tox;
import example.company.tox.java.security.KeyMarshaller;
import example.company.tox.java.security.cert.CertificateMarshaller;

public class Fiddle2 {

	/**
	 * Dump information about the private key stored in keystore1
	 * 
	 * @throws UnrecoverableKeyException
	 */
	@Test
	public void fiddle() throws KeyStoreException, NoSuchAlgorithmException, CertificateException,
			FileNotFoundException, IOException, JAXBException, XMLStreamException, UnrecoverableKeyException {

		KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
		InputStream resourceAsStream = this.getClass().getResourceAsStream("/keystore1.jks");
		keystore.load(resourceAsStream, "password".toCharArray());
		Key key = keystore.getKey("selfsigned", "password".toCharArray());

		Document document = Tox.createDocument();
		KeyMarshaller.marshal(document, null, "key", key);
		Tox.marshall(document, System.out);

	}

}
