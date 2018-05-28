package example.company.tox.java.security;

import java.security.Provider;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import example.company.tox.common.ToxTox;

public class ProviderTox extends ToxTox<Provider> {

	public ProviderTox() {
	}

	@Override
	public void tox(Document document, Element root, String name, Provider t) {
		// TODO Auto-generated method stub
	}

	@Override
	protected String getDefaultName() {
		return "provider";
	}

	public void tox(Document document, Provider[] providers) {
		for (int i = 0; i < providers.length; ++i) {
			tox(document, providers[i]);
		}
	}

}
