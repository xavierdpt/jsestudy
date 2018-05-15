package example.company.tox.java.security;

import java.security.Provider.Service;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlType()
public class ServiceDescription {

	private String algorithm;

	public ServiceDescription() {
	}

	public ServiceDescription(Service service) {
		algorithm = service.getAlgorithm();
	}

	@XmlAttribute
	public String getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

}
