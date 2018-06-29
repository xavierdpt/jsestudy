package example.company.acme.v2;

public class AcmeResponse<T> {

	private boolean failed;
	private String nonce;
	private T content;
	private String failureDetails;

	public AcmeResponse() {
	}

	public AcmeResponse(String nonce, T content) {
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

	public boolean isFailed() {
		return failed;
	}

	public void setFailed(boolean failed) {
		this.failed = failed;
	}

	public String getFailureDetails() {
		return failureDetails;
	}

	public void setFailureDetails(String failureDetails) {
		this.failureDetails = failureDetails;
	}

}