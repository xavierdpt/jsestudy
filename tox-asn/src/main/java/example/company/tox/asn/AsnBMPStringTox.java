package example.company.tox.asn;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import example.company.asn.elements.AsnBMPString;
import example.company.tox.common.Tox;
import example.company.tox.common.ToxTox;

public class AsnBMPStringTox extends ToxTox<AsnBMPString>{

	public static void marshal(Document document, Element root, String name, AsnBMPString t) {
		Tox.appendChild(document, root, name, t.getValue());
	}
	
	@Override
	public void tox(Document document, Element root, String name, AsnBMPString t) {
		Tox.appendChild(document, root, name, t.getValue());
	}

	@Override
	protected String getDefaultName() {
		return "bmp-string";
	}

}
