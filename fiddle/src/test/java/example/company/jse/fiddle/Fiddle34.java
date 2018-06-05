package example.company.jse.fiddle;

import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.ECParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.junit.Test;
import org.w3c.dom.Document;

import example.company.jse.fiddle.tox.KPTOX;
import example.company.tox.common.Tox;

public class Fiddle34 {
	
	/* Elliptic curves with named curves */
	
	@Test
	public void fiddle() throws NoSuchAlgorithmException {

		for (String name : getSupportedCurves()) {
			try {
				generateKeyPair(name);
			} catch (NoSuchAlgorithmException | InvalidParameterSpecException | InvalidAlgorithmParameterException e) {
				System.out.println(e.getMessage() + " ; " + name);
			}
		}

	}

	public void generateKeyPair(String name)
			throws NoSuchAlgorithmException, InvalidParameterSpecException, InvalidAlgorithmParameterException {

		Document document = Tox.createDocument();
		Tox.appendChild(document, document.getDocumentElement(), "name", name);
		ECGenParameterSpec spec = new ECGenParameterSpec(name);

		AlgorithmParameters instance = AlgorithmParameters.getInstance("EC");
		instance.init(spec);

		ECParameterSpec parameterSpec = instance.getParameterSpec(ECParameterSpec.class);

		KeyPairGenerator ec = KeyPairGenerator.getInstance("EC");
		ec.initialize(parameterSpec);
		KeyPair pair = ec.generateKeyPair();
		KPTOX.ec(document, pair);
		Tox.print(document, System.out);

	}

	public List<String> getSupportedCurves() throws NoSuchAlgorithmException {

		List<String> curves = new ArrayList<>();

		Properties props = AlgorithmParameters.getInstance("EC").getProvider();
		String value = props.getProperty("AlgorithmParameters.EC SupportedCurves");
		System.out.println(value);
		String[] parts = value.split("\\|");
		for (String p : parts) {
			String n = p.substring(1, p.indexOf(","));
			curves.add(n);
		}
		return curves;
	}
}