package example.company.jse.fiddle;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.junit.Assert;
import org.junit.Test;

import xdptdr.common.Common;

public class Fiddle44 {

	/*
	 * Generate new RSA public an drivate keys, reconstruct them from modulus and
	 * public and private exponent, then encrypt some content, then decrypt it and
	 * check it's correctt
	 */

	@Test
	public void fiddle() throws NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException,
			IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException {

		byte[] input = Common.bytes("00010203040506070809");

		RSAInfos infos = getInfos();

		RSAPublicKeySpec publicKs = new RSAPublicKeySpec(infos.modulus, infos.publicExponent);
		RSAPrivateKeySpec privateKs = new RSAPrivateKeySpec(infos.modulus, infos.privateExponent);

		KeyFactory kf = KeyFactory.getInstance("RSA");
		PublicKey public1 = kf.generatePublic(publicKs);
		PrivateKey private1 = kf.generatePrivate(privateKs);

		Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPPADDING");
		cipher.init(Cipher.ENCRYPT_MODE, public1);
		byte[] output = cipher.doFinal(input);

		cipher.init(Cipher.DECRYPT_MODE, private1);
		byte[] output2 = cipher.doFinal(output);

		Assert.assertArrayEquals(output2, input);

	}

	private RSAInfos getInfos() throws NoSuchAlgorithmException {
		KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
		KeyPair pair = kpg.generateKeyPair();
		RSAPublicKey public1 = (RSAPublicKey) pair.getPublic();
		RSAPrivateKey private1 = (RSAPrivateKey) pair.getPrivate();
		BigInteger modulus = public1.getModulus();
		BigInteger publicExponent = public1.getPublicExponent();
		BigInteger privateExponent = private1.getPrivateExponent();
		return new RSAInfos(modulus, publicExponent, privateExponent);

	}

	private static class RSAInfos {

		private BigInteger modulus;
		private BigInteger publicExponent;
		private BigInteger privateExponent;

		public RSAInfos(BigInteger modulus, BigInteger publicExponent, BigInteger privateExponent) {
			this.modulus = modulus;
			this.publicExponent = publicExponent;
			this.privateExponent = privateExponent;
		}

	}

}
