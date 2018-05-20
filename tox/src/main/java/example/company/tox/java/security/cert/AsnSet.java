package example.company.tox.java.security.cert;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class AsnSet extends AsnElement {

	List<AsnElement> elements = new ArrayList<>();

	public AsnSet() {
	}

	public AsnSet(Bytes bytes) {
		super(bytes);

		Bytes nextElementBytes = contentBytes;
		while (true) {
			if (nextElementBytes.length() == 0) {
				break;
			}
			AsnElement element = AsnUtils.parse(nextElementBytes);
			if (element == null) {
				break;
			}
			elements.add(element);

			int elementSize = element.identifierBytes.length() + element.lengthBytes.length()
					+ element.contentBytes.length();
			if (elementSize >= bytes.length()) {
				break;
			} else {
				nextElementBytes = nextElementBytes.offset(elementSize);
			}
		}
	}

	@XmlElement(name = "setElement")
	public List<AsnElement> getElements() {
		return elements;
	}

	public void setElements(List<AsnElement> elements) {
		this.elements = elements;
	}
}
