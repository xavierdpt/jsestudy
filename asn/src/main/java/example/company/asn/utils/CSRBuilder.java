package example.company.asn.utils;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.util.Map;

import example.company.asn.elements.AsnBitString;
import example.company.asn.elements.AsnContextSpecific;
import example.company.asn.elements.AsnInteger;
import example.company.asn.elements.AsnNull;
import example.company.asn.elements.AsnObjectIdentifier;
import example.company.asn.elements.AsnOctetString;
import example.company.asn.elements.AsnSequence;
import example.company.asn.elements.AsnSet;

public class CSRBuilder {

	private int version;
	private String name;
	private PublicKey publicKey;
	private byte[] subjectKeyIdentifier;

	public void setVersion(int version) {
		this.version = version;
	}

	public void setSubjectDNName(String name) {
		this.name = name;
	}

	public void setPublickKey(PublicKey publicKey) {
		this.publicKey = publicKey;
	}

	public void setSubjectKeyIdentifier(byte[] subjectKeyIdentifier) {
		this.subjectKeyIdentifier = subjectKeyIdentifier;
	}

	public byte[] encode(PrivateKey privateKey)
			throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {

		AsnInteger asnVersion = new AsnInteger(version);

		Map<String, String> nameMap = DistinguishedNameUtils.parse(name);

		AsnSequence subjectSequence = DistinguishedNameUtils.toSequence(nameMap);

		

		AsnObjectIdentifierUtils.class.getName();
		AsnObjectIdentifier publicKeyObjectIdentifier = new AsnObjectIdentifier(OIDS.RSA);

		AsnSequence publicKeyIdentifierSequence = new AsnSequence();
		publicKeyIdentifierSequence.getElements().add(publicKeyObjectIdentifier);
		publicKeyIdentifierSequence.getElements().add(new AsnNull());

		byte[] publicKeyBytes = AsnUtils.parse(publicKey.getEncoded()).asSequence().getBitString(1).toByteArray();
		AsnBitString publicKeyBitString = new AsnBitString(publicKeyBytes);

		AsnSequence publicKeySequence = new AsnSequence();
		publicKeySequence.getElements().add(publicKeyIdentifierSequence);
		publicKeySequence.getElements().add(publicKeyBitString);

		AsnObjectIdentifier extensionRequestIdentifier = new AsnObjectIdentifier(OIDS.EXTENSION_REQUEST);

		AsnObjectIdentifier subjectKeyObjectIdentifier = new AsnObjectIdentifier(OIDS.SUBJECT_KEY_IDENTIFIER);

		AsnOctetString subjectKeyIdentifierContent = new AsnOctetString(subjectKeyIdentifier);

		AsnSequence extensionRequestSetSequenceSequence = new AsnSequence();
		extensionRequestSetSequenceSequence.getElements().add(subjectKeyObjectIdentifier);
		extensionRequestSetSequenceSequence.getElements().add(subjectKeyIdentifierContent);

		AsnSequence extensionRequestSetSequence = new AsnSequence();
		extensionRequestSetSequence.getElements().add(extensionRequestSetSequenceSequence);

		AsnSet extensionRequestSet = new AsnSet();
		extensionRequestSet.getElements().add(extensionRequestSetSequence);

		AsnSequence contextSpecificSequence = new AsnSequence();
		contextSpecificSequence.getElements().add(extensionRequestIdentifier);
		contextSpecificSequence.getElements().add(extensionRequestSet);

		AsnContextSpecific contextSpecific = new AsnContextSpecific(0);
		contextSpecific.setValue(contextSpecificSequence.encode());

		AsnSequence seq1 = new AsnSequence();
		seq1.getElements().add(asnVersion);
		seq1.getElements().add(subjectSequence);
		seq1.getElements().add(publicKeySequence);
		seq1.getElements().add(contextSpecific);

		AsnObjectIdentifier signatureIdentifier = new AsnObjectIdentifier(OIDS.SHA256RSA);

		AsnSequence seq2 = new AsnSequence();
		seq2.getElements().add(signatureIdentifier);
		seq2.getElements().add(new AsnNull());

		Signature s = Signature.getInstance(OIDS.getLabel(OIDS.SHA256RSA));
		s.initSign(privateKey);
		s.update(AsnUtils.encode(seq1));
		byte[] signature = s.sign();

		AsnBitString bits = new AsnBitString(signature);

		AsnSequence csrSequence = new AsnSequence();
		csrSequence.getElements().add(seq1);
		csrSequence.getElements().add(seq2);
		csrSequence.getElements().add(bits);

		return AsnUtils.encode(csrSequence);
	}

	

}
