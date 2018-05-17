package example.company.tox.java.lang;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

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

}
