package example.company.jse.fiddle;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;

import xdptdr.asn.OIDS;
import xdptdr.asn.pkcs12.PKCS12Builder;
import xdptdr.common.Common;

public class FiddleCommon {

	public static final String CLIENT_JKS_4 = "/genclient/step4/client.jks";
	public static final String CLIENT_CRT_4 = "/genclient/step4/client.crt";

	public static final String CLIENT_JKS_5 = "/genclient/step5/client.jks";
	public static final String CLIENTCA_CRT_5 = "/genclient/step5/clientca.crt";

	public static final String CLIENT_JKS_6 = "/genclient/step6/client.jks";
	public static final String CLIENT_CRT_6 = "/genclient/step6/client.crt";

	public static final String CLIENT_JKS_7 = "/genclient/step7/client.jks";
	public static final String CLIENTCA_P12_7 = "/genclient/step7/clientca.p12";
	public static final String LETSENCRTYPT = "/letsencrypt";

	public static KeyStore getKeyStore(String path, String password)
			throws NoSuchAlgorithmException, CertificateException, IOException, KeyStoreException {
		KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
		keyStore.load(FiddleCommon.class.getResourceAsStream(path), password.toCharArray());
		return keyStore;
	}

	public static X509Certificate getCertificate(KeyStore keyStore, String name) throws KeyStoreException {
		return (X509Certificate) keyStore.getCertificate(name);
	}

	public static InputStream getInputStream(String path) {
		return FiddleCommon.class.getResourceAsStream(path);
	}
	
	public static String getResourceContent(String path) throws IOException {
		InputStream r = FiddleCommon.class.getResourceAsStream(path);
		Assert.assertNotNull(r);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		IOUtils.copy(r, baos);
		return new String(baos.toByteArray(), Charset.forName("UTF-8"));
	}

	public static PrivateKey getPrivateKey(KeyStore keyStore, String name, String password)
			throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException {
		return (PrivateKey) keyStore.getKey(name, password.toCharArray());
	}

	public static byte[] getResourceBytes(String path) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		IOUtils.copy(FiddleCommon.class.getResourceAsStream(path), baos);
		return baos.toByteArray();
	}

	public static PKCS12Builder getSamplePKCS12Builder() throws NoSuchAlgorithmException, CertificateException,
			KeyStoreException, IOException, UnrecoverableKeyException {

		KeyStore keyStore = getKeyStore(FiddleCommon.CLIENT_JKS_7, "password");
		X509Certificate x = getCertificate(keyStore, "clientCA");
		PrivateKey k = getPrivateKey(keyStore, "clientCA", "password");

		PKCS12Builder builder = new PKCS12Builder(x, k);

		builder.setPrivateKeyEncryptionSalt(Common.bytes("8FD2439C4F78EB6612D94F642C7B75FBBF5FF371"));
		builder.setPrivateKeyEncryptionIterationCount(50000);

		builder.setCertificateEncryptionSalt(Common.bytes("1B9DCA53442A0222F5DA254DBBC62C7C6FD2E60E"));
		builder.setCertificateEncryptionIterationCount(50000);

		builder.setFriendlyName("clientca");

		builder.setMacSalt(Common.bytes("6CB867698D2BF58AC0ECA315C192DEC3C76AF213"));
		builder.setMacIterationCount(100000);

		builder.setPrivateKeyCipher(OIDS.PBE_WITH_SHA_AND_3_KEY_TRIPLE_DES_CBC);
		builder.setCertificateCipher(OIDS.PBE_WITH_SHA_AND_40_BIT_RC2_CBC);
		builder.setCertificatePassword("password");
		builder.setPrivateKeyPassword("password");
		builder.setMacAlgo("HmacPBESHA1");

		builder.setLocalKeyId("Time 1527240679023");

		return builder;

	}
}
