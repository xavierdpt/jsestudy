package example.company.jse.fiddle;

import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import example.company.acme.v2.JWSBase64;
import example.company.tox.common.Common;

public class JWSValidator {

	public static void validate(String HPS, ObjectMapper om, JWK ecwk)
			throws JsonParseException, JsonMappingException, IOException, InvalidKeyException, NoSuchAlgorithmException,
			InvalidParameterSpecException, InvalidKeySpecException, SignatureException {

		String[] parts = HPS.split("\\.");
		String header64 = parts[0];
		String payload64 = parts[1];
		String signature64 = parts[2];

		byte[] headerBytes = JWSBase64.decode(header64);
		byte[] payloadBytes = JWSBase64.decode(payload64);
		byte[] signatureBytes = JWSBase64.decode(signature64);

		JOSEHeader header = om.readValue(headerBytes, JOSEHeader.class);

		byte[] input = Common.concatenate(headerBytes, ".".getBytes(), payloadBytes);

		if (JWA.ES256.equals(header.getAlg())) {

			int sl = signatureBytes.length;

			BigInteger r = Common.bigInteger(Common.part(signatureBytes, 0, sl / 2));
			BigInteger s = Common.bigInteger(Common.part(signatureBytes, sl / 2 + 1, sl));

			String curve = javaCurve(ecwk.getCrv());

			BigInteger x = Common.bigInteger(JWSBase64.decode(ecwk.getX()));
			BigInteger y = Common.bigInteger(JWSBase64.decode(ecwk.getY()));

			ECSigner.verify(new ECSignature(r, s), input, curve, x, y);
		} else if (JWA.RS256.equals(header.getAlg())) {

			BigInteger n = Common.bigInteger(JWSBase64.decode(ecwk.getN()));
			BigInteger e = Common.bigInteger(JWSBase64.decode(ecwk.getE()));

			RSASigner.verify(signatureBytes, input, n, e);
		} else {
			throw new RuntimeException("Unknown algorithm " + header.getAlg());
		}

	}

	public static void validate(JWSWithMultipleSignatures jws, ObjectMapper om, Map<String, JWK> keys)
			throws JsonParseException, JsonMappingException, InvalidKeyException, NoSuchAlgorithmException,
			InvalidParameterSpecException, InvalidKeySpecException, SignatureException, IOException {

		for (JWSSignature signature : jws.getSignatures()) {
			String hps = signature.getProtekted() + "." + jws.getPayload() + "." + signature.getSignature();
			validate(hps, om, keys.get(signature.getHeader().getKid()));
		}

	}

	public static void validate(FlatJWS jws, ObjectMapper om, Map<String, JWK> keys)
			throws JsonParseException, JsonMappingException, InvalidKeyException, NoSuchAlgorithmException,
			InvalidParameterSpecException, InvalidKeySpecException, SignatureException, IOException {
		String hps = jws.getProtekted() + "." + jws.getPayload() + "." + jws.getSignature();
		validate(hps, om, keys.get(jws.getHeader().getKid()));
	}

	private static String javaCurve(String crv) {
		return "NIST " + crv;
	}

}
