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
import example.company.tox.common.ToxTox;

public class AsnTox extends ToxTox<AsnElement> {

	@Override
	public void tox(Document document, Element root, String name, AsnElement t) {
		if (root != null) {
			if (root instanceof AsnBitString) {
				AsnBitStringMarshaller.marshal(document, root, "bit-string", (AsnBitString) root);
			} else if (root instanceof AsnContextSpecific) {
				AsnContextSpecificMarshaller.marshal(document, root, "context-specific", (AsnContextSpecific) root);
			} else if (root instanceof AsnInteger) {
				AsnIntegerMarshaller.marshal(document, root, "integer", (AsnInteger) root);
			} else if (root instanceof AsnNull) {
				AsnNullMarshaller.marshal(document, root, "null", (AsnNull) root);
			} else if (root instanceof AsnObjectIdentifier) {
				AsnObjectIdentifierMarshaller.marshal(document, root, "object-identifier", (AsnObjectIdentifier) root);
			} else if (root instanceof AsnOctetString) {
				AsnOctetStringMarshaller.marshal(document, root, "octet-string", (AsnOctetString) root);
			} else if (root instanceof AsnPrintableString) {
				AsnPrintableStringMarshaller.marshal(document, root, "printable-string", (AsnPrintableString) root);
			} else if (root instanceof AsnSequence) {
				AsnSequenceMarshaller.marshal(document, root, "sequence", (AsnSequence) root);
			} else if (root instanceof AsnSet) {
				AsnSetMarshaller.marshal(document, root, "set", (AsnSet) root);
			} else if (root instanceof AsnSubjectIdentifier) {
				AsnSubjectIdentifierMarshaller.marshal(document, root, "subject-identifier",
						(AsnSubjectIdentifier) root);
			} else if (root instanceof AsnUtcTime) {
				AsnUtcTimeMarshaller.marshal(document, root, "utc-time", (AsnUtcTime) root);
			}
		}
	}

	@Override
	protected String getDefaultName() {
		return "asn";
	}

}
