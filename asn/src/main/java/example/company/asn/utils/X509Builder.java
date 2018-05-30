package example.company.asn.utils;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;
import java.util.Date;

import example.company.asn.AsnEncoding;
import example.company.asn.elements.Asn;
import example.company.asn.elements.AsnSequence;
import example.company.asn.elements.AsnUtcTime;
import example.company.tox.common.Common;

public class X509Builder {

	private byte[] publicKeyBSs;

	private int version = 3;

	private int serial;

	private String issuerCC;
	private String issuerST;
	private String issuerL;
	private String issuerO;
	private String issuerOU;
	private String issuerCN;

	private String subjectCC;
	private String subjectST;
	private String subjectL;
	private String subjectO;
	private String subjectOU;
	private String subjectCN;

	private String notBefore;
	private String notAfter;

	private String signatureOid = OIDS.SHA256RSA;
	private String rsaOid = OIDS.RSA;

	private byte[] authorityKeyIdentifier;
	private String extKeyUsage;
	private byte[] subjectKeyIdentifier;

	public byte[] getPublicKeyBSs() {
		return publicKeyBSs;
	}

	public void setPublicKeyBSs(byte[] publicKeyBSs) {
		this.publicKeyBSs = publicKeyBSs;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public int getSerial() {
		return serial;
	}

	public void setSerial(int serial) {
		this.serial = serial;
	}

	public String getSignatureOid() {
		return signatureOid;
	}

	public void setSignatureOid(String signatureOid) {
		this.signatureOid = signatureOid;
	}

	public String getIssuerCC() {
		return issuerCC;
	}

	public void setIssuerCC(String issuerCC) {
		this.issuerCC = issuerCC;
	}

	public String getIssuerST() {
		return issuerST;
	}

	public void setIssuerST(String issuerST) {
		this.issuerST = issuerST;
	}

	public String getIssuerL() {
		return issuerL;
	}

	public void setIssuerL(String issuerL) {
		this.issuerL = issuerL;
	}

	public String getIssuerO() {
		return issuerO;
	}

	public void setIssuerO(String issuerO) {
		this.issuerO = issuerO;
	}

	public String getIssuerOU() {
		return issuerOU;
	}

	public void setIssuerOU(String issuerOU) {
		this.issuerOU = issuerOU;
	}

	public String getIssuerCN() {
		return issuerCN;
	}

	public void setIssuerCN(String issuerCN) {
		this.issuerCN = issuerCN;
	}

	public String getSubjectCC() {
		return subjectCC;
	}

	public void setSubjectCC(String subjectCC) {
		this.subjectCC = subjectCC;
	}

	public String getSubjectST() {
		return subjectST;
	}

	public void setSubjectST(String subjectST) {
		this.subjectST = subjectST;
	}

	public String getSubjectL() {
		return subjectL;
	}

	public void setSubjectL(String subjectL) {
		this.subjectL = subjectL;
	}

	public String getSubjectO() {
		return subjectO;
	}

	public void setSubjectO(String subjectO) {
		this.subjectO = subjectO;
	}

	public String getSubjectOU() {
		return subjectOU;
	}

	public void setSubjectOU(String subjectOU) {
		this.subjectOU = subjectOU;
	}

	public String getSubjectCN() {
		return subjectCN;
	}

	public void setSubjectCN(String subjectCN) {
		this.subjectCN = subjectCN;
	}

	public String getNotBefore() {
		return notBefore;
	}

	public void setNotBefore(String notBefore) {
		this.notBefore = notBefore;
	}

	public void setNotBefore(Date date) {
		setNotBefore(AsnUtcTime.toString(date));
	}

	public String getNotAfter() {
		return notAfter;
	}

	public void setNotAfter(String notAfter) {
		this.notAfter = notAfter;
	}

	public void setNotAfter(Date date) {
		setNotAfter(AsnUtcTime.toString(date));
	}

	public String getRsaOid() {
		return rsaOid;
	}

	public void setRsaOid(String rsaOid) {
		this.rsaOid = rsaOid;
	}

	public byte[] getAuthorityKeyIdentifier() {
		return authorityKeyIdentifier;
	}

	public void setAuthorityKeyIdentifier(byte[] authorityKeyIdentifier) {
		this.authorityKeyIdentifier = authorityKeyIdentifier;
	}

	public String getExtKeyUsage() {
		return extKeyUsage;
	}

	public void setExtKeyUsage(String extKeyUsage) {
		this.extKeyUsage = extKeyUsage;
	}

	public byte[] getSubjectKeyIdentifier() {
		return subjectKeyIdentifier;
	}

	public void setSubjectKeyIdentifier(byte[] subjectKeyIdentifier) {
		this.subjectKeyIdentifier = subjectKeyIdentifier;
	}

	public byte[] encode(PrivateKey privateKey)
			throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {

		AsnSequence tbsRoot = Asn.seq(

				Asn.contextSpecific(0, Asn.integer(version - 1)),

				Asn.integer(serial),

				Asn.seq(Asn.oid(signatureOid), Asn.n()),

				Asn.seq(

						Asn.set(Asn.seq(Asn.oid(OIDS.COUNTRY_NAME), Asn.str(issuerCC))),

						Asn.set(Asn.seq(Asn.oid(OIDS.STATE_OR_PROVINCE_NAME), Asn.str(issuerST))),

						Asn.set(Asn.seq(Asn.oid(OIDS.LOCALITY_NAME), Asn.str(issuerL))),

						Asn.set(Asn.seq(Asn.oid(OIDS.ORGANIZATION_NAME), Asn.str(issuerO))),

						Asn.set(Asn.seq(Asn.oid(OIDS.ORGANIZATIONAL_UNIT_NAME), Asn.str(issuerOU))),

						Asn.set(Asn.seq(Asn.oid(OIDS.COMMON_NAME), Asn.str(issuerCN)))

				),

				Asn.seq(Asn.time(notBefore), Asn.time(notAfter)),

				Asn.seq(

						Asn.set(Asn.seq(Asn.oid(OIDS.COUNTRY_NAME), Asn.str(subjectCC))),

						Asn.set(Asn.seq(Asn.oid(OIDS.STATE_OR_PROVINCE_NAME), Asn.str(subjectST))),

						Asn.set(Asn.seq(Asn.oid(OIDS.LOCALITY_NAME), Asn.str(subjectL))),

						Asn.set(Asn.seq(Asn.oid(OIDS.ORGANIZATION_NAME), Asn.str(subjectO))),

						Asn.set(Asn.seq(Asn.oid(OIDS.ORGANIZATIONAL_UNIT_NAME), Asn.str(subjectOU))),

						Asn.set(Asn.seq(Asn.oid(OIDS.COMMON_NAME), Asn.str(subjectCN)))

				),

				Asn.seq(

						Asn.seq(Asn.oid(rsaOid), Asn.n()),

						Asn.bitstring(publicKeyBSs)

				),

				Asn.contextSpecific(3, Asn.seq(

						Asn.seq(

								Asn.oid(OIDS.AUTHORITY_KEY_IDENTIFIER),

								Asn.os(Asn.seq(Asn.cs(0, authorityKeyIdentifier, AsnEncoding.PRIMITIVE)))

						),

						Asn.seq(

								Asn.oid(OIDS.EXT_KEY_USAGE),

								Asn.os(Asn.seq(Asn.oid(extKeyUsage)))

						),

						Asn.seq(

								Asn.oid(OIDS.SUBJECT_KEY_IDENTIFIER),

								Asn.os(subjectKeyIdentifier)

						)

				))

		);

		Signature s = Signature.getInstance(OIDS.getLabel(signatureOid));
		s.initSign(privateKey);
		s.update(tbsRoot.encode());
		byte[] signature = s.sign();

		AsnSequence root = Asn.seq(

				tbsRoot,

				Asn.seq(Asn.oid(signatureOid), Asn.n()),

				Asn.bitstring(signature)

		);

		return root.encode();
	}

}
