package example.company.jse.fiddle;

public class Payload {
	
	private boolean termsOfServiceAgreed = true;
	private String[] contact = new String[] { "xavierdpt@gmail.com" };

	public boolean getTermsOfServiceAgreed() {
		return termsOfServiceAgreed;
	}

	public void setTermsOfServiceAgreed(boolean termsOfServiceAgreed) {
		this.termsOfServiceAgreed = termsOfServiceAgreed;
	}

	public String[] getContact() {
		return contact;
	}

	public void setContact(String[] contact) {
		this.contact = contact;
	}

}
