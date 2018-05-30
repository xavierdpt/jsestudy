package example.company.jse.fiddle;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Base64;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;

public class FiddleCommon {

	public static final String CLIENT_JKS_4 = "/genclient/step4/client.jks";
	public static final String CLIENT_CRT_4 = "/genclient/step4/client.crt";
	
	public static final String CLIENT_JKS_5 = "/genclient/step5/client.jks";
	public static final String CLIENTCA_CRT_5 = "/genclient/step5/clientca.crt";
	
	public static final String CLIENT_JKS_6 = "/genclient/step6/client.jks";
	public static final String CLIENT_CRT_6 = "/genclient/step6/client.crt";
	
	public static final String CLIENT_JKS_7 = "/genclient/step7/client.jks";
	public static final String CLIENTCA_P12_7 = "/genclient/step7/clientca.p12";
	
	public static KeyStore getKeyStore(String path, String password)
			throws NoSuchAlgorithmException, CertificateException, IOException, KeyStoreException {
		KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
		keyStore.load(FiddleCommon.class.getResourceAsStream(path), password.toCharArray());
		return keyStore;
	}

	public static X509Certificate getCertificate(KeyStore keyStore, String name) throws KeyStoreException {
		return (X509Certificate) keyStore.getCertificate(name);
	}

	public static byte[] getCertificateSigningRequestBytes(String path) throws IOException {
		return getBase64Bytes(path, "-----BEGIN NEW CERTIFICATE REQUEST-----", "-----END NEW CERTIFICATE REQUEST-----");
	}

	public static byte[] getCertificateBytes(String path) throws IOException {
		return getBase64Bytes(path, "-----BEGIN CERTIFICATE-----", "-----END CERTIFICATE-----");
	}

	private static byte[] getBase64Bytes(String path, String firstLine, String lastLine) throws IOException {
		InputStream r = FiddleCommon.class.getResourceAsStream(path);
		Assert.assertNotNull(r);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		IOUtils.copy(r, baos);
		byte[] bytes = baos.toByteArray();
		String str = new String(bytes, Charset.forName("UTF-8"));
		String[] lines = str.split("[\\r\\n]");
		Assert.assertEquals(firstLine, lines[0]);
		Assert.assertEquals(lastLine, lines[lines.length - 1]);
		StringWriter sw = new StringWriter();
		for (int i = 1; i < lines.length - 1; ++i) {
			sw.append(lines[i]);
		}
		return Base64.getDecoder().decode(sw.toString());
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
}
