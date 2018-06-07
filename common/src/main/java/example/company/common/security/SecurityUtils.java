package example.company.common.security;

import java.security.Security;

public class SecurityUtils {

	public static String[] getSupportedPaddings(CipherE c) {
		return Security.getProvider("SunJCE").getProperty("Cipher." + c.name() + " SupportedPaddings").split("\\|");
	}

	public static String[] getSupportedModes(CipherE c) {
		return Security.getProvider("SunJCE").getProperty("Cipher." + c.name() + " SupportedModes").split("\\|");
	}

}
