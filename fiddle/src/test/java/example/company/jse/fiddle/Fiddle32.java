package example.company.jse.fiddle;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.DSAParams;
import java.security.interfaces.DSAPrivateKey;
import java.security.interfaces.DSAPublicKey;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.ECField;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;
import java.security.spec.EllipticCurve;

import javax.crypto.interfaces.DHPrivateKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;

import org.junit.Assert;
import org.junit.Test;

public class Fiddle32 {

	/* Params are same for public and private keys */
	
	@Test
	public void fiddle() throws NoSuchAlgorithmException {
		rsa();
		dsa();
		dh();
		ec();
	}

	private void rsa() throws NoSuchAlgorithmException {

		KeyPairGenerator rsa = KeyPairGenerator.getInstance("RSA");
		KeyPair pair = rsa.generateKeyPair();
		RSAPrivateKey pr = (RSAPrivateKey) pair.getPrivate();
		RSAPublicKey pu = (RSAPublicKey) pair.getPublic();

		Assert.assertEquals(pu.getModulus(), pr.getModulus());
		

	}

	private void dsa() throws NoSuchAlgorithmException {

		KeyPairGenerator dsa = KeyPairGenerator.getInstance("DSA");
		KeyPair pair = dsa.generateKeyPair();
		DSAPrivateKey pr = (DSAPrivateKey) pair.getPrivate();
		DSAPublicKey pu = (DSAPublicKey) pair.getPublic();

		DSAParams puParams = pu.getParams();
		DSAParams prParams = pr.getParams();

		Assert.assertEquals(puParams.getG(), prParams.getG());
		Assert.assertEquals(puParams.getP(), prParams.getP());
		Assert.assertEquals(puParams.getQ(), prParams.getQ());

	}

	private void dh() throws NoSuchAlgorithmException {

		KeyPairGenerator dh = KeyPairGenerator.getInstance("DiffieHellman");
		KeyPair pair = dh.generateKeyPair();
		DHPrivateKey pr = (DHPrivateKey) pair.getPrivate();
		DHPublicKey pu = (DHPublicKey) pair.getPublic();

		DHParameterSpec puParams = pu.getParams();
		DHParameterSpec prParams = pr.getParams();

		Assert.assertEquals(puParams.getG(), prParams.getG());
		Assert.assertEquals(puParams.getP(), prParams.getP());
		Assert.assertEquals(puParams.getL(), prParams.getL());

	}

	private void ec() throws NoSuchAlgorithmException {

		KeyPairGenerator ec = KeyPairGenerator.getInstance("EC");
		KeyPair pair = ec.generateKeyPair();
		ECPrivateKey pr = (ECPrivateKey) pair.getPrivate();
		ECPublicKey pu = (ECPublicKey) pair.getPublic();

		ECParameterSpec puParams = pu.getParams();
		ECParameterSpec prParams = pr.getParams();

		EllipticCurve puCurve = puParams.getCurve();
		EllipticCurve prCurve = prParams.getCurve();

		ECPoint puGen = puParams.getGenerator();
		ECPoint prGen = prParams.getGenerator();

		ECField puField = puCurve.getField();
		ECField prField = prCurve.getField();

		Assert.assertArrayEquals(puCurve.getSeed(), prCurve.getSeed());
		Assert.assertEquals(puCurve.getA(), prCurve.getA());
		Assert.assertEquals(puCurve.getB(), prCurve.getB());
		Assert.assertEquals(puField.getFieldSize(), prField.getFieldSize());
		Assert.assertEquals(puGen.getAffineX(), prGen.getAffineX());
		Assert.assertEquals(puGen.getAffineY(), prGen.getAffineY());
		Assert.assertEquals(puParams.getCofactor(), prParams.getCofactor());
		Assert.assertEquals(puParams.getOrder(), prParams.getOrder());
	}

}
