package example.company.jse.fiddle.java.security;

import java.security.AccessControlContext;
import java.security.DomainCombiner;
import java.security.Permission;
import java.security.ProtectionDomain;

import org.junit.Test;

public class AccessControlContextFiddle {
	private AccessControlContext accessControlContext;
	private DomainCombiner domainCombiner;
	private ProtectionDomain[] protectionDomains;
	private Permission permission;

	@Test
	public void fiddle() {
		AccessControlContext.class.getName();

		AccessControlContext acc = new AccessControlContext(accessControlContext, domainCombiner);
		acc = new AccessControlContext(protectionDomains);
		acc.checkPermission(permission);
		acc.getDomainCombiner();
	}
}
