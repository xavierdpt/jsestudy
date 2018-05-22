package example.company.tox.java.security.cert;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import example.company.tox.common.Bytes;

public class AsnSubjectIdentifier extends AsnElement {

	private byte[] value;

	public AsnSubjectIdentifier() {
	}

	public AsnSubjectIdentifier(Bytes bytes) {
		super(bytes);
		value = contentBytes.toByteArray();
	}

	@XmlJavaTypeAdapter(HexBinaryAdapter.class)
	public byte[] getValue() {
		return value;
	}

	public void setValue(byte[] value) {
		this.value = value;
	}

	@Override
	protected boolean isGood() {
		return true;
	}
}
