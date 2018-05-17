package example.company.tox.java.security.cert;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlSeeAlso({ AsnSequence.class, AsnContextSpecific.class, AsnInteger.class })
public class AsnElement {

	private int length;
	private Bytes identifierBytes;
	private Bytes lengthBytes;
	private Bytes contentBytes;

	public AsnElement() {
	}

	public AsnElement(Bytes bytes) {
		identifierBytes = AsnUtils.getIdentifierBytes(bytes);
		lengthBytes = AsnUtils.getLengthBytes(bytes.offset(identifierBytes));
		length = AsnUtils.parseLengthBytes(lengthBytes);
		contentBytes = bytes.subStartLen(identifierBytes.length() + lengthBytes.length(), length);
	}

	@XmlAttribute
	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	@XmlJavaTypeAdapter(value = ByteAdapter.class)
	public Bytes getIdentifierBytes() {
		return identifierBytes;
	}

	public void setIdentifierBytes(Bytes identifierBytes) {
		this.identifierBytes = identifierBytes;
	}

	@XmlJavaTypeAdapter(value = ByteAdapter.class)
	public Bytes getLengthBytes() {
		return lengthBytes;
	}

	public void setLengthBytes(Bytes lengthBytes) {
		this.lengthBytes = lengthBytes;
	}

	@XmlJavaTypeAdapter(value = ByteAdapter.class)
	public Bytes getContentBytes() {
		return contentBytes;
	}

	public void setContentBytes(Bytes contentBytes) {
		this.contentBytes = contentBytes;
	}

}
