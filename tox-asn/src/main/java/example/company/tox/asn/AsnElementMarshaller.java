package example.company.tox.asn;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import example.company.asn.elements.AsnBitString;
import example.company.asn.elements.AsnContextSpecific;
import example.company.asn.elements.AsnElement;
import example.company.asn.elements.AsnInteger;
import example.company.asn.elements.AsnNull;
import example.company.asn.elements.AsnObjectIdentifier;
import example.company.asn.elements.AsnOctetString;
import example.company.asn.elements.AsnPrintableString;
import example.company.asn.elements.AsnSequence;
import example.company.asn.elements.AsnSet;
import example.company.asn.elements.AsnSubjectIdentifier;
import example.company.asn.elements.AsnUtcTime;
import example.company.asn.utils.AsnUtils;
import example.company.tox.common.Bytes;
import example.company.tox.common.Tox;

public class AsnElementMarshaller {

	public static void marshal(Document document, Element root, AsnElement element) {
		if (element != null) {
			if (element instanceof AsnBitString) {
				AsnBitStringMarshaller.marshal(document, root, "bit-string", (AsnBitString) element);
			} else if (element instanceof AsnContextSpecific) {
				AsnContextSpecificMarshaller.marshal(document, root, "context-specific", (AsnContextSpecific) element);
			} else if (element instanceof AsnInteger) {
				AsnIntegerMarshaller.marshal(document, root, "integer", (AsnInteger) element);
			} else if (element instanceof AsnNull) {
				AsnNullMarshaller.marshal(document, root, "null", (AsnNull) element);
			} else if (element instanceof AsnObjectIdentifier) {
				AsnObjectIdentifierMarshaller.marshal(document, root, "object-identifier",
						(AsnObjectIdentifier) element);
			} else if (element instanceof AsnOctetString) {
				AsnOctetStringMarshaller.marshal(document, root, "octet-string", (AsnOctetString) element);
			} else if (element instanceof AsnPrintableString) {
				AsnPrintableStringMarshaller.marshal(document, root, "printable-string", (AsnPrintableString) element);
			} else if (element instanceof AsnSequence) {
				AsnSequenceMarshaller.marshal(document, root, "sequence", (AsnSequence) element);
			} else if (element instanceof AsnSet) {
				AsnSetMarshaller.marshal(document, root, "set", (AsnSet) element);
			} else if (element instanceof AsnSubjectIdentifier) {
				AsnSubjectIdentifierMarshaller.marshal(document, root, "subject-identifier",
						(AsnSubjectIdentifier) element);
			} else if (element instanceof AsnUtcTime) {
				AsnUtcTimeMarshaller.marshal(document, root, "utc-time", (AsnUtcTime) element);
			}
			if (element.debug()) {
				Element debug = Tox.appendChild(document, root, "debug");
				Tox.appendChild(document, debug, "identifier", element.getIdentifierBytes().toByteArray());
				Tox.appendChild(document, debug, "length", element.getLengthBytes().toByteArray());
				Tox.appendChild(document, debug, "content", element.getContentBytes().toByteArray());
			}
		}
	}

	public static void marshal(Document document, Element root, String name, byte[] encoded) {
		AsnElement asnElement = AsnUtils.parse(new Bytes(encoded));
		Element asnRoot = Tox.appendChild(document, root, "asn");
		marshal(document, asnRoot, asnElement);
	}

	public static void marshal(Document document, String name, AsnElement element) {
		Element asnRoot = Tox.appendChild(document, name);
		marshal(document, asnRoot, element);
	}

}
