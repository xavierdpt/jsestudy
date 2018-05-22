package example.company.tox.java.lang;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import example.company.tox.common.Tox;

public class ExceptionDescription {

	private String className;
	private String message;

	public ExceptionDescription() {
	}

	public ExceptionDescription(Exception e) {
		className = e.getClass().getName();
		message = e.getMessage();
	}

	@XmlAttribute
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	@XmlValue
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public static void marshal(Document document, Element root, String name, Exception e) {
		Element element = Tox.appendChild(document, root, name);
		Tox.setAttribute(element, "className", e.getClass().getName());
		Tox.appendText(document, element, e.getMessage());
	}

}
