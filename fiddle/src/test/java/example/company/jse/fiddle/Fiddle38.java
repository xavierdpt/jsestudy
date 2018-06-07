package example.company.jse.fiddle;

import java.security.Provider;
import java.security.Security;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

public class Fiddle38 {
	
	/* Dump all provider props */
	
	@Test
	public void fiddle() {

		List<String> list = new ArrayList<>();

		for (Provider p : Security.getProviders()) {
			String n = p.getName();
			for (String prop : p.stringPropertyNames()) {
				String line = n + "." + prop + "=" + p.getProperty(prop);
				list.add(line);
			}
		}

		Collections.sort(list);

		for (String line : list) {
			System.out.println(line);
		}
	}

	
}
