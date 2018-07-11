package example.company.jse.fiddle.java.security;

import java.security.Provider;
import java.security.Security;

import javax.xml.bind.JAXBException;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import example.company.tox.common.Tox;

public class KeyPairGeneratorFiddle {

	/**
	 * Exercises all key pair generators
	 * 
	 * @throws JAXBException
	 */
	@Test
	public void fiddle2() throws JAXBException {

		Document document = Tox.createDocument();

		Provider[] providers = Security.getProviders();
		for (int i = 0; i < providers.length; ++i) {
			Provider provider = providers[i];
			provider.getServices().forEach((service) -> {
				if ("KeyPairGenerator".equals(service.getType())) {
					String providerName = provider.getName();
					Element providerE = Tox.appendChild(document, "provider");
					Tox.setAttribute(providerE, "name", providerName);
				}
			});
		}

		Tox.print(document, System.out);
	}
}
