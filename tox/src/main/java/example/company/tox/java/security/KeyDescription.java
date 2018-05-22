package example.company.tox.java.security;

import java.security.Key;

import javax.xml.bind.annotation.XmlType;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import example.company.tox.common.Tox;
import example.company.tox.java.lang.ExceptionDescription;
import example.company.tox.proxy.AsnElementMarshaller;

@XmlType(propOrder = { "algorithm", "format", "encoded", "asnElement" })
public class KeyDescription {

	public KeyDescription() {
	}

	public KeyDescription(Key key) {
		// TODO Auto-generated constructor stub
	}

	public static void marshal(Document document, Element root, String name, Key key) {
		Element element = Tox.appendChild(document, root, name);
		Tox.setAttribute(element, "algorithm", key.getAlgorithm());
		Tox.setAttribute(element, "format", key.getFormat());
		byte[] encoded = key.getEncoded();
		Tox.appendChild(document, element, "encoded", encoded);
		if (encoded != null) {
			try {
				AsnElementMarshaller.marshal(document, root, "asn", encoded);

			} catch (Exception e) {
				ExceptionDescription.marshal(document, root, "asnParseException", e);
			}
		}
	}

}
