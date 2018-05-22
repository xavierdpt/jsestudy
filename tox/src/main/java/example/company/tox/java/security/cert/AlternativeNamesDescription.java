package example.company.tox.java.security.cert;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AlternativeNamesDescription {

	private Collection<AlternativeNamesListDescription> col = new ArrayList<>();

	public AlternativeNamesDescription(Collection<List<?>> issuerAlternativeNames) {
		issuerAlternativeNames.forEach((l) -> {
			col.add(new AlternativeNamesListDescription(l));
		});
	}

	

	public Collection<AlternativeNamesListDescription> getCol() {
		return col;
	}

	public void setCol(Collection<AlternativeNamesListDescription> col) {
		this.col = col;
	}

}
