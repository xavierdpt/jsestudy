package example.company.tox.java.security.cert;

import java.util.Collection;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import example.company.tox.common.Tox;

public class AlternativeNamesDescription {

	private AlternativeNamesDescription() {

	}

	public static void marshal(Document document, Element root, String name, Collection<List<?>> alternativeNames) {
		if (alternativeNames != null) {
			alternativeNames.forEach((alternatives) -> {
				alternatives.forEach((o) -> {
					Tox.appendChild(document, root, name, o);
				});
			});
		}
	}
}
