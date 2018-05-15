package example.company.tox.java.security;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "keyPairs")
public class AnnotatedKeyPairDescriptions {

	private List<AnnotatedKeyPairDescription> keyPairs = new ArrayList<>();

	public AnnotatedKeyPairDescriptions() {
	}

	@XmlElement(name = "keyPair")
	public List<AnnotatedKeyPairDescription> getKeyPairs() {
		return keyPairs;
	}

	public void setKeyPairs(List<AnnotatedKeyPairDescription> keyPairs) {
		this.keyPairs = keyPairs;
	}

}
