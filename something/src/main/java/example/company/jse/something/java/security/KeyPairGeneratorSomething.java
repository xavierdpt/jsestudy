package example.company.jse.something.java.security;

import java.security.KeyPair;
import java.security.KeyPairGeneratorSpi;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Provider.Service;
import java.security.Security;

public class KeyPairGeneratorSomething {

	public static KeyPair createKeyPair() throws NoSuchAlgorithmException {
		Provider provider = Security.getProvider("SUN");
		Service service = provider.getService("KeyPairGenerator", "DSA");
		KeyPairGeneratorSpi kpg = (KeyPairGeneratorSpi) service.newInstance(null);
		return kpg.generateKeyPair();

	}
}
