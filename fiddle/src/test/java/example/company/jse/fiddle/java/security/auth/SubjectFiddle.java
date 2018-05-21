package example.company.jse.fiddle.java.security.auth;

import java.security.AccessControlContext;
import java.security.Principal;
import java.security.PrivilegedAction;
import java.util.Set;

import javax.security.auth.Subject;

import org.junit.Test;

public class SubjectFiddle {
	private boolean readOnly;
	private Set<? extends Principal> principals;
	private Set<?> pubCredentials;
	private Set<?> privCredentials;
	private PrivilegedAction<?> privilegedAction;
	private PrivilegedAction<?> privilegedExceptionAction;
	private AccessControlContext accessControlContext;
	private Class<?> clazz;
	private Class<Principal> principalClass;

	@Test
	public void fiddle() {
		Subject subject = new Subject();
		subject = new Subject(readOnly, principals, pubCredentials, privCredentials);

		Subject.doAs(subject, privilegedAction);
		Subject.doAs(subject, privilegedExceptionAction);
		Subject.doAsPrivileged(subject, privilegedAction, accessControlContext);
		Subject.doAsPrivileged(subject, privilegedExceptionAction, accessControlContext);
		Subject.getSubject(accessControlContext);
		subject.getPrincipals();
		subject.getPrincipals(principalClass);
		subject.getPrivateCredentials();
		subject.getPrivateCredentials(clazz);
		subject.getPublicCredentials();
		subject.getPublicCredentials(clazz);
		subject.hashCode();
		subject.isReadOnly();
		subject.setReadOnly();

	}
}
