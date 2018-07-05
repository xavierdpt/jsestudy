package xpdtr.acme.gui.components;

public enum AcmeGuiActions {

	NONCE("New Nonce"),

	CREATE_ACCOUNT("New Account"),

	DEACTIVATE_ACCOUNT("Deactivate Account"),

	ORDER("New Order"),

	CHANGE_KEY("Change Key"),

	REVOKE_CERT("Revoke Cert"),

	ACCOUNT_DETAILS("Account Details"),

	AUTHORIZATION_DETAILS("Authorization details"),

	CHALLENGE_DETAIL("Get challenge detail"),

	RESPOND_CHALLENGE("Respond to challenge"),

	CREATE_KEY_PAIR("Create new key pair"),

	SAVE_KEY_PAIR("Save key pair"),

	LOAD_KEY_PAIR("Load key pair"),

	FOO("Foo"),

	BAR("Bar");

	private String label;

	private AcmeGuiActions(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

}
