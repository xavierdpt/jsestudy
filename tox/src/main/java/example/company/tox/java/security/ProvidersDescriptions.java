package example.company.tox.java.security;

import java.security.Provider;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="providers")
public class ProvidersDescriptions {

	private List<ProviderDescription> providersDescriptions = new ArrayList<ProviderDescription>();

	public ProvidersDescriptions() {
	}

	public ProvidersDescriptions(Provider[] providers) {
		for (int i = 0; i < providers.length; ++i) {
			providersDescriptions.add(new ProviderDescription(providers[i]));
		}
	}

	public List<ProviderDescription> getProvidersDescriptions() {
		return providersDescriptions;
	}

	@XmlElement(name = "provider")
	public void setProvidersDescriptions(List<ProviderDescription> providersDescriptions) {
		this.providersDescriptions = providersDescriptions;
	}

}
