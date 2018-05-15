package example.company.tox.java.security;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

public class ServicesDescriptions {

	List<ServiceDescription> servicesDescriptions = new ArrayList<>();

	public ServicesDescriptions() {
	}

	public List<ServiceDescription> getServicesDescriptions() {
		return servicesDescriptions;
	}

	@XmlElementWrapper
	@XmlElement(name="serviceDescription")
	public void setServicesDescriptions(List<ServiceDescription> servicesDescriptions) {
		this.servicesDescriptions = servicesDescriptions;
	}

}
