package example.company.jse.fiddle;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import org.junit.Assert;
import org.junit.Test;

import example.company.asn.elements.AsnSequence;
import example.company.tox.common.Common;

public class Fiddle20 {

	/* Verify MAC of clientca.p12 through PKCS12Builder which generates the same bytes */

	@Test
	public void fiddle() throws NoSuchAlgorithmException, IOException, InvalidParameterSpecException, InvalidKeyException, InvalidAlgorithmParameterException, InvalidKeySpecException {

		PKCS12Builder builder = new PKCS12Builder();
		
		AsnSequence macDataAsn = builder.getMacAsn().asSequence();
		byte[] salt = macDataAsn.getOctetString(1).getValue();
		int iterationCount = (int) macDataAsn.getInteger(2).getValue();
		
		Mac m = Mac.getInstance("HmacPBESHA1");
		PBEParameterSpec params =
                new PBEParameterSpec(salt, iterationCount);
		SecretKey key = null;
		
		char[] password = "password".toCharArray();
		PBEKeySpec keySpec = new PBEKeySpec(password );
        SecretKeyFactory skFac = SecretKeyFactory.getInstance("PBE");
        key = skFac.generateSecret(keySpec);
        keySpec.clearPassword();
		
		m.init(key, params);
		m.update(builder.getDataContent().getValue());
		byte[] macResult = m.doFinal();
		
		System.out.println(Common.bytesToString(macResult));
		
		Assert.assertArrayEquals(builder.hash, macResult);
	}



}
