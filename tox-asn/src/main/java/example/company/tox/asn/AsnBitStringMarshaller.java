package example.company.tox.asn;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import example.company.asn.elements.AsnBitString;
import example.company.tox.common.Tox;

public class AsnBitStringMarshaller {

	public static void marshal(Document document, Element root, String name, AsnBitString bitString) {
		Tox.appendChild(document, root, name, bitString.getContent());
	}

}
