package example.company.asn.elements;

import java.util.List;

import example.company.asn.AsnClass;
import example.company.asn.AsnEncoding;
import example.company.asn.AsnTag;
import example.company.asn.utils.AsnUtils;
import example.company.tox.common.Bytes;

public class AsnBitString extends AsnElement {

	private byte[] content;

	public AsnBitString() {
	}

	public AsnBitString(Bytes bytes) {
		super(bytes);
		content = contentBytes.offset(1).toByteArray();
	}

	public AsnBitString(byte[] value) {
		content = value.clone();
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	@Override
	public void encode(List<Byte> bytes) {
		AsnUtils.addIdentifierBytes(bytes, AsnClass.UNIVERSAL, AsnEncoding.PRIMITIVE, AsnTag.BIT_STRING);
		AsnUtils.addLengthBytes(bytes, content.length + 1);
		AsnUtils.addBytes(bytes, new byte[] { 0 });
		AsnUtils.addBytes(bytes, content);
	}

}