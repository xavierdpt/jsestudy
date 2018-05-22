package example.company.tox.asn;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import example.company.asn.elements.AsnObjectIdentifier;
import example.company.asn.utils.AsnObjectIdentifierUtils;
import example.company.tox.common.Tox;

public class AsnObjectIdentifierMarshaller {

	public static void marshal(Document document, Element root, String name, AsnObjectIdentifier objectIdentifier) {

		String value = objectIdentifier.getValue();
		String label = AsnObjectIdentifierUtils.getLabel(value);

		Element element = Tox.appendChild(document, root, name);
		Tox.setAttribute(element, "value", value);
		Tox.setAttribute(element, "label", label);

	}

}
