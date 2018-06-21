package example.company.acme.v2;

import java.util.ArrayList;
import java.util.List;

public class Authorization {

	private AcmeAccountIdentifier identifier;
	private String status;
	private String expires;
	private List<Challenge> challenges = new ArrayList<>();

	public AcmeAccountIdentifier getIdentifier() {
		return identifier;
	}

	public void setIdentifier(AcmeAccountIdentifier identifier) {
		this.identifier = identifier;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getExpires() {
		return expires;
	}

	public void setExpires(String expires) {
		this.expires = expires;
	}

	public List<Challenge> getChallenges() {
		return challenges;
	}

	public void setChallenges(List<Challenge> challenges) {
		this.challenges = challenges;
	}

}