package example.company.tox.java.security.cert;

import java.math.BigInteger;
import java.security.Principal;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.security.auth.x500.X500Principal;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import example.company.tox.common.Bytes;
import example.company.tox.java.lang.ExceptionDescription;
import example.company.tox.java.security.KeyDescription;
import example.company.tox.java.security.PrincipalDescription;
import example.company.tox.java.security.auth.x500.X500PrincipalDescription;

@XmlRootElement(name = "certificate")
@XmlType(propOrder = { "type", "x509", "publicKey", "encoded", "encodedException", "x509Version", "x509Signature",
		"x509NotBefore", "x509NotAfter", "x509SigAlgName", "x509SigAlgOID", "x500SerialNumber",

		"x500IssuerDN", "x500SubjectDN",

		"x509BasicConstraints",

		"x509IssuerUniqueID",

		"x509KeyUsage",

		"x509SigAlgParams",

		"x509SubjectUniqueID",

		"x500IssuerX500Principal",

		"x500SubjectX500Principal",

		"x500ExtendedKeyUsage",

		"x500ExtendedKeyUsageException",

		"x500IssuerAlternativeNames",

		"x500IssuerAlternativeNamesException",

		"x500SubjectAlternativeNames",

		"x500SubjectAlternativeNamesException",

		"x500TBSCertificate",

		"x500TBSCertificateException",

		"asnElement",

		"document"

})
public class CertificateDescription {

	private String type;

	private KeyDescription publicKey;
	private byte[] encoded;
	private ExceptionDescription encodedException;
	private AsnElement asnElement;
	private boolean isX509;
	private Integer x509BasicConstraints;
	private boolean[] x509IssuerUniqueID;
	private boolean[] x509KeyUsage;
	private Date x509NotAfter;
	private Date x509NotBefore;
	private String x509SigAlgName;
	private String x509SigAlgOID;
	private byte[] x509SigAlgParams;
	private byte[] x509Signature;
	private boolean[] x509SubjectUniqueID;
	private Integer x509Version;
	private PrincipalDescription x500IssuerDN;
	private PrincipalDescription x500SubjectDN;
	private X500PrincipalDescription x500IssuerX500Principal;
	private X500PrincipalDescription x500SubjectX500Principal;
	private BigInteger x500SerialNumber;
	private List<String> x500ExtendedKeyUsage;
	private ExceptionDescription x500ExtendedKeyUsageException;
	private AlternativeNamesDescription x500IssuerAlternativeNames;
	private ExceptionDescription x500IssuerAlternativeNamesException;
	private AlternativeNamesDescription x500SubjectAlternativeNames;
	private ExceptionDescription x500SubjectAlternativeNamesException;
	private byte[] x500TBSCertificate;
	private ExceptionDescription x500TBSCertificateException;

	private Object document;

	public CertificateDescription() {
	}

	public CertificateDescription(Certificate certificate) {
		type = certificate.getType();
		publicKey = new KeyDescription(certificate.getPublicKey());
		try {
			encoded = certificate.getEncoded();
			asnElement = AsnUtils.parse(new Bytes(encoded));
		} catch (CertificateEncodingException e) {
			encodedException = new ExceptionDescription(e);
		}
		if (certificate instanceof X509Certificate) {
			isX509 = true;
			X509Certificate xcertificate = (X509Certificate) certificate;

			x509BasicConstraints = xcertificate.getBasicConstraints();
			x509IssuerUniqueID = xcertificate.getIssuerUniqueID();
			x509KeyUsage = xcertificate.getKeyUsage();
			x509NotAfter = xcertificate.getNotAfter();
			x509NotBefore = xcertificate.getNotBefore();
			x509SigAlgName = xcertificate.getSigAlgName();
			x509SigAlgOID = xcertificate.getSigAlgOID();
			x509SigAlgParams = xcertificate.getSigAlgParams();
			x509Signature = xcertificate.getSignature();
			x509SubjectUniqueID = xcertificate.getSubjectUniqueID();
			x509Version = xcertificate.getVersion();
			Principal issuerDN = xcertificate.getIssuerDN();
			if (issuerDN != null) {
				x500IssuerDN = new PrincipalDescription(issuerDN);
			}
			Principal subjectDN = xcertificate.getSubjectDN();
			if (subjectDN != null) {
				x500SubjectDN = new PrincipalDescription(subjectDN);
			}

			X500Principal issuerX500Principal = xcertificate.getIssuerX500Principal();
			if (issuerX500Principal != null) {
				x500IssuerX500Principal = new X500PrincipalDescription(issuerX500Principal);
			}
			X500Principal subjectX500Principal = xcertificate.getSubjectX500Principal();
			if (subjectX500Principal != null) {
				x500SubjectX500Principal = new X500PrincipalDescription(subjectX500Principal);
			}

			x500SerialNumber = xcertificate.getSerialNumber();

			try {
				x500ExtendedKeyUsage = xcertificate.getExtendedKeyUsage();
			} catch (CertificateParsingException e) {
				x500ExtendedKeyUsageException = new ExceptionDescription(e);
			}

			try {
				Collection<List<?>> issuerAlternativeNames = xcertificate.getIssuerAlternativeNames();
				if (issuerAlternativeNames != null) {
					x500IssuerAlternativeNames = new AlternativeNamesDescription(issuerAlternativeNames);
				}
			} catch (CertificateParsingException e) {
				x500IssuerAlternativeNamesException = new ExceptionDescription(e);
			}

			try {
				Collection<List<?>> subjectAlternativeNames = xcertificate.getSubjectAlternativeNames();
				if (subjectAlternativeNames != null) {
					x500SubjectAlternativeNames = new AlternativeNamesDescription(subjectAlternativeNames);
				}
			} catch (CertificateParsingException e) {
				x500SubjectAlternativeNamesException = new ExceptionDescription(e);
			}

			try {
				x500TBSCertificate = xcertificate.getTBSCertificate();
			} catch (CertificateEncodingException e) {
				x500TBSCertificateException = new ExceptionDescription(e);
			}

		}

		try {
			Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			Element hello = doc.createElement("hello");
			doc.appendChild(hello);
			
			document=hello;
			hello.appendChild(doc.createTextNode("data"));
		} catch (ParserConfigurationException e) {

		}

	}

	@XmlAttribute
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@XmlElement(name = "publicKey")
	public KeyDescription getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(KeyDescription publicKey) {
		this.publicKey = publicKey;
	}

	@XmlJavaTypeAdapter(value = HexBinaryAdapter.class)
	public byte[] getEncoded() {
		return encoded;
	}

	public void setEncoded(byte[] encoded) {
		this.encoded = encoded;
	}

	public ExceptionDescription getEncodedException() {
		return encodedException;
	}

	public void setEncodedException(ExceptionDescription encodedException) {
		this.encodedException = encodedException;
	}

	public AsnElement getAsnElement() {
		return asnElement;
	}

	public void setAsnElement(AsnElement asnElement) {
		this.asnElement = asnElement;
	}

	@XmlAttribute
	public boolean isX509() {
		return isX509;
	}

	public void setX509(boolean isX509) {
		this.isX509 = isX509;
	}

	public Integer getX509BasicConstraints() {
		return x509BasicConstraints;
	}

	public void setX509BasicConstraints(Integer x509BasicConstraints) {
		this.x509BasicConstraints = x509BasicConstraints;
	}

	public boolean[] getX509IssuerUniqueID() {
		return x509IssuerUniqueID;
	}

	public void setX509IssuerUniqueID(boolean[] x509IssuerUniqueID) {
		this.x509IssuerUniqueID = x509IssuerUniqueID;
	}

	public boolean[] getX509KeyUsage() {
		return x509KeyUsage;
	}

	public void setX509KeyUsage(boolean[] x509KeyUsage) {
		this.x509KeyUsage = x509KeyUsage;
	}

	@XmlJavaTypeAdapter(DataTimeAdapter.class)
	public Date getX509NotAfter() {
		return x509NotAfter;
	}

	public void setX509NotAfter(Date x509NotAfter) {
		this.x509NotAfter = x509NotAfter;
	}

	@XmlJavaTypeAdapter(DataTimeAdapter.class)
	public Date getX509NotBefore() {
		return x509NotBefore;
	}

	public void setX509NotBefore(Date x509NotBefore) {
		this.x509NotBefore = x509NotBefore;
	}

	public String getX509SigAlgName() {
		return x509SigAlgName;
	}

	public void setX509SigAlgName(String x509SigAlgName) {
		this.x509SigAlgName = x509SigAlgName;
	}

	public String getX509SigAlgOID() {
		return x509SigAlgOID;
	}

	public void setX509SigAlgOID(String x509SigAlgOID) {
		this.x509SigAlgOID = x509SigAlgOID;
	}

	@XmlJavaTypeAdapter(HexBinaryAdapter.class)
	public byte[] getX509SigAlgParams() {
		return x509SigAlgParams;
	}

	public void setX509SigAlgParams(byte[] x509SigAlgParams) {
		this.x509SigAlgParams = x509SigAlgParams;
	}

	@XmlJavaTypeAdapter(HexBinaryAdapter.class)
	public byte[] getX509Signature() {
		return x509Signature;
	}

	public void setX509Signature(byte[] x509Signature) {
		this.x509Signature = x509Signature;
	}

	public boolean[] getX509SubjectUniqueID() {
		return x509SubjectUniqueID;
	}

	public void setX509SubjectUniqueID(boolean[] x509SubjectUniqueID) {
		this.x509SubjectUniqueID = x509SubjectUniqueID;
	}

	public Integer getX509Version() {
		return x509Version;
	}

	public void setX509Version(Integer x509Version) {
		this.x509Version = x509Version;
	}

	public PrincipalDescription getX500IssuerDN() {
		return x500IssuerDN;
	}

	public void setX500IssuerDN(PrincipalDescription x500IssuerDN) {
		this.x500IssuerDN = x500IssuerDN;
	}

	public PrincipalDescription getX500SubjectDN() {
		return x500SubjectDN;
	}

	public void setX500SubjectDN(PrincipalDescription x500SubjectDN) {
		this.x500SubjectDN = x500SubjectDN;
	}

	public X500PrincipalDescription getX500IssuerX500Principal() {
		return x500IssuerX500Principal;
	}

	public void setX500IssuerX500Principal(X500PrincipalDescription x500IssuerX500Principal) {
		this.x500IssuerX500Principal = x500IssuerX500Principal;
	}

	public X500PrincipalDescription getX500SubjectX500Principal() {
		return x500SubjectX500Principal;
	}

	public void setX500SubjectX500Principal(X500PrincipalDescription x500SubjectX500Principal) {
		this.x500SubjectX500Principal = x500SubjectX500Principal;
	}

	public BigInteger getX500SerialNumber() {
		return x500SerialNumber;
	}

	public void setX500SerialNumber(BigInteger x500SerialNumber) {
		this.x500SerialNumber = x500SerialNumber;
	}

	public List<String> getX500ExtendedKeyUsage() {
		return x500ExtendedKeyUsage;
	}

	public void setX500ExtendedKeyUsage(List<String> x500ExtendedKeyUsage) {
		this.x500ExtendedKeyUsage = x500ExtendedKeyUsage;
	}

	public ExceptionDescription getX500ExtendedKeyUsageException() {
		return x500ExtendedKeyUsageException;
	}

	public void setX500ExtendedKeyUsageException(ExceptionDescription x500ExtendedKeyUsageException) {
		this.x500ExtendedKeyUsageException = x500ExtendedKeyUsageException;
	}

	public AlternativeNamesDescription getX500IssuerAlternativeNames() {
		return x500IssuerAlternativeNames;
	}

	public void setX500IssuerAlternativeNames(AlternativeNamesDescription x500IssuerAlternativeNames) {
		this.x500IssuerAlternativeNames = x500IssuerAlternativeNames;
	}

	public ExceptionDescription getX500IssuerAlternativeNamesException() {
		return x500IssuerAlternativeNamesException;
	}

	public void setX500IssuerAlternativeNamesException(ExceptionDescription x500IssuerAlternativeNamesException) {
		this.x500IssuerAlternativeNamesException = x500IssuerAlternativeNamesException;
	}

	public AlternativeNamesDescription getX500SubjectAlternativeNames() {
		return x500SubjectAlternativeNames;
	}

	public void setX500SubjectAlternativeNames(AlternativeNamesDescription x500SubjectAlternativeNames) {
		this.x500SubjectAlternativeNames = x500SubjectAlternativeNames;
	}

	public ExceptionDescription getX500SubjectAlternativeNamesException() {
		return x500SubjectAlternativeNamesException;
	}

	public void setX500SubjectAlternativeNamesException(ExceptionDescription x500SubjectAlternativeNamesException) {
		this.x500SubjectAlternativeNamesException = x500SubjectAlternativeNamesException;
	}

	@XmlJavaTypeAdapter(HexBinaryAdapter.class)
	public byte[] getX500TBSCertificate() {
		return x500TBSCertificate;
	}

	public void setX500TBSCertificate(byte[] x500tbsCertificate) {
		x500TBSCertificate = x500tbsCertificate;
	}

	public ExceptionDescription getX500TBSCertificateException() {
		return x500TBSCertificateException;
	}

	public void setX500TBSCertificateException(ExceptionDescription x500tbsCertificateException) {
		x500TBSCertificateException = x500tbsCertificateException;
	}

	public Object getDocument() {
		return document;
	}

	public void setDocument(Object document) {
		this.document = document;
	}

}
