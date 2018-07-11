package example.company.tox.asn;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import example.company.tox.common.Tox;
import example.company.tox.common.ToxTox;
import xdptdr.asn.elements.AsnOctetString;

public class AsnOctetStringTox extends ToxTox<AsnOctetString> {

	public static void marshal(Document document, Element root, String name, AsnOctetString octetString) {
		Tox.appendChild(document, root, name, octetString.getValue());
	}

	@Override
	public void tox(Document document, Element root, String name, AsnOctetString t) {
		Tox.appendChild(document, root, name, t.getValue());

	}

	@Override
	protected String getDefaultName() {
		return "octet-string";
	}

}
