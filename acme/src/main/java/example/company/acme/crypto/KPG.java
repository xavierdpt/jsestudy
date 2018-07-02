package example.company.acme.crypto;

import java.security.AlgorithmParameters;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.ECParameterSpec;

public class KPG {

	public static KeyPair newECP256KeyPair() throws Exception {
		AlgorithmParameterSpec algorithmParameterSpec = new ECGenParameterSpec(ECCurves.NIST_P_256);

		AlgorithmParameters algorithmParameters = AlgorithmParameters.getInstance("EC");
		algorithmParameters.init(algorithmParameterSpec);
		ECParameterSpec ecParameterSpec = algorithmParameters.getParameterSpec(ECParameterSpec.class);

		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC");
		keyPairGenerator.initialize(ecParameterSpec);
		return keyPairGenerator.generateKeyPair();
	}

	public static KeyPair newRSAKeyPair() throws Exception {
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
		return keyPairGenerator.generateKeyPair();
	}

}
