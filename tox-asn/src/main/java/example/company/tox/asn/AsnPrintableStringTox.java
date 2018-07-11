package example.company.tox.asn;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import example.company.tox.common.Tox;
import xdptdr.asn.elements.AsnPrintableString;

public class AsnPrintableStringTox {

	public static void marshal(Document document, Element root, String name, AsnPrintableString string) {
		Tox.appendChild(document, root, name, string.getValue());
	}

}
