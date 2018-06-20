package example.company.acme.v2;

import java.util.ArrayList;
import java.util.List;

public class AcmeOrder {

	private String status;
	private String expires;
	private List<AcmeAccountIdentifier> identifiers = new ArrayList<>();
	private List<String> authorizations;
	private String finalize;

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

	public List<AcmeAccountIdentifier> getIdentifiers() {
		return identifiers;
	}

	public void setIdentifiers(List<AcmeAccountIdentifier> identifiers) {
		this.identifiers = identifiers;
	}

	public List<String> getAuthorizations() {
		return authorizations;
	}

	public void setAuthorizations(List<String> authorizations) {
		this.authorizations = authorizations;
	}

	public String getFinalize() {
		return finalize;
	}

	public void setFinalize(String finalize) {
		this.finalize = finalize;
	}

}
