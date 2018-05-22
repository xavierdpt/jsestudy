package example.company.tox.asn;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import example.company.asn.elements.AsnInteger;
import example.company.tox.common.Tox;

public class AsnIntegerMarshaller {

	public static void marshal(Document document, Element root, String name, AsnInteger integer) {
		Tox.appendChild(document, root, name, integer.getValue());
	}

}
