package example.company.jse.fiddle;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
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

import xdptdr.acme.jw.JWBase64;
import xdptdr.common.Common;

public class Fiddle45 {

	/* JWE rfc7516 Appendix A.1.2 -- A.1.3 ; CEK and RSA OAEP */

	@Test
	public void test()
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InstantiationException,
			IllegalAccessException, InvalidKeySpecException, IllegalBlockSizeException, BadPaddingException {

		// Content encryption key

		byte[] cek = Common.bytes(177, 161, 244, 128, 84, 143, 225, 115, 63, 180, 3, 255, 107, 154, 212, 246, 138, 7,
				110, 91, 112, 46, 34, 105, 47, 130, 203, 46, 122, 234, 64, 252);

		// RSA public and private key data

		String n64 = "oahUIoWw0K0usKNuOR6H4wkf4oBUXHTxRvgb48E-BVvxkeDNjbC4he8rUWcJoZmds2h7M70imEVhRU5djINXtqllXI4DFqcI1DgjT9LewND8MW2Krf3Spsk_ZkoFnilakGygTwpZ3uesH-PFABNIUYpOiN15dsQRkgr0vEhxN92i2asbOenSZeyaxziK72UwxrrKoExv6kc5twXTq4h-QChLOln0_mtUZwfsRaMStPs6mS6XrgxnxbWhojf663tuEQueGC-FCMfra36C9knDFGzKsNa7LZK2djYgyD3JR_MB_4NUJW_TqOQtwHYbxevoJArm-L5StowjzGy-_bq6Gw";
		String e64 = "AQAB";
		String d64 = "kLdtIj6GbDks_ApCSTYQtelcNttlKiOyPzMrXHeI-yk1F7-kpDxY4-WY5NWV5KntaEeXS1j82E375xxhWMHXyvjYecPT9fpwR_M9gV8n9Hrh2anTpTD93Dt62ypW3yDsJzBnTnrYu1iwWRgBKrEYY46qAZIrA2xAwnm2X7uGR1hghkqDp0Vqj3kbSCz1XyfCs6_LehBwtxHIyh8Ripy40p24moOAbgxVw3rxT_vlt3UVe4WO3JkJOzlpUf-KTVI2Ptgm-dARxTEtE-id-4OJr0h-K-VFs3VSndVTIznSxfyrj8ILL6MG_Uv8YAu7VILSB3lOW085-4qE3DzgrTjgyQ";

		// Construct public and private key pair
		KeyPair pair = getKeypair(n64, e64, d64);

		// Encrypt
		Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPPADDING");
		cipher.init(Cipher.ENCRYPT_MODE, pair.getPublic());
		byte[] encrypted = cipher.doFinal(cek);

		// Encryption result has some randomness, but we can at least check that we can
		// decrypt that result back with the private key

		cipher.init(Cipher.DECRYPT_MODE, pair.getPrivate());
		byte[] cek2 = cipher.doFinal(encrypted);
		Assert.assertArrayEquals(cek, cek2);

		// Now we can check that decrypting the sample encrypted content gives back the
		// cek as well

		byte[] expectedEncrypted = Common.bytes(56, 163, 154, 192, 58, 53, 222, 4, 105, 218, 136, 218, 29, 94, 203, 22,
				150, 92, 129, 94, 211, 232, 53, 89, 41, 60, 138, 56, 196, 216, 82, 98, 168, 76, 37, 73, 70, 7, 36, 8,
				191, 100, 136, 196, 244, 220, 145, 158, 138, 155, 4, 117, 141, 230, 199, 247, 173, 45, 182, 214, 74,
				177, 107, 211, 153, 11, 205, 196, 171, 226, 162, 128, 171, 182, 13, 237, 239, 99, 193, 4, 91, 219, 121,
				223, 107, 167, 61, 119, 228, 173, 156, 137, 134, 200, 80, 219, 74, 253, 56, 185, 91, 177, 34, 158, 89,
				154, 205, 96, 55, 18, 138, 43, 96, 218, 215, 128, 124, 75, 138, 243, 85, 25, 109, 117, 140, 26, 155,
				249, 67, 167, 149, 231, 100, 6, 41, 65, 214, 251, 232, 87, 72, 40, 182, 149, 154, 168, 31, 193, 126,
				215, 89, 28, 111, 219, 125, 182, 139, 235, 195, 197, 23, 234, 55, 58, 63, 180, 68, 202, 206, 149, 75,
				205, 248, 176, 67, 39, 178, 60, 98, 193, 32, 238, 122, 96, 158, 222, 57, 183, 111, 210, 55, 188, 215,
				206, 180, 166, 150, 166, 106, 250, 55, 229, 72, 40, 69, 214, 216, 104, 23, 40, 135, 212, 28, 127, 41,
				80, 175, 174, 168, 115, 171, 197, 89, 116, 92, 103, 246, 83, 216, 182, 176, 84, 37, 147, 35, 45, 219,
				172, 99, 226, 233, 73, 37, 124, 42, 72, 49, 242, 35, 127, 184, 134, 117, 114, 135, 206);

		byte[] cek3 = cipher.doFinal(expectedEncrypted);
		Assert.assertArrayEquals(cek, cek3);

		// We can also check that the byts and base64 given in the documentation match
		String expectedEncrypted64 = "OKOawDo13gRp2ojaHV7LFpZcgV7T6DVZKTyKOMTYUmKoTCVJRgckCL9kiMT03JGeipsEdY3mx_etLbbWSrFr05kLzcSr4qKAq7YN7e9jwQRb23nfa6c9d-StnImGyFDbSv04uVuxIp5Zms1gNxKKK2Da14B8S4rzVRltdYwam_lDp5XnZAYpQdb76FdIKLaVmqgfwX7XWRxv2322i-vDxRfqNzo_tETKzpVLzfiwQyeyPGLBIO56YJ7eObdv0je81860ppamavo35UgoRdbYaBcoh9QcfylQr66oc6vFWXRcZ_ZT2LawVCWTIy3brGPi6UklfCpIMfIjf7iGdXKHzg";
		Assert.assertEquals(expectedEncrypted64, JWBase64.encode(expectedEncrypted));
	}

	private KeyPair getKeypair(String n64, String e64, String d64)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		BigInteger modulus = Common.bigInteger(JWBase64.decode(n64));
		BigInteger publicExponent = Common.bigInteger(JWBase64.decode(e64));
		BigInteger privateExponent = Common.bigInteger(JWBase64.decode(d64));

		RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(modulus, publicExponent);
		RSAPrivateKeySpec privateKeySpec = new RSAPrivateKeySpec(modulus, privateExponent);

		KeyFactory kf = KeyFactory.getInstance("RSA");
		RSAPublicKey publicKey = (RSAPublicKey) kf.generatePublic(publicKeySpec);
		RSAPrivateKey privateKey = (RSAPrivateKey) kf.generatePrivate(privateKeySpec);

		return new KeyPair(publicKey, privateKey);
	}

}
