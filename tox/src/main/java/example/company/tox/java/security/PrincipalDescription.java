package example.company.tox.java.security;

import java.security.Principal;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import example.company.tox.common.Tox;

public class PrincipalDescription {

	private PrincipalDescription() {
	}

	public static void marshal(Document document, Element root, String name, Principal principal) {
		if (principal != null) {
			Element element = Tox.appendChild(document, root, name);
			element.setAttribute("name", principal.getName());
			element.setAttribute("class-name", principal.getClass().getName());
		}

	}

}
