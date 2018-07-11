package example.company.jse.fiddle.java.security.cert;

import java.security.InvalidAlgorithmParameterException;
import java.security.cert.PKIXParameters;
import java.security.cert.TrustAnchor;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class PKIXParametersFiddle {

	@Test(expected = InvalidAlgorithmParameterException.class)
	public void fiddle2() throws InvalidAlgorithmParameterException {
		Set<TrustAnchor> trustAnchors = new HashSet<>();
		new PKIXParameters(trustAnchors);
	}
}
