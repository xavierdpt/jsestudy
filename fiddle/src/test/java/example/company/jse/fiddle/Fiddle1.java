package example.company.jse.fiddle;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;

import org.junit.Test;
import org.w3c.dom.Document;

import example.company.tox.common.Tox;
import example.company.tox.java.security.cert.CertificateMarshaller;

public class Fiddle1 {

	/**
	 * Dump information about the self-signed certificate stored in keystore1
	 */
	@Test
	public void fiddle() throws KeyStoreException, NoSuchAlgorithmException, CertificateException,
			FileNotFoundException, IOException, JAXBException, XMLStreamException {

		KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
		InputStream resourceAsStream = this.getClass().getResourceAsStream("/keystore1.jks");
		keystore.load(resourceAsStream, "password".toCharArray());
		Certificate certificate = keystore.getCertificate("selfsigned");
		Document document = Tox.createDocument();
		CertificateMarshaller.marshall(document, null, "certificate", certificate);
		Tox.toXML(document, System.out);

	}

}
