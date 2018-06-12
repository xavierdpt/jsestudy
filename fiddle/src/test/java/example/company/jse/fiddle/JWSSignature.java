package example.company.jse.fiddle;

public class JWSSignature {

	private String protekted;
	private JOSEHeader header;
	private String signature;

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
