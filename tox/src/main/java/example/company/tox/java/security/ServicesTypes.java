package example.company.tox.java.security;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class ServicesTypes {

	private String type;
	private List<ServiceDescription> servicesDescriptions = new ArrayList<>();

	public ServicesTypes() {
	}

	public ServicesTypes(String type, ServiceDescription serviceDescription) {
		this.type = type;
		servicesDescriptions.add(serviceDescription);
	}

	@XmlAttribute
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@XmlElement(name = "service")
	public List<ServiceDescription> getServicesDescriptions() {
		return servicesDescriptions;
	}

	public void setServicesDescriptions(List<ServiceDescription> servicesDescriptions) {
		this.servicesDescriptions = servicesDescriptions;
	}

}
