package example.company.tox.java.security.cert;

import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import example.company.tox.common.Tox;
import example.company.tox.java.lang.ExceptionMarshaller;
import example.company.tox.java.security.KeyMarshaller;
import example.company.tox.java.security.PrincipalDescription;
import example.company.tox.java.security.auth.x500.X500PrincipalDescription;
import example.company.tox.proxy.AsnElementMarshaller;

@XmlRootElement(name = "certificate")
public class CertificateMarshaller {

	private CertificateMarshaller() {
	}

	public static void marshall(Document document, Element root2, String name, Certificate certificate) {

		Element root = Tox.appendChild(document, root2, name);

		Tox.setAttribute(root, "type", certificate.getType());

		KeyMarshaller.marshal(document, root, "publicKey", certificate.getPublicKey());

		byte[] encoded = null;
		try {
			encoded = certificate.getEncoded();
			Tox.appendChild(document, root, "encoded", encoded);
		} catch (CertificateEncodingException e) {
			ExceptionMarshaller.marshal(document, root, "encoded-exception", e);
		}

		if (certificate instanceof X509Certificate) {
			X509Certificate xcertificate = (X509Certificate) certificate;
			Tox.setAttribute(root, "version", xcertificate.getVersion());
			Tox.setAttribute(root, "sig-alg-name", xcertificate.getSigAlgName());
			Tox.setAttribute(root, "sig-alg-oid", xcertificate.getSigAlgOID());
			Tox.setAttribute(root, "basic-constraint", xcertificate.getBasicConstraints());
			Tox.setAttribute(root, "serial-number", xcertificate.getSerialNumber());
			Tox.setAttribute(root, "not-before", xcertificate.getNotBefore());
			Tox.setAttribute(root, "not-after", xcertificate.getNotAfter());
			Tox.appendChild(document, root, "signature", xcertificate.getSignature());
			Tox.appendChild(document, root, "sig-alg-params", xcertificate.getSigAlgParams());
			Tox.appendChild(document, root, "issuer-unique-id", xcertificate.getIssuerUniqueID());
			Tox.appendChild(document, root, "subject-unique-id", xcertificate.getKeyUsage());
			Tox.appendChild(document, root, "key-usage", xcertificate.getKeyUsage());
			try {
				List<String> x500ExtendedKeyUsage = xcertificate.getExtendedKeyUsage();
				if (x500ExtendedKeyUsage != null) {
					x500ExtendedKeyUsage.forEach((s) -> {
						Tox.appendChild(document, root, "extendedKeyUsage", s);
					});
				}
			} catch (CertificateParsingException e) {
				ExceptionMarshaller.marshal(document, root, "encoded-exception", e);
			}
			try {
				Tox.appendChild(document, root, "tbs-certificate", xcertificate.getTBSCertificate());
			} catch (CertificateEncodingException e) {
				ExceptionMarshaller.marshal(document, root, "tbs-certificate-exception", e);
			}
			try {
				AlternativeNamesDescription.marshal(document, root, "issuerAlternativeName",
						xcertificate.getIssuerAlternativeNames());
			} catch (CertificateParsingException e) {
				ExceptionMarshaller.marshal(document, root, "issuer-alternative-name-exception", e);
			}
			try {
				AlternativeNamesDescription.marshal(document, root, "subjectAlternativeName",
						xcertificate.getSubjectAlternativeNames());
			} catch (CertificateParsingException e) {
				ExceptionMarshaller.marshal(document, root, "subject-alternative-name-exception", e);
			}
			PrincipalDescription.marshal(document, root, "issuerDN", xcertificate.getIssuerDN());
			PrincipalDescription.marshal(document, root, "subjectDN", xcertificate.getSubjectDN());

			X500PrincipalDescription.marshal(document, root, "issuerX500Principal",
					xcertificate.getIssuerX500Principal());
			X500PrincipalDescription.marshal(document, root, "subjectX500Principal",
					xcertificate.getSubjectX500Principal());

		}

		if (encoded != null) {
			AsnElementMarshaller.marshal(document, root, "asn", encoded);
		}
	}

}
