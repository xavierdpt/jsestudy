package example.company.jse.fiddle.javax.security.auth.x500;

import java.util.HashMap;
import java.util.Map;

import javax.security.auth.x500.X500Principal;

import org.junit.Test;

public class X500PrincipalFiddle {
	@Test
	public void fiddle1() {
		String name = "1.4=Duke, 1.3=JavaSoft, 1.2=Sun Microsystems, 1.1=US";
		X500Principal x = new X500Principal(name);

		System.out.println("Simple: " + x.getName());
		System.out.println();

		System.out.println("RFC1779: " + x.getName("RFC1779"));
		System.out.println();

		System.out.println("RFC2253: " + x.getName("RFC2253"));
		System.out.println();

		System.out.println("CANONICAL: " + x.getName("CANONICAL"));
		System.out.println();

		Map<String, String> oids = new HashMap<>();
		oids.put("1.1", "C");
		oids.put("1.2", "O");
		oids.put("1.3", "OU");
		oids.put("1.4", "CN");

		System.out.println("RFC1779 with oids: " + x.getName(X500Principal.RFC1779, oids));
		System.out.println();

		System.out.println("RFC2253 with oids: " + x.getName(X500Principal.RFC2253, oids));
		System.out.println();

//		System.out.println("CANONICAL with oids: " + x.getName(X500Principal.CANONICAL, oids));
//		System.out.println();

	}
}
