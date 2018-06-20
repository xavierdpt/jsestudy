package example.company.acme.v2;

public class AcmeOrderWithNonce {

	private String nonce;
	private AcmeOrder content;

	public String getNonce() {
		return nonce;
	}

	public void setNonce(String nonce) {
		this.nonce = nonce;
	}

	public AcmeOrder getContent() {
		return content;
	}

	public void setContent(AcmeOrder content) {
		this.content = content;
	}

}
