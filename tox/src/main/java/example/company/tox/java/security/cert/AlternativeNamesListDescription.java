package example.company.tox.java.security.cert;

import java.util.ArrayList;
import java.util.List;

public class AlternativeNamesListDescription {

	private List<Object> items = new ArrayList<>();

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public AlternativeNamesListDescription(List l) {
		l.forEach((item) -> {
			items.add(item);
		});
	}

	public List<Object> getItems() {
		return items;
	}

	public void setItems(List<Object> items) {
		this.items = items;
	}

}
