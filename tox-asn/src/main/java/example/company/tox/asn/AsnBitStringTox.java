package example.company.tox.asn;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import example.company.asn.elements.AsnBitString;
import example.company.tox.common.Tox;
import example.company.tox.common.ToxTox;

public class AsnBitStringTox extends ToxTox<AsnBitString> {

	public static void marshal(Document document, Element root, String name, AsnBitString bitString) {
		Tox.appendChild(document, root, name, bitString.toByteArray());
	}

	@Override
	public void tox(Document document, Element root, String name, AsnBitString t) {
		Tox.appendChild(document, root, name, t.toByteArray());
	}

	@Override
	protected String getDefaultName() {
		return "bitstring";
	}

}
