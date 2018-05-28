package example.company.jse.fiddle.java.security;

import java.security.DomainCombiner;
import java.security.ProtectionDomain;

import org.junit.Test;

public class DomainCombinerFiddle {
	private ProtectionDomain[] currentDomains;
	private ProtectionDomain[] assignedDomains;

	@Test(expected = InstantiationException.class)
	public void fiddle() throws InstantiationException, IllegalAccessException {
		DomainCombiner domainCombiner = DomainCombiner.class.newInstance();
		domainCombiner.combine(currentDomains, assignedDomains);
	}
}
