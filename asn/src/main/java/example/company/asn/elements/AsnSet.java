package example.company.asn.elements;

import java.util.ArrayList;
import java.util.List;

import example.company.asn.AsnTag;
import example.company.asn.utils.AsnUtils;
import example.company.tox.common.Bytes;

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

	public List<AsnElement> getElements() {
		return elements;
	}

	public void setElements(List<AsnElement> elements) {
		this.elements = elements;
	}

	@Override
	public void encode(List<Byte> bytes) {
		AsnUtils.encodeSequenceOrSet(bytes, AsnTag.SET, elements);
	}
}
