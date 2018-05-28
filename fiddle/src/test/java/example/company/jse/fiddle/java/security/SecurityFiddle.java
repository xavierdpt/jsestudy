package example.company.jse.fiddle.java.security;

import java.security.Security;

import javax.xml.bind.JAXBException;

import org.junit.Test;
import org.w3c.dom.Document;

import example.company.tox.common.Tox;
import example.company.tox.java.security.ProviderTox;

public class SecurityFiddle {

	@Test
	public void test() throws JAXBException {

		ProviderTox tox = new ProviderTox();

		Document document = Tox.createDocument();
		tox.tox(document, Security.getProviders());
		Tox.print(document, System.out);

	}
}
