package example.company.acme.v2;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AcmeDirectoryInfos2 {

	private String keyChange;
	private String newAccount;
	private String newNonce;
	private String newOrder;
	private String revokeCert;

	public String getKeyChange() {
		return keyChange;
	}

	public void setKeyChange(String keyChange) {
		this.keyChange = keyChange;
	}

	public String getNewAccount() {
		return newAccount;
	}

	public void setNewAccount(String newAccount) {
		this.newAccount = newAccount;
	}

	public String getNewNonce() {
		return newNonce;
	}

	public void setNewNonce(String newNonce) {
		this.newNonce = newNonce;
	}

	public String getNewOrder() {
		return newOrder;
	}

	public void setNewOrder(String newOrder) {
		this.newOrder = newOrder;
	}

	public String getRevokeCert() {
		return revokeCert;
	}

	public void setRevokeCert(String revokeCert) {
		this.revokeCert = revokeCert;
	}

	
}
