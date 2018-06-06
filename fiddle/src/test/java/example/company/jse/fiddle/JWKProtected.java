package example.company.jse.fiddle;

public class JWKProtected {

	private String alg;
	private JWK jwk;
	private String nonce;
	private String url;

	public String getAlg() {
		return alg;
	}

	public void setAlg(String alg) {
		this.alg = alg;
	}

	public JWK getJwk() {
		return jwk;
	}

	public void setJwk(JWK jwk) {
		this.jwk = jwk;
	}

	public String getNonce() {
		return nonce;
	}

	public void setNonce(String nonce) {
		this.nonce = nonce;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
