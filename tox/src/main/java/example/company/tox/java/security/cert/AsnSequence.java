package example.company.tox.java.security.cert;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElementWrapper;

public class AsnSequence extends AsnElement {

	List<AsnElement> elements = new ArrayList<>();

	public AsnSequence(Bytes allBytes) {
		super(allBytes);

		Bytes nextElementBytes = getContentBytes();
		while (true) {
			if(nextElementBytes.length()==0) {
				break;
			}
			AsnElement element = AsnUtils.parse(nextElementBytes);
			if (element == null) {
				break;
			}
			elements.add(element);
			int elementSize = element.getIdentifierBytes().length() + element.getLengthBytes().length()
					+ element.getContentBytes().length();
			if (elementSize >= allBytes.length()) {
				break;
			} else {
				nextElementBytes = nextElementBytes.offset(elementSize);
			}
		}
	}

	@XmlElementWrapper
	public List<AsnElement> getElements() {
		return elements;
	}

	public void setElements(List<AsnElement> elements) {
		this.elements = elements;
	}

}
