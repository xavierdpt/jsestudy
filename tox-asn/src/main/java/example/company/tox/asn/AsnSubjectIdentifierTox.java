package example.company.tox.asn;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import example.company.asn.elements.AsnSubjectIdentifier;
import example.company.tox.common.Tox;

public class AsnSubjectIdentifierTox {

	public static void marshal(Document document, Element root, String name, AsnSubjectIdentifier subjectIdentifier) {
		Tox.appendChild(document, root, name, subjectIdentifier.getValue());
	}

}
