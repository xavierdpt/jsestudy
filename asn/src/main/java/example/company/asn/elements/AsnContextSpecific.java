package example.company.asn.elements;

import java.util.List;

import example.company.asn.AsnClass;
import example.company.asn.AsnEncoding;
import example.company.asn.utils.AsnUtils;
import example.company.tox.common.Bytes;

public class AsnContextSpecific extends AsnElement {

	private long tag;
	private byte[] value;

	public AsnContextSpecific(long tag) {
		this.tag = tag;
	}

	public AsnContextSpecific(Bytes identifierBytes, Bytes lengthBytes, Bytes contentBytes) {
		tag = AsnUtils.parseTag(identifierBytes);
		value = contentBytes.toByteArray();
	}

	public AsnContextSpecific(int tag, byte[] value) {
		this.value = value;
		this.tag = tag;
	}

	public long getTag() {
		return tag;
	}

	public void setTag(long tag) {
		this.tag = tag;
	}

	public byte[] getValue() {
		return value;
	}

	public void setValue(byte[] value) {
		this.value = value;
	}

	@Override
	public void encode(List<Byte> bytes) {
		AsnUtils.addIdentifierBytes(bytes, AsnClass.CONTEXT_SPECIFIC, AsnEncoding.CONSTRUCTED, tag);
		AsnUtils.addLengthBytes(bytes, value.length);
		AsnUtils.addBytes(bytes, value);
	}

	public AsnElement get() {
		return AsnUtils.parse(value);
	}

	public AsnSequence getSequence() {
		return (AsnSequence) get();
	}
}
