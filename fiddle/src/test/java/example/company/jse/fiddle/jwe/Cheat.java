package example.company.jse.fiddle.jwe;

public class Cheat {

	private String headerJSON;
	private byte[] expectedEncryptedCek;
	private boolean decryptEncryptedKey;

	public boolean getDecryptEncryptedKey() {
		return decryptEncryptedKey;
	}

	public void setDecryptEncryptedKey(boolean decryptEncryptedKey) {
		this.decryptEncryptedKey = decryptEncryptedKey;

	}

	public byte[] getExpectedEncryptedCek() {
		return expectedEncryptedCek;
	}

	public void setExpectedEncryptedCek(byte[] expectedEncryptedCek) {
		this.expectedEncryptedCek = expectedEncryptedCek;

	}

	public void setHeaderJSON(String headerJSON) {
		this.headerJSON = headerJSON;
	}

	public String getHeaderJSON() {
		return headerJSON;
	}

}
