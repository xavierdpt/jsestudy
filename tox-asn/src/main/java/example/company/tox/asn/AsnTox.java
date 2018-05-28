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
		if (t != null) {
			if (t instanceof AsnBitString) {
				AsnBitStringMarshaller.marshal(document, root, "bit-string", (AsnBitString) t);
			} else if (t instanceof AsnContextSpecific) {
				AsnContextSpecificMarshaller.marshal(document, root, "context-specific", (AsnContextSpecific) t);
			} else if (t instanceof AsnInteger) {
				AsnIntegerMarshaller.marshal(document, root, "integer", (AsnInteger) t);
			} else if (t instanceof AsnNull) {
				AsnNullMarshaller.marshal(document, root, "null", (AsnNull) t);
			} else if (t instanceof AsnObjectIdentifier) {
				AsnObjectIdentifierMarshaller.marshal(document, root, "object-identifier", (AsnObjectIdentifier) t);
			} else if (t instanceof AsnOctetString) {
				AsnOctetStringMarshaller.marshal(document, root, "octet-string", (AsnOctetString) t);
			} else if (t instanceof AsnPrintableString) {
				AsnPrintableStringMarshaller.marshal(document, root, "printable-string", (AsnPrintableString) t);
			} else if (t instanceof AsnSequence) {
				AsnSequenceMarshaller.marshal(document, root, "sequence", (AsnSequence) t);
			} else if (t instanceof AsnSet) {
				AsnSetMarshaller.marshal(document, root, "set", (AsnSet) t);
			} else if (t instanceof AsnSubjectIdentifier) {
				AsnSubjectIdentifierMarshaller.marshal(document, root, "subject-identifier", (AsnSubjectIdentifier) t);
			} else if (t instanceof AsnUtcTime) {
				AsnUtcTimeMarshaller.marshal(document, root, "utc-time", (AsnUtcTime) t);
			}
		}
	}

	@Override
	protected String getDefaultName() {
		return "asn";
	}

}
