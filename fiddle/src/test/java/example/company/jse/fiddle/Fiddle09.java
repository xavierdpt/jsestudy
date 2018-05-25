package example.company.jse.fiddle;

import java.security.Provider;
import java.security.Security;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Test;

public class Fiddle09 {

	@Test
	public void dumpAll() {
		computeMap().forEach((t, m) -> {
			m.forEach((a, l) -> {
				System.out.println(t + " " + a);
				l.forEach(n -> {
					System.out.println(" - " + n);
				});
			});
		});
	}

	@Test
	public void dumpMultiple() {
		computeMap().forEach((t, m) -> {
			m.forEach((a, l) -> {
				if (l.size() > 1) {
					System.out.println(t + " " + a);
					l.forEach(n -> {
						System.out.println(" - " + n);
					});
				}
			});
		});
	}

	@Test
	public void dumpSingle() {
		computeMap().forEach((t, m) -> {
			m.forEach((a, l) -> {
				if (l.size() == 1) {
					System.out.println(t + " " + a + " " + l.get(0));
				}
			});
		});
	}

	private Map<String, Map<String, List<String>>> computeMap() {
		Map<String, Map<String, List<String>>> map = new TreeMap<>();
		for (Provider provider : Security.getProviders()) {
			provider.getServices().forEach(service -> {
				String type = service.getType();
				String alg = service.getAlgorithm();
				String pname = provider.getName();
				if (!map.containsKey(type)) {
					map.put(type, new TreeMap<String, List<String>>());
				}
				if (!map.get(type).containsKey(alg)) {
					map.get(type).put(alg, new ArrayList<String>());
				}
				map.get(type).get(alg).add(pname);
			});
		}
		return map;
	}
}
