package example.company.jse.fiddle;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

public class Fiddle20 {

	/* New stuff */

	// AlgorithmParameters.getInstance("PBE")
	// algParams.init(params.toByteArray());
	// pbeSpec = algParams.getParameterSpec(PBEParameterSpec.class);
	// ic = pbeSpec.getIterationCount();
	// PBEKeySpec keySpec = new PBEKeySpec(password);
	// SecretKeyFactory skFac = SecretKeyFactory.getInstance("PBE");
	// skey = skFac.generateSecret(keySpec);
	// Cipher.getInstance("1.2.840.113549.1.12.1.6");
	// cipher.init(Cipher.DECRYPT_MODE, skey, algParams);
	// safeContentsData = cipher.doFinal(safeContentsData);

	// MacData macData = new MacData(s);
	// int ic = macData.getIterations();
	// SHA1
	// Mac m = Mac.getInstance("HmacPBESHA1");
	// PBEParameterSpec params = new PBEParameterSpec(macData.getSalt(), ic);

	/*-
	PBEKeySpec keySpec = new PBEKeySpec(password);
	        SecretKeyFactory skFac = SecretKeyFactory.getInstance("PBE");
	        skey = skFac.generateSecret(keySpec);
	        keySpec.clearPassword();
	 */

	/*-
	m.init(key, params);
	            m.update(authSafeData);
	            byte[] macResult = m.doFinal();
	 */

	@Test
	public void fiddle() throws NoSuchAlgorithmException, CertificateException, KeyStoreException, IOException,
			UnrecoverableKeyException {

		KeyStore ks2 = FiddleCommon.getKeyStore(FiddleCommon.CLIENTCA_P12_7, "password");

		ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
		IOUtils.copy(this.getClass().getResourceAsStream(FiddleCommon.CLIENTCA_P12_7), baos2);

		byte[] bytes2 = baos2.toByteArray();

		PKCS12Builder builder = new PKCS12Builder();

		Assert.assertArrayEquals(bytes2, builder.build());

	}

}
