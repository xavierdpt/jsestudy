package example.company.jse.fiddle;

import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.ECParameterSpec;
import java.security.spec.InvalidParameterSpecException;

import org.junit.Test;

import example.company.jse.fiddle.tox.KPTOX;
import example.company.tox.common.Tox;

public class Fiddle35 {

	/* NIST P-256 */

	@Test
	public void fiddle()
			throws NoSuchAlgorithmException, InvalidParameterSpecException, InvalidAlgorithmParameterException {

		KeyPair pair = generateKeyPair();

		Tox.print(KPTOX.ec(pair), System.out);

	}

	private KeyPair generateKeyPair()
			throws NoSuchAlgorithmException, InvalidParameterSpecException, InvalidAlgorithmParameterException {

		ECGenParameterSpec ecGenSpec = getEcGenSpec();

		ECParameterSpec ecSpec = getEcSpec(ecGenSpec);

		return generateKeyPair(ecSpec);
	}

	private ECGenParameterSpec getEcGenSpec() {

		return new ECGenParameterSpec("NIST P-256");

	}

	private ECParameterSpec getEcSpec(AlgorithmParameterSpec ecGenspec)
			throws NoSuchAlgorithmException, InvalidParameterSpecException {

		AlgorithmParameters params = AlgorithmParameters.getInstance("EC");

		params.init(ecGenspec);

		return params.getParameterSpec(ECParameterSpec.class);

	}

	private KeyPair generateKeyPair(ECParameterSpec ecSpec)
			throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {

		KeyPairGenerator ec = KeyPairGenerator.getInstance("EC");

		ec.initialize(ecSpec);

		return ec.generateKeyPair();
	}
}
