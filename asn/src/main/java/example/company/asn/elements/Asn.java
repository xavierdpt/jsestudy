package example.company.asn.elements;

import java.math.BigInteger;
import java.nio.charset.Charset;

import example.company.asn.AsnEncoding;

public class Asn {

	public static AsnSequence seq(AsnElement... elements) {
		AsnSequence seq = new AsnSequence();
		for (AsnElement element : elements) {
			seq.getElements().add(element);
		}
		return seq;
	}

	public static AsnSet set(AsnElement... elements) {
		AsnSet set = new AsnSet();
		for (AsnElement element : elements) {
			set.getElements().add(element);
		}
		return set;
	}

	public static AsnElement cs(int tag, byte[] value) {
		return new AsnContextSpecific(tag, value);
	}

	public static AsnElement cs(int tag, byte[] value, AsnEncoding encoding) {
		return new AsnContextSpecific(tag, value, encoding);
	}

	public static AsnContextSpecific contextSpecific(int tag, byte[] value) {
		return new AsnContextSpecific(tag, value);
	}

	public static AsnElement contextSpecific(int tag, AsnElement element) {
		return contextSpecific(tag, element.encode());
	}

	public static AsnInteger integer(int value) {
		return new AsnInteger(BigInteger.valueOf(value));
	}

	public static AsnInteger integer(BigInteger value) {
		return new AsnInteger(value);
	}

	public static AsnObjectIdentifier oid(String oid) {
		return new AsnObjectIdentifier(oid);
	}

	public static AsnNull n() {
		return new AsnNull();
	}

	public static AsnPrintableString str(String value) {
		return new AsnPrintableString(value);
	}

	public static AsnUtcTime time(String value) {
		return new AsnUtcTime(value);
	}

	public static AsnBitString bitstring(byte[] bytes) {
		return new AsnBitString(bytes);
	}

	public static AsnOctetString os(byte[] bytes) {
		return new AsnOctetString(bytes);
	}

	public static AsnOctetString os(AsnElement element) {
		return os(element.encode());
	}

	public static AsnContextSpecific cs(int tag, AsnElement element) {
		return new AsnContextSpecific(tag, element.encode());
	}

	public static AsnBMPString bmpstring(String bytes) {
		return new AsnBMPString(bytes);
	}

	public static AsnBMPString bmpstring(byte[] bytes) {
		return new AsnBMPString(new String(bytes, Charset.forName("UTF-16")));
	}

}
