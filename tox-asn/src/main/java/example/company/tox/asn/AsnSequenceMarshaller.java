package example.company.tox.asn;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import example.company.asn.elements.AsnSequence;
import example.company.tox.common.Tox;

public class AsnSequenceMarshaller {

	public static void marshal(Document document, Element root, String name, AsnSequence sequence) {
		Element element = Tox.appendChild(document, root, name);
		sequence.getElements().forEach((item) -> {
			AsnTox.marshal(document, element, item);
		});
	}

}
