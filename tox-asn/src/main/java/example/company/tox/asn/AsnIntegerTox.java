package example.company.tox.asn;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import example.company.asn.elements.AsnInteger;
import example.company.tox.common.Tox;
import example.company.tox.common.ToxTox;

public class AsnIntegerTox extends ToxTox<AsnInteger> {

	public static void marshal(Document document, Element root, String name, AsnInteger integer) {
		Tox.appendChild(document, root, name, integer.getValue().toByteArray());
	}

	@Override
	public void tox(Document document, Element root, String name, AsnInteger t) {
		Tox.appendChild(document, root, name, t.getValue().toByteArray());

	}

	@Override
	protected String getDefaultName() {
		return "integer";
	}

}
