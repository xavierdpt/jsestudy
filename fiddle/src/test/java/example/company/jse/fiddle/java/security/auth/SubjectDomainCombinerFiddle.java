package example.company.jse.fiddle.java.security.auth;

import java.security.ProtectionDomain;

import javax.security.auth.Subject;
import javax.security.auth.SubjectDomainCombiner;

import org.junit.Test;

public class SubjectDomainCombinerFiddle {
	private Subject subject;
	private ProtectionDomain[] protectionDomains;

	@Test
	public void fiddle() {
		
		SubjectDomainCombiner.class.getName();

		SubjectDomainCombiner s = new SubjectDomainCombiner(subject);
		s.combine(protectionDomains, protectionDomains);
		subject = s.getSubject();
	}
}
