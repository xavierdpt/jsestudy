package example.company.acme.v2.account;

import java.security.PrivateKey;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AcmeAccount {

	private long id;
	private String nonce;
	private String url;
	private PrivateKey privateKey;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public PrivateKey getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(PrivateKey privateKey) {
		this.privateKey = privateKey;
	}

}
