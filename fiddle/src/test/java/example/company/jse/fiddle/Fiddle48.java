package example.company.jse.fiddle;

import java.io.IOException;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.ECParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import example.company.acme.v2.Acme2;
import example.company.acme.v2.AcmeDirectoryInfos2;
import example.company.acme.v2.JWSBase64;
import example.company.tox.common.Common;

public class Fiddle48 {

	@Test
	public void fiddle()
			throws ClientProtocolException, IOException, NoSuchAlgorithmException, InvalidAlgorithmParameterException,
			InvalidParameterSpecException, InvalidKeyException, InvalidKeySpecException, SignatureException {

		ObjectMapper om = new ObjectMapper();
		om.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

		AcmeDirectoryInfos2 infos = Acme2.directory(om, true);

		String nonce64 = Acme2.nonce64(infos, true);

		KeyPair keyPair = newKeyPair();

		ECPublicKey publicKey = (ECPublicKey) keyPair.getPublic();

		String x64 = JWSBase64.encode(Common.bigIntegerToBytes(publicKey.getW().getAffineX()));
		String y64 = JWSBase64.encode(Common.bigIntegerToBytes(publicKey.getW().getAffineY()));

		Map<String, Object> newAccountJWS = AcmeNewAccount.newAccountJWS(infos, keyPair, nonce64, om, getJWK(x64, y64));

		System.out.println("Invoking new account ....");

		AcmeAccount account = AcmeNewAccount.newAccount(infos, newAccountJWS, om);
		long kid = account.getId();

		String kids = getKid(infos, account.getId());
		System.out.println(kids);
		String noNonce = account.getNonce();
		String noUrl = infos.getNewOrder();

		Map<String, Object> newOrderJWS = AcmeNewOrder.newOrderJWS(kids, noNonce, noUrl, om, keyPair, getJWK(x64, y64));

		System.out.println("Invoking new order ....");

		AcmeNewOrder.newOrder(infos, newOrderJWS, om);

	}

	private String getKid(AcmeDirectoryInfos2 infos, long kid) {
		String url = infos.getNewAccountURL();
		int lastSlash = url.lastIndexOf('/');

		return url.substring(0, lastSlash) + "/acct/" + kid;
	}

	private KeyPair newKeyPair()
			throws NoSuchAlgorithmException, InvalidParameterSpecException, InvalidAlgorithmParameterException {
		AlgorithmParameterSpec algorithmParameterSpec = new ECGenParameterSpec(ECCurves.NIST_P_256);

		AlgorithmParameters algorithmParameters = AlgorithmParameters.getInstance("EC");
		algorithmParameters.init(algorithmParameterSpec);
		ECParameterSpec ecParameterSpec = algorithmParameters.getParameterSpec(ECParameterSpec.class);

		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC");
		keyPairGenerator.initialize(ecParameterSpec);
		return keyPairGenerator.generateKeyPair();
	}

	private Map<String, String> getJWK(String x64, String y64) {
		Map<String, String> jwk = new HashMap<>();
		jwk.put("kty", "EC");
		jwk.put("crv", "P-256");
		jwk.put("x", x64);
		jwk.put("y", y64);
		return jwk;
	}
}