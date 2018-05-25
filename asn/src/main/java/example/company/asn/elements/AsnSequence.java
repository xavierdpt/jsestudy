package example.company.asn.elements;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import example.company.asn.AsnTag;
import example.company.asn.utils.AsnUtils;
import example.company.tox.common.Bytes;

public class AsnSequence extends AsnElement implements Iterable<AsnElement> {

	List<AsnElement> elements = new ArrayList<>();

	public AsnSequence() {
	}

	public AsnSequence(Bytes allBytes) {
		super(allBytes);

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
			if (elementSize >= allBytes.length()) {
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
		AsnUtils.encodeSequenceOrSet(bytes, AsnTag.SEQUENCE, elements);
	}

	public AsnElement get(int i) {
		return elements.get(i);
	}

	public AsnContextSpecific getContextSpecific(int i) {
		return (AsnContextSpecific) get(i);
	}

	@Override
	public Iterator<AsnElement> iterator() {
		return elements.iterator();
	}

	public AsnObjectIdentifier getObjectIdentifier(int i) {
		return (AsnObjectIdentifier) get(i);
	}

	public AsnOctetString getOctetString(int i) {
		return (AsnOctetString) get(i);
	}
}
