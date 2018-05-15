package example.company.tox.java.security;

import java.security.Provider;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class ProviderDescription {

	private String name;
	private List<ServicesTypes> serviceTypes = new ArrayList<>();

	public ProviderDescription() {
	}

	public ProviderDescription(Provider provider) {
		name = provider.getName();
		provider.getServices().forEach((service) -> {
			String type = service.getType();
			boolean[] found = new boolean[] { false };
			ServiceDescription serviceDescription = new ServiceDescription(service);
			serviceTypes.forEach((f) -> {
				if (type.equals(f.getType())) {
					f.getServicesDescriptions().add(serviceDescription);
					found[0] = true;
				}
			});
			if (!found[0]) {
				serviceTypes.add(new ServicesTypes(type, new ServiceDescription(service)));
			}
		});
	}

	@XmlAttribute
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlElement(name = "services")
	public List<ServicesTypes> getServiceTypes() {
		return serviceTypes;
	}

	public void setFoo(List<ServicesTypes> serviceTypes) {
		this.serviceTypes = serviceTypes;
	}

}
