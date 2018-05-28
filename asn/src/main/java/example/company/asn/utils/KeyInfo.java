package example.company.asn.utils;

public class KeyInfo {

	private String algorithmOID;
	private byte[] encoded;
	private String algorithmName;

	public KeyInfo(String algorithmOID, String algorithmName, byte[] encoded) {
		this.algorithmOID = algorithmOID;
		this.algorithmName = algorithmName;
		this.encoded = encoded;
	}

	public String getAlgorithmOID() {
		return algorithmOID;
	}

	public void setAlgorithmOID(String algorithmOID) {
		this.algorithmOID = algorithmOID;
	}

	public String getAlgorithmName() {
		return algorithmName;
	}

	public void setAlgorithmName(String algorithmName) {
		this.algorithmName = algorithmName;
	}

	public byte[] getEncoded() {
		return encoded;
	}

	public void setEncoded(byte[] encoded) {
		this.encoded = encoded;
	}

}
