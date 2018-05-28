package example.company.tox.java.security;

import java.security.KeyPair;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import example.company.tox.common.ToxTox;

public class KeyPairDescription extends ToxTox<KeyPair> {

	@Override
	public void tox(Document document, Element root, String name, KeyPair t) {
		// TODO Auto-generated method stub
	}

	@Override
	protected String getDefaultName() {
		return "keypair";
	}

}
