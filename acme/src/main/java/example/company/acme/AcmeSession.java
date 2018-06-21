package example.company.acme;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import example.company.acme.v2.AcmeDirectoryInfos2;
import example.company.acme.v2.AcmeOrderWithNonce;
import example.company.acme.v2.Authorization;
import example.company.acme.v2.Challenge;
import example.company.acme.v2.account.AcmeAccount;

public class AcmeSession {

	private ObjectMapper om = new ObjectMapper();

	private String version;
	private String url;
	private AcmeDirectoryInfos2 infos;
	private String nonce;
	private AcmeAccount account;
	private AcmeOrderWithNonce order;
	private Authorization authorization;
	private Challenge challenge;

	public AcmeSession() {
		om.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public AcmeDirectoryInfos2 getInfos() {
		return infos;
	}

	public void setInfos(AcmeDirectoryInfos2 infos) {
		this.infos = infos;
	}

	public ObjectMapper getOm() {
		return om;
	}

	public void setOm(ObjectMapper om) {
		this.om = om;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getNonce() {
		return nonce;
	}

	public void setNonce(String nonce) {
		this.nonce = nonce;
	}

	public AcmeAccount getAccount() {
		return account;
	}

	public void setAccount(AcmeAccount account) {
		this.account = account;
	}

	public AcmeOrderWithNonce getOrder() {
		return order;
	}

	public void setOrder(AcmeOrderWithNonce order) {
		this.order = order;
	}

	public Authorization getAuthorization() {
		return authorization;
	}

	public void setAuthorization(Authorization authorization) {
		this.authorization = authorization;
	}

	public Challenge getChallenge() {
		return challenge;
	}

	public void setChallenge(Challenge challenge) {
		this.challenge = challenge;
	}

}