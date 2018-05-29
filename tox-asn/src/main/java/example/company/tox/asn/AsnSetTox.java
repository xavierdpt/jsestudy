package example.company.tox.asn;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import example.company.asn.elements.AsnSet;
import example.company.tox.common.Tox;

public class AsnSetTox {

	public static void marshal(Document document, Element root, String name, AsnSet set) {
		Element element = Tox.appendChild(document, root, name);
		set.getElements().forEach((item) -> {
			new AsnTox().tox(document, element, item);
		});
	}

}
