package example.company.jse.fiddle;

import org.junit.Test;

import example.company.common.security.CipherE;
import example.company.common.security.SecurityUtils;

public class Fiddle39 {

	@Test
	public void fiddle() {

		for (CipherE c : CipherE.values()) {
			for (String padding : SecurityUtils.getSupportedPaddings(c)) {
				System.out.println(c.name() + "-padding-" + padding);
			}
			for (String mode : SecurityUtils.getSupportedModes(c)) {
				System.out.println(c.name() + "-mode-" + mode);
			}
		}

	}

}
