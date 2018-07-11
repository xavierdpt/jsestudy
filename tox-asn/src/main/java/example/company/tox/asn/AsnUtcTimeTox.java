package example.company.tox.asn;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import example.company.tox.common.Tox;
import xdptdr.asn.elements.AsnUtcTime;

public class AsnUtcTimeTox {

	public static void marshal(Document document, Element root, String name, AsnUtcTime utcTime) {
		Tox.appendChild(document, root, name, utcTime.getValue());
	}

}
