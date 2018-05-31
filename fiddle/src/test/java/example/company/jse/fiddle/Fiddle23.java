package example.company.jse.fiddle;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import org.junit.Assert;
import org.junit.Test;

/* Make PKCS12Sample rely only on certificate and private key bytes */

public class Fiddle23 {

	@Test
	public void fiddle() throws NoSuchAlgorithmException, CertificateException, KeyStoreException, IOException,
			UnrecoverableKeyException {

		byte[] expected = FiddleCommon.getResourceBytes(FiddleCommon.CLIENTCA_P12_7);

		PKCS12Sample p12Sample = new PKCS12Sample();
		
		Assert.assertArrayEquals(expected, p12Sample.encode());

	}

}
