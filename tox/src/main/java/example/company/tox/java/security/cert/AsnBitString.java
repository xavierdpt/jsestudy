package example.company.tox.java.security.cert;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import example.company.tox.common.Bytes;

public class AsnBitString extends AsnElement {

	private byte[] content;

	public AsnBitString() {
	}

	public AsnBitString(Bytes bytes) {
		super(bytes);
		content = contentBytes.toByteArray();
	}

	@XmlJavaTypeAdapter(HexBinaryAdapter.class)
	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	@Override
	protected boolean isGood() {
		return true;
	}
}
