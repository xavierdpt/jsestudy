package example.company.acme.v2;

public class WithNonce<T> {

	private String nonce;
	private T content;

	public WithNonce(String nonce, T content) {
		this.nonce = nonce;
		this.content = content;
	}

	public String getNonce() {
		return nonce;
	}

	public void setNonce(String nonce) {
		this.nonce = nonce;
	}

	public T getContent() {
		return content;
	}

	public void setContent(T content) {
		this.content = content;
	}

}