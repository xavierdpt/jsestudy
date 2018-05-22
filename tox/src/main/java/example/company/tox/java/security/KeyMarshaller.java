package example.company.tox.java.security;

import java.security.Key;

import javax.xml.bind.annotation.XmlType;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import example.company.tox.common.Tox;
import example.company.tox.proxy.AsnElementMarshaller;

@XmlType(propOrder = { "algorithm", "format", "encoded", "asnElement" })
public class KeyMarshaller {

	public KeyMarshaller() {
	}

	public KeyMarshaller(Key key) {
		// TODO Auto-generated constructor stub
	}

	public static void marshal(Document document, Element root, String name, Key key) {
		Element element = Tox.appendChild(document, root, name);
		Tox.setAttribute(element, "algorithm", key.getAlgorithm());
		Tox.setAttribute(element, "format", key.getFormat());
		byte[] encoded = key.getEncoded();
		Tox.appendChild(document, element, "encoded", encoded);
		if (encoded != null) {
			AsnElementMarshaller.marshal(document, element, "asn", encoded);
		}
	}

}
