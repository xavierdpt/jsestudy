package example.company.tox.asn;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import example.company.asn.elements.AsnBMPString;
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
import example.company.tox.common.Tox;
import example.company.tox.common.ToxTox;

public class AsnTox extends ToxTox<AsnElement> {

	@Override
	public void tox(Document document, Element root, String name, AsnElement t) {

		Element element  = root;
		if(name != null) {
			element = Tox.appendChild(document, root, name);
		}

		if (t != null) {
			if (t instanceof AsnBitString) {
				AsnBitStringTox.marshal(document, element, "bit-string", (AsnBitString) t);
			} else if (t instanceof AsnContextSpecific) {
				AsnContextSpecificTox.marshal(document, element, "context-specific", (AsnContextSpecific) t);
			} else if (t instanceof AsnInteger) {
				AsnIntegerTox.marshal(document, element, "integer", (AsnInteger) t);
			} else if (t instanceof AsnNull) {
				AsnNullTox.marshal(document, element, "null", (AsnNull) t);
			} else if (t instanceof AsnObjectIdentifier) {
				AsnObjectIdentifierTox.marshal(document, element, "object-identifier", (AsnObjectIdentifier) t);
			} else if (t instanceof AsnOctetString) {
				AsnOctetStringTox.marshal(document, element, "octet-string", (AsnOctetString) t);
			} else if (t instanceof AsnPrintableString) {
				AsnPrintableStringTox.marshal(document, element, "printable-string", (AsnPrintableString) t);
			} else if (t instanceof AsnSequence) {
				AsnSequenceTox.marshal(document, element, "sequence", (AsnSequence) t);
			} else if (t instanceof AsnSet) {
				AsnSetTox.marshal(document, element, "set", (AsnSet) t);
			} else if (t instanceof AsnSubjectIdentifier) {
				AsnSubjectIdentifierTox.marshal(document, element, "subject-identifier", (AsnSubjectIdentifier) t);
			} else if (t instanceof AsnUtcTime) {
				AsnUtcTimeTox.marshal(document, element, "utc-time", (AsnUtcTime) t);
			} else if (t instanceof AsnBMPString) {
				AsnBMPStringTox.marshal(document, element, "bmpstring", (AsnBMPString) t);
			}
		}
	}

	@Override
	protected String getDefaultName() {
		return null;
	}

}
