package example.company.tox.asn;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import example.company.asn.elements.AsnNull;
import example.company.tox.common.Tox;

public class AsnNullMarshaller {

	public static void marshal(Document document, Element root, String name, AsnNull asnNull) {
		Tox.appendChild(document, root, name);
	}

}
