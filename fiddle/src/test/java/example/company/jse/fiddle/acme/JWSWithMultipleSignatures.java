package example.company.jse.fiddle.acme;

import java.util.ArrayList;
import java.util.List;

public class JWSWithMultipleSignatures {
	
	private String payload;
	private List<JWSSignature> signatures = new ArrayList<>();

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

	public List<JWSSignature> getSignatures() {
		return signatures;
	}

	public void setSignatures(List<JWSSignature> signatures) {
		this.signatures = signatures;
	}

}
