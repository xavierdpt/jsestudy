package example.company.jse.fiddle;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Fiddle47 {

	@Test
	public void testA2() throws JsonParseException, JsonMappingException, InvalidKeyException, NoSuchAlgorithmException,
			InvalidParameterSpecException, InvalidKeySpecException, SignatureException, IOException {

		// validate RFC7515 JWS A2 and generate a new input and validate the output for
		// that input

		ObjectMapper om = new ObjectMapper();

		JWK jwk = getRSAJWK();

		String a2Output = "eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJqb2UiLA0KICJleHAiOjEzMDA4MTkzODAsDQogImh0dHA6Ly9leGFtcGxlLmNvbS9pc19yb290Ijp0cnVlfQ.cC4hiUPoj9Eetdgtv3hF80EGrhuB__dzERat0XF9g2VtQgr9PJbu3XOiZj5RZmh7AAuHIm4Bh-0Qc_lF5YKt_O8W2Fp5jujGbds9uJdbF9CUAr7t1dnZcAcQjbKBYNX4BAynRFdiuB--f_nZLgrnbyTyWzO75vRK5h6xBArLIARNPvkSjtQBMHlb1L07Qe7K0GarZRmB_eSN9383LcOLn6_dO--xi12jzDwusC-eOkHWEsqtFZESc6BfI7noOPqvhJ1phCnvWh6IeYI2w9QOYEUipUTI8np6LbgGY9Fs98rqVt5AXLIhWkWywlVmtVrBp0igcN_IoypGlUPQGe77Rw";

		JWSValidator.validate(a2Output, om, jwk);

		String result = JWSGenerator.generate(new JOSEHeader(JWA.RS256), "hello".getBytes(), om, jwk);
		JWSValidator.validate(result, om, jwk);

	}

	public void testA3() throws JsonParseException, JsonMappingException, InvalidKeyException, NoSuchAlgorithmException,
			InvalidParameterSpecException, InvalidKeySpecException, SignatureException, IOException {

		// validate RFC7515 JWS A3 and generate a new input and validate the output for
		// that input

		ObjectMapper om = new ObjectMapper();

		JWK jwk = getECP256JWK();
		;

		String a3Output = "eyJhbGciOiJFUzI1NiJ9.eyJpc3MiOiJqb2UiLA0KICJleHAiOjEzMDA4MTkzODAsDQogImh0dHA6Ly9leGFtcGxlLmNvbS9pc19yb290Ijp0cnVlfQ.DtEhU3ljbEg8L38VWAfUAqOyKAM6-Xx-F4GawxaepmXFCgfTjDxw5djxLa8ISlSApmWQxfKTUJqPP3-Kg6NU1Q";

		JWSValidator.validate(a3Output, om, jwk);

		String result = JWSGenerator.generate(new JOSEHeader(JWA.ES256), "hello".getBytes(), om, jwk);
		JWSValidator.validate(result, om, jwk);
	}

	@Test
	public void testA6() throws JsonParseException, JsonMappingException, InvalidKeyException, NoSuchAlgorithmException,
			InvalidParameterSpecException, InvalidKeySpecException, SignatureException, IOException {

		ObjectMapper om = new ObjectMapper();

		JWSWithMultipleSignatures jws = new JWSWithMultipleSignatures();

		jws.setPayload(
				"eyJpc3MiOiJqb2UiLA0KICJleHAiOjEzMDA4MTkzODAsDQogImh0dHA6Ly9leGFtcGxlLmNvbS9pc19yb290Ijp0cnVlfQ");

		JWSSignature signature1 = new JWSSignature();
		signature1.setProtekted("eyJhbGciOiJSUzI1NiJ9");
		signature1.setHeader(new JOSEHeader(null, "2010-12-29"));
		signature1.setSignature(
				"cC4hiUPoj9Eetdgtv3hF80EGrhuB__dzERat0XF9g2VtQgr9PJbu3XOiZj5RZmh7AAuHIm4Bh-0Qc_lF5YKt_O8W2Fp5jujGbds9uJdbF9CUAr7t1dnZcAcQjbKBYNX4BAynRFdiuB--f_nZLgrnbyTyWzO75vRK5h6xBArLIARNPvkSjtQBMHlb1L07Qe7K0GarZRmB_eSN9383LcOLn6_dO--xi12jzDwusC-eOkHWEsqtFZESc6BfI7noOPqvhJ1phCnvWh6IeYI2w9QOYEUipUTI8np6LbgGY9Fs98rqVt5AXLIhWkWywlVmtVrBp0igcN_IoypGlUPQGe77Rw");

		JWSSignature signature2 = new JWSSignature();
		signature2.setProtekted("eyJhbGciOiJFUzI1NiJ9");
		signature2.setHeader(new JOSEHeader(null, "e9bc097a-ce51-4036-9562-d2ade882db0d"));
		signature2
				.setSignature("DtEhU3ljbEg8L38VWAfUAqOyKAM6-Xx-F4GawxaepmXFCgfTjDxw5djxLa8ISlSApmWQxfKTUJqPP3-Kg6NU1Q");
		jws.getSignatures().add(signature1);
		jws.getSignatures().add(signature2);

		Map<String, JWK> keys = new HashMap<>();
		keys.put("2010-12-29", getRSAJWK());
		keys.put("e9bc097a-ce51-4036-9562-d2ade882db0d", getECP256JWK());

		JWSValidator.validate(jws, om, keys);

	}

	@Test
	public void testA7() throws JsonParseException, JsonMappingException, InvalidKeyException, NoSuchAlgorithmException,
			InvalidParameterSpecException, InvalidKeySpecException, SignatureException, IOException {

		ObjectMapper om = new ObjectMapper();

		FlatJWS jws = new FlatJWS();

		JOSEHeader header = new JOSEHeader(null, "e9bc097a-ce51-4036-9562-d2ade882db0d");
		jws.setPayload(
				"eyJpc3MiOiJqb2UiLA0KICJleHAiOjEzMDA4MTkzODAsDQogImh0dHA6Ly9leGFtcGxlLmNvbS9pc19yb290Ijp0cnVlfQ");
		jws.setHeader(header);
		jws.setProtekted("eyJhbGciOiJFUzI1NiJ9");
		jws.setSignature("DtEhU3ljbEg8L38VWAfUAqOyKAM6-Xx-F4GawxaepmXFCgfTjDxw5djxLa8ISlSApmWQxfKTUJqPP3-Kg6NU1Q");

		Map<String, JWK> keys = new HashMap<>();
		keys.put("e9bc097a-ce51-4036-9562-d2ade882db0d", getECP256JWK());

		JWSValidator.validate(jws, om, keys);

		JOSEHeader algAndKidHeader = new JOSEHeader(JWA.ES256, header.getKid());
		FlatJWS helloJWS = JWSGenerator.generateFlatJWS(algAndKidHeader, "hello".getBytes(), om, keys);
		JWSValidator.validate(helloJWS, om, keys);

	}

	private JWK getECP256JWK() {
		JWK jwk = new JWK();
		jwk.setKty("EC");
		jwk.setCrv("P-256");
		jwk.setX("f83OJ3D2xF1Bg8vub9tLe1gHMzV76e8Tus9uPHvRVEU");
		jwk.setY("x_FEzRu9m36HLN_tue659LNpXW6pCyStikYjKIWI5a0");
		jwk.setD("jpsQnnGQmL-YBIffH1136cspYG6-0iY7X1fCE9-E9LI");
		return jwk;
	}

	private JWK getRSAJWK() {
		JWK jwk = new JWK();
		jwk.setKty("RSA");
		jwk.setN(
				"ofgWCuLjybRlzo0tZWJjNiuSfb4p4fAkd_wWJcyQoTbji9k0l8W26mPddxHmfHQp-Vaw-4qPCJrcS2mJPMEzP1Pt0Bm4d4QlL-yRT-SFd2lZS-pCgNMsD1W_YpRPEwOWvG6b32690r2jZ47soMZo9wGzjb_7OMg0LOL-bSf63kpaSHSXndS5z5rexMdbBYUsLA9e-KXBdQOS-UTo7WTBEMa2R2CapHg665xsmtdVMTBQY4uDZlxvb3qCo5ZwKh9kG4LT6_I5IhlJH7aGhyxXFvUK-DWNmoudF8NAco9_h9iaGNj8q2ethFkMLs91kzk2PAcDTW9gb54h4FRWyuXpoQ");
		jwk.setE("AQAB");
		jwk.setD(
				"Eq5xpGnNCivDflJsRQBXHx1hdR1k6Ulwe2JZD50LpXyWPEAeP88vLNO97IjlA7_GQ5sLKMgvfTeXZx9SE-7YwVol2NXOoAJe46sui395IW_GO-pWJ1O0BkTGoVEn2bKVRUCgu-GjBVaYLU6f3l9kJfFNS3E0QbVdxzubSu3Mkqzjkn439X0M_V51gfpRLI9JYanrC4D4qAdGcopV_0ZHHzQlBjudU2QvXt4ehNYTCBr6XCLQUShb1juUO1ZdiYoFaFQT5Tw8bGUl_x_jTj3ccPDVZFD9pIuhLhBOneufuBiB4cS98l2SR_RQyGWSeWjnczT0QU91p1DhOVRuOopznQ");
		jwk.setP(
				"4BzEEOtIpmVdVEZNCqS7baC4crd0pqnRH_5IB3jw3bcxGn6QLvnEtfdUdiYrqBdss1l58BQ3KhooKeQTa9AB0Hw_Py5PJdTJNPY8cQn7ouZ2KKDcmnPGBY5t7yLc1QlQ5xHdwW1VhvKn-nXqhJTBgIPgtldC-KDV5z-y2XDwGUc");
		jwk.setQ(
				"uQPEfgmVtjL0Uyyx88GZFF1fOunH3-7cepKmtH4pxhtCoHqpWmT8YAmZxaewHgHAjLYsp1ZSe7zFYHj7C6ul7TjeLQeZD_YwD66t62wDmpe_HlB-TnBA-njbglfIsRLtXlnDzQkv5dTltRJ11BKBBypeeF6689rjcJIDEz9RWdc");
		jwk.setDp(
				"BwKfV3Akq5_MFZDFZCnW-wzl-CCo83WoZvnLQwCTeDv8uzluRSnm71I3QCLdhrqE2e9YkxvuxdBfpT_PI7Yz-FOKnu1R6HsJeDCjn12Sk3vmAktV2zb34MCdy7cpdTh_YVr7tss2u6vneTwrA86rZtu5Mbr1C1XsmvkxHQAdYo0");
		jwk.setDq(
				"h_96-mK1R_7glhsum81dZxjTnYynPbZpHziZjeeHcXYsXaaMwkOlODsWa7I9xXDoRwbKgB719rrmI2oKr6N3Do9U0ajaHF-NKJnwgjMd2w9cjz3_-kyNlxAr2v4IKhGNpmM5iIgOS1VZnOZ68m6_pbLBSp3nssTdlqvd0tIiTHU");
		jwk.setQi(
				"IYd7DHOhrWvxkwPQsRM2tOgrjbcrfvtQJipd-DlcxyVuuM9sQLdgjVk2oy26F0EmpScGLq2MowX7fhd_QJQ3ydy5cY7YIBi87w93IKLEdfnbJtoOPLUW0ITrJReOgo1cq9SbsxYawBgfp_gh6A5603k2-ZQwVK0JKSHuLFkuQ3U");
		return jwk;
	}
}
