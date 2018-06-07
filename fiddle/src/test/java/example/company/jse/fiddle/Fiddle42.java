package example.company.jse.fiddle;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.junit.Assert;
import org.junit.Test;

import example.company.acme.v2.JWSBase64;
import example.company.tox.common.Common;

public class Fiddle42 {
	private static final String OAEPPADDING = "OAEPPADDING";

	@Test
	public void test() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
			InstantiationException, IllegalAccessException, InvalidKeySpecException, IllegalBlockSizeException {
		String text = "The true sign of intelligence is not knowledge but imagination.";
		byte[] textBytes = text.getBytes();

		String header = "{\"alg\":\"RSA-OAEP\",\"enc\":\"A256GCM\"}";
		byte[] headerBytes = header.getBytes();
		String headerb64 = JWSBase64.encode(headerBytes);

		Assert.assertArrayEquals(Common.bytes(84, 104, 101, 32, 116, 114, 117, 101, 32, 115, 105, 103, 110, 32, 111,
				102, 32, 105, 110, 116, 101, 108, 108, 105, 103, 101, 110, 99, 101, 32, 105, 115, 32, 110, 111, 116, 32,
				107, 110, 111, 119, 108, 101, 100, 103, 101, 32, 98, 117, 116, 32, 105, 109, 97, 103, 105, 110, 97, 116,
				105, 111, 110, 46), textBytes);

		Assert.assertEquals("eyJhbGciOiJSU0EtT0FFUCIsImVuYyI6IkEyNTZHQ00ifQ", headerb64);

		byte[] cek = Common.bytes(177, 161, 244, 128, 84, 143, 225, 115, 63, 180, 3, 255, 107, 154, 212, 246, 138, 7,
				110, 91, 112, 46, 34, 105, 47, 130, 203, 46, 122, 234, 64, 252);

		String n64 = "oahUIoWw0K0usKNuOR6H4wkf4oBUXHTxRvgb48E-BVvxkeDNjbC4he8rUWcJoZmds2h7M70imEVhRU5djINXtqllXI4DFqcI1DgjT9LewND8MW2Krf3Spsk_ZkoFnilakGygTwpZ3uesH-PFABNIUYpOiN15dsQRkgr0vEhxN92i2asbOenSZeyaxziK72UwxrrKoExv6kc5twXTq4h-QChLOln0_mtUZwfsRaMStPs6mS6XrgxnxbWhojf663tuEQueGC-FCMfra36C9knDFGzKsNa7LZK2djYgyD3JR_MB_4NUJW_TqOQtwHYbxevoJArm-L5StowjzGy-_bq6Gw";
		String e64 = "AQAB";
		String d64 = "kLdtIj6GbDks_ApCSTYQtelcNttlKiOyPzMrXHeI-yk1F7-kpDxY4-WY5NWV5KntaEeXS1j82E375xxhWMHXyvjYecPT9fpwR_M9gV8n9Hrh2anTpTD93Dt62ypW3yDsJzBnTnrYu1iwWRgBKrEYY46qAZIrA2xAwnm2X7uGR1hghkqDp0Vqj3kbSCz1XyfCs6_LehBwtxHIyh8Ripy40p24moOAbgxVw3rxT_vlt3UVe4WO3JkJOzlpUf-KTVI2Ptgm-dARxTEtE-id-4OJr0h-K-VFs3VSndVTIznSxfyrj8ILL6MG_Uv8YAu7VILSB3lOW085-4qE3DzgrTjgyQ";
		String p64 = "1r52Xk46c-LsfB5P442p7atdPUrxQSy4mti_tZI3Mgf2EuFVbUoDBvaRQ-SWxkbkmoEzL7JXroSBjSrK3YIQgYdMgyAEPTPjXv_hI2_1eTSPVZfzL0lffNn03IXqWF5MDFuoUYE0hzb2vhrlN_rKrbfDIwUbTrjjgieRbwC6Cl0";
		String q64 = "wLb35x7hmQWZsWJmB_vle87ihgZ19S8lBEROLIsZG4ayZVe9Hi9gDVCOBmUDdaDYVTSNx_8Fyw1YYa9XGrGnDew00J28cRUoeBB_jKI1oma0Orv1T9aXIWxKwd4gvxFImOWr3QRL9KEBRzk2RatUBnmDZJTIAfwTs0g68UZHvtc";
		String dp64 = "ZK-YwE7diUh0qR1tR7w8WHtolDx3MZ_OTowiFvgfeQ3SiresXjm9gZ5KLhMXvo-uz-KUJWDxS5pFQ_M0evdo1dKiRTjVw_x4NyqyXPM5nULPkcpU827rnpZzAJKpdhWAgqrXGKAECQH0Xt4taznjnd_zVpAmZZq60WPMBMfKcuE";
		String dq64 = "Dq0gfgJ1DdFGXiLvQEZnuKEN0UUmsJBxkjydc3j4ZYdBiMRAy86x0vHCjywcMlYYg4yoC4YZa9hNVcsjqA3FeiL19rk8g6Qn29Tt0cj8qqyFpz9vNDBUfCAiJVeESOjJDZPYHdHY8v1b-o-Z2X5tvLx-TCekf7oxyeKDUqKWjis";
		String qi64 = "VIMpMYbPf47dT1w_zDUXfPimsSegnMOA1zTaX7aGk_8urY6R8-ZW1FxU7AlWAyLWybqq6t16VFd7hQd0y6flUK4SlOydB61gwanOsXGOAOv82cHq0E3eL4HrtZkUuKvnPrMnsUUFlfUdybVzxyjz9JF_XyaY14ardLSjf4L_FNY";

		Map<String, String> map = new HashMap<>();
		map.put("n64", n64);
		map.put("e64", e64);
		map.put("d64", d64);
		map.put("p64", p64);
		map.put("q64", q64);
		map.put("dp64", dp64);
		map.put("dq64", dq64);
		map.put("qi64", qi64);

		SecretKeySpec sks = new SecretKeySpec(cek, "RAW");

		byte[] expectedEncryptedKey = Common.bytes(56, 163, 154, 192, 58, 53, 222, 4, 105, 218, 136, 218, 29, 94, 203,
				22, 150, 92, 129, 94, 211, 232, 53, 89, 41, 60, 138, 56, 196, 216, 82, 98, 168, 76, 37, 73, 70, 7, 36,
				8, 191, 100, 136, 196, 244, 220, 145, 158, 138, 155, 4, 117, 141, 230, 199, 247, 173, 45, 182, 214, 74,
				177, 107, 211, 153, 11, 205, 196, 171, 226, 162, 128, 171, 182, 13, 237, 239, 99, 193, 4, 91, 219, 121,
				223, 107, 167, 61, 119, 228, 173, 156, 137, 134, 200, 80, 219, 74, 253, 56, 185, 91, 177, 34, 158, 89,
				154, 205, 96, 55, 18, 138, 43, 96, 218, 215, 128, 124, 75, 138, 243, 85, 25, 109, 117, 140, 26, 155,
				249, 67, 167, 149, 231, 100, 6, 41, 65, 214, 251, 232, 87, 72, 40, 182, 149, 154, 168, 31, 193, 126,
				215, 89, 28, 111, 219, 125, 182, 139, 235, 195, 197, 23, 234, 55, 58, 63, 180, 68, 202, 206, 149, 75,
				205, 248, 176, 67, 39, 178, 60, 98, 193, 32, 238, 122, 96, 158, 222, 57, 183, 111, 210, 55, 188, 215,
				206, 180, 166, 150, 166, 106, 250, 55, 229, 72, 40, 69, 214, 216, 104, 23, 40, 135, 212, 28, 127, 41,
				80, 175, 174, 168, 115, 171, 197, 89, 116, 92, 103, 246, 83, 216, 182, 176, 84, 37, 147, 35, 45, 219,
				172, 99, 226, 233, 73, 37, 124, 42, 72, 49, 242, 35, 127, 184, 134, 117, 114, 135, 206);

		map.forEach((ka, va) -> {
			map.forEach((kb, vb) -> {
				try {
					byte[] bytesA = JWSBase64.decode(va);
					byte[] bytesB = JWSBase64.decode(vb);
					BigInteger ba = BigInteger.ZERO;
					for (int i = bytesA.length - 1; i >= 0; --i) {
						ba = ba.shiftLeft(8).add(BigInteger.valueOf(bytesA[i]));
					}
					BigInteger bb = BigInteger.ZERO;
					for (int i = bytesB.length - 1; i >= 0; --i) {
						bb = bb.shiftLeft(8).add(BigInteger.valueOf(bytesB[i]));
					}

					RSAPublicKeySpec keySpec = new RSAPublicKeySpec(ba, bb);

					KeyFactory kf = KeyFactory.getInstance("RSA");
					RSAPublicKey key = (RSAPublicKey) kf.generatePublic(keySpec);

					Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPPADDING");
					cipher.init(Cipher.ENCRYPT_MODE, key, OAEPParameterSpec.DEFAULT,
							new SecureRandom(Common.bytes(227, 197, 117, 252, 2, 219, 233, 68, 180, 225, 77, 219)));

					// byte[] wrapped = cipher.wrap(sks);
					byte[] wrapped = cipher.doFinal(cek);
					System.out.println("Got something !!! (" + ka + "," + kb + ")");
					
					cipher = Cipher.getInstance("RSA/ECB/OAEPPADDING");
					cipher.init(Cipher.DECRYPT_MODE, key);
					byte[] toto = cipher.doFinal(expectedEncryptedKey);
					
					try {
						Assert.assertArrayEquals(cek, toto);
						System.out.println("And it matches here !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
					} catch (AssertionError e) {
						System.out.println(e.getMessage());
					}
					
					try {
						Assert.assertArrayEquals(expectedEncryptedKey, wrapped);
						System.out.println("And it matches !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
					} catch (AssertionError e) {
						System.out.println(e.getMessage());
					}
				} catch (Exception ex) {
					 System.out.println(ex.getMessage());
				}
			});
		});

		String expectedEncryptedKey64 = "OKOawDo13gRp2ojaHV7LFpZcgV7T6DVZKTyKOMTYUmKoTCVJRgckCL9kiMT03JGeipsEdY3mx_etLbbWSrFr05kLzcSr4qKAq7YN7e9jwQRb23nfa6c9d-StnImGyFDbSv04uVuxIp5Zms1gNxKKK2Da14B8S4rzVRltdYwam_lDp5XnZAYpQdb76FdIKLaVmqgfwX7XWRxv2322i-vDxRfqNzo_tETKzpVLzfiwQyeyPGLBIO56YJ7eObdv0je81860ppamavo35UgoRdbYaBcoh9QcfylQr66oc6vFWXRcZ_ZT2LawVCWTIy3brGPi6UklfCpIMfIjf7iGdXKHzg";

		Assert.assertEquals(expectedEncryptedKey64, JWSBase64.encode(expectedEncryptedKey));

	}
}
