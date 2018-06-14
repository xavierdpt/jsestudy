package example.company.jse.fiddle.acme;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import example.company.acme.v2.JWSBase64;
import example.company.tox.common.Common;

public class JWSGenerator {

	public static String generate(JOSEHeader header, byte[] payload, ObjectMapper om, JWK jwk)
			throws JsonProcessingException, InvalidKeyException, NoSuchAlgorithmException,
			InvalidParameterSpecException, InvalidKeySpecException, SignatureException {

		String headerJson = om.writeValueAsString(header);
		String header64 = JWSBase64.encode(headerJson.getBytes());

		String payload64 = JWSBase64.encode(payload);

		String input64 = header64 + "." + payload64;

		String signature64 = null;
		if (JWA.ES256.equals(header.getAlg())) {
			BigInteger d = Common.bigInteger(JWSBase64.decode(jwk.getD()));
			String curve = javaCurve(jwk.getCrv());
			ECSignature signature = ECSigner.sign(input64.getBytes(), curve, d);

			signature64 = JWSBase64.encode(Common.concatenate(Common.bigIntegerToBytes(signature.getR()),
					Common.bigIntegerToBytes(signature.getS())));
		} else if (JWA.RS256.equals(header.getAlg())) {

			BigInteger n = Common.bigInteger(JWSBase64.decode(jwk.getN()));
			BigInteger d = Common.bigInteger(JWSBase64.decode(jwk.getD()));

			byte[] signature = RSASigner.sign(input64.getBytes(), n, d);

			signature64 = JWSBase64.encode(signature);
		}

		return header64 + "." + payload64 + "." + signature64;
	}

	private static String javaCurve(String crv) {
		return "NIST " + crv;
	}

	public static FlatJWS generateFlatJWS(JOSEHeader header, byte[] bytes, ObjectMapper om,
			Map<String, JWK> keys) throws InvalidKeyException, JsonProcessingException, NoSuchAlgorithmException,
			InvalidParameterSpecException, InvalidKeySpecException, SignatureException {

		FlatJWS jws = new FlatJWS();

		JOSEHeader algHeader = new JOSEHeader(header.getAlg());
		JOSEHeader kidHeader = new JOSEHeader(null, header.getKid());

		String result = generate(algHeader, bytes, om, keys.get(header.getKid()));
		String[] parts = result.split("\\.");
		jws.setHeader(kidHeader);
		jws.setProtekted(parts[0]);
		jws.setPayload(parts[1]);
		jws.setSignature(parts[2]);
		return jws;
	}
}
