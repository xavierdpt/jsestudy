package example.company.jse.fiddle;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.interfaces.ECPublicKey;
import java.security.spec.InvalidParameterSpecException;

import org.apache.http.client.ClientProtocolException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import example.company.jse.fiddle.acme.JWK;
import example.company.jse.fiddle.acme.JWKProtected;
import xdptdr.acme.jw.JWBase64;
import xdptdr.acme.v2.Acme2;
import xdptdr.acme.v2.AcmeSession;
import xdptdr.acme.v2.model.AcmeDirectoryInfos2;
import xdptdr.common.Common;

public class Fiddle36 {

//	@Test
	public void fiddle() throws ClientProtocolException, IOException, NoSuchAlgorithmException,
			InvalidParameterSpecException, InvalidAlgorithmParameterException, InvalidKeyException, SignatureException {

		KeyPair keys = EC.genKeyPair(EC.NIST_P_256);
		ECPublicKey publik = (ECPublicKey) keys.getPublic();

		Common.disableHCLogging();

		ObjectMapper om = new ObjectMapper();
		om.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

		AcmeSession session = new AcmeSession();
		AcmeDirectoryInfos2 infos = Acme2.directory(Acme2.ACME_STAGING_V2, om, session).getContent();

		String nonce = session.getNonce();

		String url = infos.getNewAccountURL();

		JWK jwk = new JWK();
		jwk.setX(JWBase64.encode(publik.getW().getAffineX().toByteArray()));
		jwk.setY(JWBase64.encode(publik.getW().getAffineY().toByteArray()));

		JWKProtected jwkProtected = new JWKProtected();
		jwkProtected.setAlg("ES256");
		jwkProtected.setNonce(nonce);
		jwkProtected.setUrl(url);
		jwkProtected.setJwk(jwk);

		System.out.println(nonce);

	}

}
