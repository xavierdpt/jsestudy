package example.company.jse.fiddle;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.ECField;
import java.security.spec.ECFieldFp;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;
import java.security.spec.ECPrivateKeySpec;
import java.security.spec.ECPublicKeySpec;
import java.security.spec.EllipticCurve;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import example.company.acme.jw.JWBase64;
import example.company.asn.elements.AsnElement;
import example.company.asn.utils.AsnUtils;
import example.company.tox.asn.AsnTox;
import example.company.tox.common.Common;
import example.company.tox.common.Tox;

public class Fiddle30 {

	public static class F {
		private String alg = "ES256";

		public String getAlg() {
			return alg;
		}

		public void setAlg(String alg) {
			this.alg = alg;
		}

	}

	public static class P {
		String iss = "joe";
		int exp = 1300819380;
		boolean root = true;

		public int getExp() {
			return exp;
		}

		public void setExp(int exp) {
			this.exp = exp;
		}

		public String getIss() {
			return iss;
		}

		public void setIss(String iss) {
			this.iss = iss;
		}

		@JsonProperty("http://example.com/is_root")
		public boolean isRoot() {
			return root;
		}

		public void setRoot(boolean root) {
			this.root = root;
		}

	}

	private String getContent() throws JsonProcessingException {
		F f = new F();
		ObjectMapper om = new ObjectMapper();
		String s = om.writeValueAsString(f);
		System.out.println(s);
		Encoder e = Base64.getEncoder();
		String b = e.encodeToString(s.getBytes());
		System.out.println(b);
		Assert.assertEquals("eyJhbGciOiJFUzI1NiJ9", b);
		String v = om.writeValueAsString(new P());
		System.out.println(v);
		System.out.println(e.encodeToString(v.getBytes()));

		Decoder d = Base64.getDecoder();
		String o = "eyJpc3MiOiJqb2UiLA0KICJleHAiOjEzMDA4MTkzODAsDQogImh0dHA6Ly9leGFtcGxlLmNvbS9pc19yb290Ijp0cnVlfQ";
		byte[] r = d.decode(o);
		System.out.println(new String(r, Charset.forName("UTF-8")));

		v = o;

		String c = b + "." + v;

		return c;

	}

	@Test
	public void tata()
			throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException,
			JsonProcessingException, InvalidParameterSpecException, InvalidAlgorithmParameterException {

		String kty = "EC";
		String crv = "P-256";

		String xBase64Encoded = "f83OJ3D2xF1Bg8vub9tLe1gHMzV76e8Tus9uPHvRVEU";
		String yBase64Encoded = "x_FEzRu9m36HLN_tue659LNpXW6pCyStikYjKIWI5a0";
		String dBase64Encoded = "jpsQnnGQmL-YBIffH1136cspYG6-0iY7X1fCE9-E9LI";

		byte[] dBytes = JWBase64.decode(dBase64Encoded);
		byte[] xBytes = JWBase64.decode(xBase64Encoded);
		byte[] yBytes = JWBase64.decode(yBase64Encoded);

		System.out.println("dBytes=" + Common.bytesToString(dBytes));
		System.out.println("xBytes=" + Common.bytesToString(xBytes));
		System.out.println("yBytes=" + Common.bytesToString(yBytes));

		BigInteger d = new BigInteger(dBytes);
		BigInteger x = new BigInteger(xBytes);
		BigInteger y = new BigInteger(yBytes);

		System.out.println("d=" + Common.bytesToString(d.toByteArray()));
		System.out.println("x=" + Common.bytesToString(x.toByteArray()));
		System.out.println("y=" + Common.bytesToString(y.toByteArray()));

		Assert.assertEquals("EC", kty);

		byte[] content = Common.bytes(101, 121, 74, 104, 98, 71, 99, 105, 79, 105, 74, 70, 85, 122, 73, 49, 78, 105, 74,
				57, 46, 101, 121, 74, 112, 99, 51, 77, 105, 79, 105, 74, 113, 98, 50, 85, 105, 76, 65, 48, 75, 73, 67,
				74, 108, 101, 72, 65, 105, 79, 106, 69, 122, 77, 68, 65, 52, 77, 84, 107, 122, 79, 68, 65, 115, 68, 81,
				111, 103, 73, 109, 104, 48, 100, 72, 65, 54, 76, 121, 57, 108, 101, 71, 70, 116, 99, 71, 120, 108, 76,
				109, 78, 118, 98, 83, 57, 112, 99, 49, 57, 121, 98, 50, 57, 48, 73, 106, 112, 48, 99, 110, 86, 108, 102,
				81);

		KeyPair pair = getECKeyPair(getCurveName(crv), d, x, y);

		System.out.println("content=" + Common.bytesToString(content));

		Signature signature = Signature.getInstance("SHA256withECDSA");
		signature.initSign(pair.getPrivate());
		signature.update(content);
		byte[] signatureBytes = signature.sign();

		signature.initVerify(pair.getPublic());
		signature.verify(signatureBytes);

		System.out.println("signature=" + Common.bytesToString(signatureBytes));

		byte[] r = Common.bytes(14, 209, 33, 83, 121, 99, 108, 72, 60, 47, 127, 21, 88, 7, 212, 2, 163, 178, 40, 3, 58,
				249, 124, 126, 23, 129, 154, 195, 22, 158, 166, 101);
		byte[] s = Common.bytes(197, 10, 7, 211, 140, 60, 112, 229, 216, 241, 45, 175, 8, 74, 84, 128, 166, 101, 144,
				197, 242, 147, 80, 154, 143, 63, 127, 138, 131, 163, 84, 213);
		System.out.println("r=" + Common.bytesToString(r));
		System.out.println("s=" + Common.bytesToString(s));

		AsnElement signatureAsn = AsnUtils.parse(signatureBytes);
		Tox.print(new AsnTox().tox(signatureAsn), System.out);

	}

	private String getCurveName(String crv) {
		if (crv.startsWith("P")) {
			return "NIST " + crv;
		}
		return crv;
	}

	private ECParameterSpec getSpec(String xEncoded, String yEncoded) {

		byte[] xBytes = JWBase64.decode(xEncoded);
		byte[] yBytes = JWBase64.decode(yEncoded);

		BigInteger p = new BigInteger("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEFFFFFC2F", 16);
		ECField field = new ECFieldFp(p);
		BigInteger a = new BigInteger("0", 16);
		BigInteger b = new BigInteger("7", 16);
		EllipticCurve curve = new EllipticCurve(field, a, b);
		BigInteger x = new BigInteger(Common.bytesToString(xBytes), 16);
		BigInteger y = new BigInteger(Common.bytesToString(yBytes), 16);
		ECPoint g = new ECPoint(x, y);

		BigInteger n = new BigInteger("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEBAAEDCE6AF48A03BBFD25E8CD0364141", 16);
		int h = 1;
		return new ECParameterSpec(curve, g, n, h);
	}

	private KeyPair getECKeyPair(String name, BigInteger s, BigInteger x, BigInteger y) throws NoSuchAlgorithmException,
			InvalidParameterSpecException, InvalidAlgorithmParameterException, InvalidKeySpecException {

		ECParameterSpec params = getECParams(name);
		KeyFactory keyFactory = KeyFactory.getInstance("EC");

		PublicKey publicKey = getPublicKey(keyFactory, params, x, y);
		PrivateKey privateKey = getPrivateKey(keyFactory, params, s);
		return new KeyPair(publicKey, privateKey);

	}

	private ECParameterSpec getECParams(String name) throws NoSuchAlgorithmException, InvalidParameterSpecException {
		AlgorithmParameters ap = AlgorithmParameters.getInstance("EC");
		ap.init(new ECGenParameterSpec(name));
		return ap.getParameterSpec(ECParameterSpec.class);
	}

	public PublicKey getPublicKey(KeyFactory keyFactory, ECParameterSpec params, BigInteger x, BigInteger y)
			throws InvalidKeySpecException {
		ECPublicKeySpec key = new ECPublicKeySpec(new ECPoint(x, y), params);
		return keyFactory.generatePublic(key);

	}

	public PrivateKey getPrivateKey(KeyFactory keyFactory, ECParameterSpec params, BigInteger s)
			throws InvalidKeySpecException {
		ECPrivateKeySpec key = new ECPrivateKeySpec(s, params);
		return keyFactory.generatePrivate(key);
	}

}
