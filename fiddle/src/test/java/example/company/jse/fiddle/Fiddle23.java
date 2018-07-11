package example.company.jse.fiddle;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.junit.Assert;
import org.junit.Test;

import xdptdr.asn.pkcs12.PKCS12Builder;

/* Make PKCS12Sample rely only on certificate and private key  */

public class Fiddle23 {

	@Test
	public void fiddle() throws NoSuchAlgorithmException, CertificateException, KeyStoreException, IOException,
			UnrecoverableKeyException, InvalidKeyException, InvalidKeySpecException, InvalidParameterSpecException,
			NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {

		PKCS12Builder builder = getBuilder();

		Assert.assertArrayEquals(getExpected(), builder.build());

	}

	public PKCS12Builder getBuilder() throws NoSuchAlgorithmException, CertificateException, KeyStoreException,
			IOException, UnrecoverableKeyException {
		KeyStore keyStore = FiddleCommon.getKeyStore(FiddleCommon.CLIENT_JKS_7, "password");
		X509Certificate x = FiddleCommon.getCertificate(keyStore, "clientCA");
		PrivateKey k = FiddleCommon.getPrivateKey(keyStore, "clientCA", "password");

		return new PKCS12Builder(x, k);
	}

	public byte[] getExpected() throws IOException {
		return FiddleCommon.getResourceBytes(FiddleCommon.CLIENTCA_P12_7);
	}

}
