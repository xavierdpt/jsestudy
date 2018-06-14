package example.company.jse.fiddle.acme;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FlatJWS {

	private String payload;
	private String protekted;
	private JOSEHeader header;
	private String signature;

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

	@JsonProperty("protected")
	public String getProtekted() {
		return protekted;
	}

	public void setProtekted(String protekted) {
		this.protekted = protekted;
	}

	public JOSEHeader getHeader() {
		return header;
	}

	public void setHeader(JOSEHeader header) {
		this.header = header;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

}
