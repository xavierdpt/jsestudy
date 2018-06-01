package example.company.jse.fiddle;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

import org.junit.Test;

public class Fiddle26 {
	@Test
	public void fiddle() {

		Map<String, Set<String>> r = new HashMap<>();

		SortedMap<String, Charset> charsets = Charset.availableCharsets();
		charsets.forEach((n1, c1) -> {
			charsets.forEach((n2, c2) -> {
				if (c1.contains(c2)) {
					if (!r.containsKey(n1)) {
						r.put(n1, new HashSet<String>());
					}
					r.get(n1).add(n2);
				}
			});
		});

		r.forEach((n1, set) -> {
			set.forEach(n2 -> {

				if (!n1.equals(n2)) {
					boolean iso = r.containsKey(n2) && r.get(n2).contains(n1);
					if (iso) {
						System.out.println(n1 + " === " + n2);
					} else {
						 System.out.println(n1 + " > " + n2);
					}
				}

			});
		});
	}
}
