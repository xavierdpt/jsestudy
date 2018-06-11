package example.company.jse.fiddle.jwe;

import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class InputForTest {

	private String text;
	private Header header;

	private ObjectMapper objectMapper;
	private String alg;
	private String encCrypt;
	private String encMac;
	private byte[] key;
	private byte[] iv;
	private Map<String, String> keyEncryptionParams;

	private Cheat cheat = new Cheat();

	private String headerJSON;

	public void setText(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public void setObjectMapper(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	public String getAlg() {
		return alg;
	}

	public String getEncCrypt() {
		return encCrypt;
	}

	public String getEncMac() {
		return encMac;
	}

	public void fillIn() throws JsonProcessingException {
		if (objectMapper == null) {
			objectMapper = new ObjectMapper();
		}
		if (cheat.getHeaderJSON() == null) {
			headerJSON = objectMapper.writeValueAsString(header);
		} else {
			headerJSON = cheat.getHeaderJSON();
		}
		if (alg == null) {
			alg = header.getAlg();
		}
		String[] headerEncParts = header.getEnc().split("-");
		if (encCrypt == null) {
			encCrypt = headerEncParts[0];
		}
		if (encMac == null) {
			encMac = headerEncParts[1];
		}
	}

	public void setKey(byte[] key) {
		this.key = key;

	}

	public void setIv(byte[] iv) {
		this.iv = iv;
	}

	public byte[] getKey() {
		return key;
	}

	public byte[] getIv() {
		return iv;
	}

	public void setKeyEncryptionParams(Map<String, String> KeyEncryptionParams) {
		keyEncryptionParams = KeyEncryptionParams;
	}

	public Map<String, String> getKeyEncryptionParams() {
		return keyEncryptionParams;
	}

	public Cheat getCheat() {
		return cheat;
	}

	public void setCheat(Cheat cheat) {
		this.cheat = cheat;
	}

	public String getHeaderJSON() {
		return headerJSON;
	}

	public void setHeaderJSON(String headerJSON) {
		this.headerJSON = headerJSON;
	}

}
