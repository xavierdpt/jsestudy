package example.company.tox.asn;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import example.company.asn.elements.AsnContextSpecific;
import example.company.tox.common.Tox;
import example.company.tox.common.ToxTox;

public class AsnContextSpecificTox extends ToxTox<AsnContextSpecific> {

	public static void marshal(Document document, Element root, String name, AsnContextSpecific contextSpecific) {
		Element element = Tox.appendChild(document, root, name, contextSpecific.getValue());
		Tox.setAttribute(element, "tag", contextSpecific.getTag());
	}

	@Override
	public void tox(Document document, Element root, String name, AsnContextSpecific t) {
		Element element = Tox.appendChild(document, root, name, t.getValue());
		Tox.setAttribute(element, "tag", t.getTag());
	}

	@Override
	protected String getDefaultName() {
		return "context-specific";
	}

}
