package example.company.jse.fiddle.java.security;

import java.security.Security;

import javax.xml.bind.JAXBException;

import org.junit.Test;

import example.company.tox.Tox;
import example.company.tox.java.security.ProvidersDescriptions;

public class SecurityFiddle {

	@Test
	public void test() throws JAXBException {

		ProvidersDescriptions providers = new ProvidersDescriptions(Security.getProviders());

		Tox.marshall(providers, System.out);

	}
}
