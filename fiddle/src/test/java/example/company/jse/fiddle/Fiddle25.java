package example.company.jse.fiddle;

import java.nio.charset.Charset;
import java.util.SortedMap;

import org.junit.Test;

public class Fiddle25 {
	@Test
	public void fiddle() {
		SortedMap<String, Charset> charsets = Charset.availableCharsets();
		charsets.forEach((s, c) -> {
			// System.out.println(s);
			// System.out.println(c.getClass().getName());
			System.out.println(c.name());
			c.aliases().forEach(a -> {
				System.out.println(" - " + a);
			});
			System.out.println("d : " + c.displayName());

		});
		System.out.println(charsets.size());
	}
}
