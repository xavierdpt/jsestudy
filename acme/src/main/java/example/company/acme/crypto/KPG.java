package example.company.acme.crypto;

import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.ECParameterSpec;
import java.security.spec.InvalidParameterSpecException;

public class KPG {
	
	public static KeyPair newECP256KeyPair()
			throws NoSuchAlgorithmException, InvalidParameterSpecException, InvalidAlgorithmParameterException {
		AlgorithmParameterSpec algorithmParameterSpec = new ECGenParameterSpec(ECCurves.NIST_P_256);

		AlgorithmParameters algorithmParameters = AlgorithmParameters.getInstance("EC");
		algorithmParameters.init(algorithmParameterSpec);
		ECParameterSpec ecParameterSpec = algorithmParameters.getParameterSpec(ECParameterSpec.class);

		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC");
		keyPairGenerator.initialize(ecParameterSpec);
		return keyPairGenerator.generateKeyPair();
	}
	
}
