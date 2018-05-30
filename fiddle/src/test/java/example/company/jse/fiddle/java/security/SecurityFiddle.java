package example.company.jse.fiddle.java.security;

import java.security.Provider;
import java.security.Provider.Service;
import java.security.Security;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.xml.bind.JAXBException;

import org.junit.Test;

public class SecurityFiddle {

	@Test
	public void test() throws JAXBException {

		Map<String, Map<String, List<String>>> map = new TreeMap<>();

		Provider[] providers = Security.getProviders();
		for (Provider provider : providers) {
			String providerName = provider.getName();
			for (Service service : provider.getServices()) {
				String serviceAlgorithm = service.getAlgorithm();
				String serviceType = service.getType();
				if (!map.containsKey(serviceType)) {
					map.put(serviceType, new TreeMap<String, List<String>>());
				}
				if (!map.get(serviceType).containsKey(serviceAlgorithm)) {
					map.get(serviceType).put(serviceAlgorithm, new ArrayList<String>());
				}
				map.get(serviceType).get(serviceAlgorithm).add(providerName);
			}
		}

		map.forEach((serviceType, b) -> {
			System.out.println(serviceType);
			b.forEach((serviceAlgorithm, d) -> {
				System.out.print(" - " + serviceAlgorithm + ": ");
				d.forEach(providerName -> {
					System.out.print(providerName + ", ");
				});
				System.out.println();
			});
		});
	}
}
