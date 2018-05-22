package example.company.tox.java.security.auth.x500;

import javax.security.auth.x500.X500Principal;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import example.company.tox.common.Tox;
import example.company.tox.proxy.AsnElementMarshaller;

public class X500PrincipalDescription {

	public X500PrincipalDescription(X500Principal principal) {
	}

	public static void marshal(Document document, Element root, String name, X500Principal principal) {
		Element elementt = Tox.appendChild(document, root, name);
		Tox.setAttribute(elementt, "name", principal.getName());
		Tox.setAttribute(elementt, "className", principal.getClass().getName());
		byte[] encoded = principal.getEncoded();
		Tox.appendChild(document, elementt, "encoded", encoded);
		if (encoded != null) {
			AsnElementMarshaller.marshal(document, root, "asn", encoded);
		}
	}

}
