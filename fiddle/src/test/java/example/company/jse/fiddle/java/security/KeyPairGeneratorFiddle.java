package example.company.jse.fiddle.java.security;

import java.security.KeyPair;
import java.security.KeyPairGeneratorSpi;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Security;

import javax.xml.bind.JAXBException;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import example.company.tox.common.Tox;
import example.company.tox.java.security.AnnotatedKeyPairDescriptions;
import example.company.tox.java.security.KeyPairDescription;

public class KeyPairGeneratorFiddle {

	/**
	 * Generate a key pairwith the SUN DSA Key Pair Generator
	 * 
	 * @throws JAXBException
	 * @throws NoSuchAlgorithmException
	 */
	@Test
	public void fiddle1() throws JAXBException, NoSuchAlgorithmException {

		KeyPairGeneratorSpi kpg = (KeyPairGeneratorSpi) Security.getProvider("SUN")
				.getService("KeyPairGenerator", "DSA").newInstance(null);

		KeyPair keypair = kpg.generateKeyPair();

		KeyPairDescription keyPairDescription = new KeyPairDescription();

		Tox.print(keyPairDescription.tox(keypair), System.out);
	}

	/**
	 * Exercises all key pair generators
	 * 
	 * @throws JAXBException
	 */
	@Test
	public void fiddle2() throws JAXBException {

		Document document = Tox.createDocument();

		AnnotatedKeyPairDescriptions tox = new AnnotatedKeyPairDescriptions();

		Provider[] providers = Security.getProviders();
		for (int i = 0; i < providers.length; ++i) {
			Provider provider = providers[i];
			provider.getServices().forEach((service) -> {
				if ("KeyPairGenerator".equals(service.getType())) {
					String providerName = provider.getName();
					Element providerE = Tox.appendChild(document, "provider");
					Tox.setAttribute(providerE, "name", providerName);
					try {
						KeyPairGeneratorSpi keyPairGenerator = (KeyPairGeneratorSpi) service.newInstance(null);
						KeyPair keyPair = keyPairGenerator.generateKeyPair();
						tox.tox(document, keyPair);
					} catch (NoSuchAlgorithmException e) {
						System.out.println(e.getClass().getName() + " : " + e.getMessage());
					}
				}
			});
		}

		Tox.print(document, System.out);
	}
}
