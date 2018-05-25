package example.company.tox.asn;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import example.company.asn.elements.AsnContextSpecific;
import example.company.tox.common.Tox;

public class AsnContextSpecificMarshaller {

	public static void marshal(Document document, Element root, String name, AsnContextSpecific contextSpecific) {
		Element element = Tox.appendChild(document, root, name);
		Tox.setAttribute(element, "tag", contextSpecific.getTag());
		AsnTox.marshal(document, element, contextSpecific.getElement());
	}

}
