package example.company.tox.java.security.cert;

import java.nio.charset.Charset;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

public class TaggedBytes {

	private String tag;
	private byte[] bytes;
	private String bytesAsString;

	public TaggedBytes(String s, byte[] b) {
		tag = s;
		bytes = b;
		bytesAsString = new String(bytes, Charset.forName("UTF-8"));
	}

	@XmlAttribute
	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	@XmlJavaTypeAdapter(value = HexBinaryAdapter.class)
	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

	public String getBytesAsString() {
		return bytesAsString;
	}

	public void setBytesAsString(String bytesAsString) {
		this.bytesAsString = bytesAsString;
	}

}
