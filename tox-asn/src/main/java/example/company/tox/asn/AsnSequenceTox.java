package example.company.tox.asn;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import example.company.tox.common.Tox;
import xdptdr.asn.elements.AsnSequence;

public class AsnSequenceTox {

	public static void marshal(Document document, Element root, String name, AsnSequence sequence) {
		Element element = Tox.appendChild(document, root, name);
		sequence.getElements().forEach((item) -> {
			new AsnTox().tox(document, element, item);
		});
	}

}
