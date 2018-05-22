package example.company.tox.java.security;

import java.security.Principal;

public class PrincipalDescription {

	private String className;
	private String name;

	public PrincipalDescription(Principal principal) {
		className = principal.getClass().getName();
		name = principal.getName();
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
