package example.company.tox.asn;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import example.company.tox.common.Tox;
import example.company.tox.common.ToxTox;
import xdptdr.asn.elements.AsnNull;

public class AsnNullTox extends ToxTox<AsnNull> {

	public static void marshal(Document document, Element root, String name, AsnNull asnNull) {
		Tox.appendChild(document, root, name);
	}

	@Override
	public void tox(Document document, Element root, String name, AsnNull t) {
		Tox.appendChild(document, root, name);
	}

	@Override
	protected String getDefaultName() {
		return "null";
	}

}
