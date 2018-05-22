package example.company.tox.java.security.auth.x500;

import javax.security.auth.x500.X500Principal;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import example.company.tox.common.Bytes;
import example.company.tox.java.security.cert.AsnElement;
import example.company.tox.java.security.cert.AsnUtils;

public class X500PrincipalDescription {

	private String className;
	private String name;
	private byte[] encoded;
	private AsnElement element;

	public X500PrincipalDescription(X500Principal principal) {
		className = principal.getClass().getName();
		name = principal.getName();
		encoded = principal.getEncoded();
		element = AsnUtils.parse(new Bytes(encoded));
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlJavaTypeAdapter(HexBinaryAdapter.class)
	public byte[] getEncoded() {
		return encoded;
	}

	public void setEncoded(byte[] encoded) {
		this.encoded = encoded;
	}

	public AsnElement getElement() {
		return element;
	}

	public void setElement(AsnElement element) {
		this.element = element;
	}

}
