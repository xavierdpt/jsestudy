package example.company.tox.java.security.cert;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlSeeAlso({ AsnSequence.class, AsnContextSpecific.class, AsnInteger.class, AsnObjectIdentifier.class, AsnNull.class,
		AsnSet.class, AsnPrintableString.class })
public class AsnElement {

	protected Integer length;
	protected Bytes identifierBytes;
	protected Bytes lengthBytes;
	protected Bytes contentBytes;

	public AsnElement() {
	}

	public AsnElement(Bytes bytes) {
		identifierBytes = AsnUtils.getIdentifierBytes(bytes);
		lengthBytes = AsnUtils.getLengthBytes(bytes.offset(identifierBytes));
		length = AsnUtils.parseLengthBytes(lengthBytes);
		contentBytes = bytes.subStartLen(identifierBytes.length() + lengthBytes.length(), length);
	}

	@XmlAttribute
	public Integer getLength() {
		if (isGood()) {
			return null;
		}
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	@XmlJavaTypeAdapter(value = ByteAdapter.class)
	public Bytes getIdentifierBytes() {
		if (isGood()) {
			return null;
		}
		return identifierBytes;
	}

	public void setIdentifierBytes(Bytes identifierBytes) {
		this.identifierBytes = identifierBytes;
	}

	@XmlJavaTypeAdapter(value = ByteAdapter.class)
	public Bytes getLengthBytes() {
		if (isGood()) {
			return null;
		}
		return lengthBytes;
	}

	public void setLengthBytes(Bytes lengthBytes) {
		this.lengthBytes = lengthBytes;
	}

	@XmlJavaTypeAdapter(value = ByteAdapter.class)
	public Bytes getContentBytes() {
		if (isGood()) {
			return null;
		}
		return contentBytes;
	}

	public void setContentBytes(Bytes contentBytes) {
		this.contentBytes = contentBytes;
	}

	protected boolean isGood() {
		return false;
	}
}
