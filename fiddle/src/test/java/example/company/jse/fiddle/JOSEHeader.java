package example.company.jse.fiddle;

public class JOSEHeader {
	
	private String alg;
	private String kid;

	public JOSEHeader() {
	}

	public JOSEHeader(String alg) {
		this.alg = alg;
	}

	public JOSEHeader(String alg, String kid) {
		this.alg = alg;
		this.kid = kid;
	}

	public String getAlg() {
		return alg;
	}

	public void setAlg(String alg) {
		this.alg = alg;
	}

	public String getKid() {
		return kid;
	}

	public void setKid(String kid) {
		this.kid = kid;
	}

}
