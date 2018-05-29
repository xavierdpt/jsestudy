package example.company.tox.asn;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import example.company.asn.elements.AsnObjectIdentifier;
import example.company.asn.utils.OIDS;
import example.company.tox.common.Tox;
import example.company.tox.common.ToxTox;

public class AsnObjectIdentifierTox extends ToxTox<AsnObjectIdentifier> {

	public static void marshal(Document document, Element root, String name, AsnObjectIdentifier objectIdentifier) {

		String value = objectIdentifier.getValue();
		String label = OIDS.getLabel(value);

		Element element = Tox.appendChild(document, root, name);
		Tox.setAttribute(element, "value", value);
		Tox.setAttribute(element, "label", label);

	}

	@Override
	public void tox(Document document, Element root, String name, AsnObjectIdentifier t) {
		String value = t.getValue();
		String label = OIDS.getLabel(value);

		Element element = Tox.appendChild(document, root, name);
		Tox.setAttribute(element, "value", value);
		Tox.setAttribute(element, "label", label);

	}

	@Override
	protected String getDefaultName() {
		return "object-identifier";
	}

}
