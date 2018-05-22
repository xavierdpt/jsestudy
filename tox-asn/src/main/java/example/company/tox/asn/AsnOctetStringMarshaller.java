package example.company.tox.asn;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import example.company.asn.elements.AsnOctetString;
import example.company.tox.common.Tox;

public class AsnOctetStringMarshaller {

	public static void marshal(Document document, Element root, String name, AsnOctetString octetString) {
		Element element = Tox.appendChild(document, root, name);
		Tox.appendChild(document, element, "rawBytes", octetString.getRawBytes());
		AsnElementMarshaller.marshal(document, element, octetString.getElement());
	}

}
