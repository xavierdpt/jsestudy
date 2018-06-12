package example.company.jse.fiddle;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Fiddle47Payload {

	private String iss = "joe";
	private long exp = 1300819380;
	private boolean root = true;

	public String getIss() {
		return iss;
	}

	public void setIss(String iss) {
		this.iss = iss;
	}

	public long getExp() {
		return exp;
	}

	public void setExp(long exp) {
		this.exp = exp;
	}

	@JsonProperty("http://example.com/is_root")
	public boolean getRoot() {
		return root;
	}

	public void setRoot(boolean root) {
		this.root = root;
	}

}
