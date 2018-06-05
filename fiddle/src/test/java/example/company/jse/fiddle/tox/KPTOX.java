package example.company.jse.fiddle.tox;

import java.math.BigInteger;
import java.security.KeyPair;
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

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import example.company.tox.common.Tox;

public class KPTOX {
	public static void rsa(Document document, KeyPair pair) {

		RSAPublicKey publik = (RSAPublicKey) pair.getPublic();
		RSAPrivateKey pryvate = (RSAPrivateKey) pair.getPrivate();

		BigInteger modulus = publik.getModulus();
		BigInteger publicExponent = publik.getPublicExponent();
		BigInteger privateExponent = pryvate.getPrivateExponent();

		Element root = Tox.appendChild(document, document.getDocumentElement(), "rsa");
		Tox.appendChild(document, root, "modulus", modulus.toByteArray());
		Tox.appendChild(document, root, "publicExponent", publicExponent.toByteArray());
		Tox.appendChild(document, root, "privateExponent", privateExponent.toByteArray());

	}

	public static void dsa(Document document, KeyPair pair) {

		DSAPublicKey publik = (DSAPublicKey) pair.getPublic();
		DSAPrivateKey pryvate = (DSAPrivateKey) pair.getPrivate();

		DSAParams params = publik.getParams();

		BigInteger g = params.getG();
		BigInteger p = params.getP();
		BigInteger q = params.getQ();

		BigInteger y = publik.getY();
		BigInteger x = pryvate.getX();

		Element root = Tox.appendChild(document, document.getDocumentElement(), "dsa");
		Tox.appendChild(document, root, "g", g.toByteArray());
		Tox.appendChild(document, root, "p", p.toByteArray());
		Tox.appendChild(document, root, "q", q.toByteArray());
		Tox.appendChild(document, root, "y", y.toByteArray());
		Tox.appendChild(document, root, "x", x.toByteArray());

	}

	public static void dh(Document document, KeyPair pair) {

		DHPublicKey publik = (DHPublicKey) pair.getPublic();
		DHPrivateKey pryvate = (DHPrivateKey) pair.getPrivate();

		DHParameterSpec params = publik.getParams();

		BigInteger g = params.getG();
		BigInteger p = params.getP();
		int l = params.getL();

		BigInteger y = publik.getY();
		BigInteger x = pryvate.getX();

		Element root = Tox.appendChild(document, document.getDocumentElement(), "dh");
		Tox.appendChild(document, root, "g", g.toByteArray());
		Tox.appendChild(document, root, "p", p.toByteArray());
		Tox.appendChild(document, root, "l", l);
		Tox.appendChild(document, root, "y", y.toByteArray());
		Tox.appendChild(document, root, "x", x.toByteArray());

	}

	public static void ec(Document document, KeyPair pair) {

		ECPublicKey publik = (ECPublicKey) pair.getPublic();
		ECPrivateKey pryvate = (ECPrivateKey) pair.getPrivate();

		ECParameterSpec params = publik.getParams();
		EllipticCurve curve = params.getCurve();
		ECPoint generator = params.getGenerator();
		ECField field = curve.getField();

		byte[] seed = curve.getSeed();
		BigInteger a = curve.getA();
		BigInteger b = curve.getB();
		int fieldSize = field.getFieldSize();
		BigInteger x = generator.getAffineX();
		BigInteger y = generator.getAffineY();
		int cofactor = params.getCofactor();
		BigInteger order = params.getOrder();

		ECPoint publikPoint = publik.getW();
		BigInteger publikX = publikPoint.getAffineX();
		BigInteger publikY = publikPoint.getAffineY();

		BigInteger s = pryvate.getS();

		Element root = Tox.appendChild(document, document.getDocumentElement(), "ec");
		Tox.appendChild(document, root, "seed", seed);
		Tox.appendChild(document, root, "a", a.toByteArray());
		Tox.appendChild(document, root, "b", b.toByteArray());
		Tox.appendChild(document, root, "field-size", fieldSize);
		Tox.appendChild(document, root, "x", x.toByteArray());
		Tox.appendChild(document, root, "y", y.toByteArray());
		Tox.appendChild(document, root, "cofactor", cofactor);
		Tox.appendChild(document, root, "order", order.toByteArray());
		Tox.appendChild(document, root, "public-x", publikX.toByteArray());
		Tox.appendChild(document, root, "public-y", publikY.toByteArray());
		Tox.appendChild(document, root, "s", s.toByteArray());

	}

	public static Document ec(KeyPair pair) {
		Document document = Tox.createDocument();
		ec(document, pair);
		return document;
	}

}
