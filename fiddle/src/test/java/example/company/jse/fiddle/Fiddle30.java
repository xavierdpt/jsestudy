package example.company.jse.fiddle;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.interfaces.ECPrivateKey;
import java.security.spec.ECField;
import java.security.spec.ECFieldFp;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;
import java.security.spec.ECPrivateKeySpec;
import java.security.spec.EllipticCurve;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import example.company.acme.v2.Acme2;
import example.company.acme.v2.AcmeDirectoryInfos2;
import example.company.acme.v2.JWSBase64;
import example.company.acme.v2.NewAccountRequest;
import example.company.acme.v2.NewAccountResponse;
import example.company.asn.elements.Asn;
import example.company.asn.elements.AsnElement;
import example.company.asn.utils.AsnUtils;
import example.company.asn.utils.OIDS;
import example.company.tox.asn.AsnTox;
import example.company.tox.common.Common;
import example.company.tox.common.Tox;

public class Fiddle30 {

	@Test
	public void fiddle() throws ClientProtocolException, IOException {

		Common.disableHCLogging();

		ObjectMapper om = new ObjectMapper();
		om.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

		AcmeDirectoryInfos2 infos = Acme2.directory(om);

		String nonce = Acme2.nonce(infos);

		String url = infos.getNewAccount();

		NewAccountRequest request = new NewAccountRequest();
		NewAccountResponse response = Request.Post(url).setHeader("Content-Type", "application/jose+json")
				.bodyString(om.writeValueAsString(request), ContentType.create("application/jose+json")).execute()
				.handleResponse(new ResponseHandler<NewAccountResponse>() {

					@Override
					public NewAccountResponse handleResponse(HttpResponse response)
							throws ClientProtocolException, IOException {
						System.out.println(response.getStatusLine().getStatusCode());
						String nonce2 = response.getFirstHeader("Replay-Nonce").getValue();
						String location = response.getFirstHeader("Location").getValue();
						return new NewAccountResponse(nonce2, location);
					}

				});

		System.out.println(nonce);
		System.out.println(response.getNonce());
		System.out.println(response.getLocation());

	}

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

	private String tigrou() throws JsonProcessingException {
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
	public void tata() throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException,
			SignatureException, JsonProcessingException {

		String kty = "EC";
		String crv = "P-256";
		String x = "f83OJ3D2xF1Bg8vub9tLe1gHMzV76e8Tus9uPHvRVEU";
		String y = "x_FEzRu9m36HLN_tue659LNpXW6pCyStikYjKIWI5a0";
		String d = "jpsQnnGQmL-YBIffH1136cspYG6-0iY7X1fCE9-E9LI";

		byte[] di = JWSBase64.decode(d);

		KeyPairGenerator kpg = KeyPairGenerator.getInstance("EC");
		KeyPair kp = kpg.generateKeyPair();
		PrivateKey p = kp.getPrivate();
		byte[] encoded = p.getEncoded();
		System.out.println(Common.bytesToString(encoded));
		AsnElement asn = AsnUtils.parse(p.getEncoded());
		Tox.print(new AsnTox().tox(asn), System.out);
		Tox.print(new AsnTox().tox(asn.asSequence().getOctetString(2).parse()), System.out);
		System.out.println(Common
				.bytesToString(asn.asSequence().getOctetString(2).parse().asSequence().getOctetString(1).getValue()));
		System.out.println(Common.bytesToString(di));

		KeyFactory kf = KeyFactory.getInstance("EC");

		byte[] bytes = tigrou().getBytes();

		byte[] dddBytes = JWSBase64.decode(d);
		ECPrivateKeySpec ks = new ECPrivateKeySpec(new BigInteger(Common.bytesToString(dddBytes), 16), getSpec(x, y));
		PrivateKey oups = kf.generatePrivate(ks);

		Signature s = Signature.getInstance("SHA256withECDSA");
		s.initSign(oups);
		s.update(bytes);
		byte[] signature = s.sign();

		System.out.println("signature="+Common.bytesToString(signature));

		byte[] r = Common.bytes(14, 209, 33, 83, 121, 99, 108, 72, 60, 47, 127, 21, 88, 7, 212, 2, 163, 178, 40, 3, 58,
				249, 124, 126, 23, 129, 154, 195, 22, 158, 166, 101);
		byte[] ss = Common.bytes(197, 10, 7, 211, 140, 60, 112, 229, 216, 241, 45, 175, 8, 74, 84, 128, 166, 101, 144,
				197, 242, 147, 80, 154, 143, 63, 127, 138, 131, 163, 84, 213);
		System.out.println("r="+Common.bytesToString(r));
		System.out.println("s="+Common.bytesToString(ss));

	}

	private ECParameterSpec getSpec(String xEncoded, String yEncoded) {

		byte[] xBytes = JWSBase64.decode(xEncoded);
		byte[] yBytes = JWSBase64.decode(yEncoded);

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

}
