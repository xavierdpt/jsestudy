package example.company.jse.fiddle.jwe;

public class Header {

	public static final String A128CBC = "A128CBC";
	public static final String A128KW = "A128KW";
	public static final String HS256 = "HS256";
	public static final String RSA1_5 = "RSA1_5";
	public static final String RSAOAEP = "RSA-OAEP";
	public static final String A256GCM = "A256GCM";

	private String alg;
	private String enc;

	public Header(String alg, String crypt, String mac) {
		this.alg = alg;
		enc = crypt + "-" + mac;
	}

	public String getAlg() {
		return alg;
	}

	public void setAlg(String alg) {
		this.alg = alg;
	}

	public String getEnc() {
		return enc;
	}

	public void setEnc(String enc) {
		this.enc = enc;
	}

}
