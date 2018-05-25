package example.company.tox.common;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public abstract class ToxTox<T> {

	public abstract void tox(Document document, Element root, String name, T t);

	public final void tox(Document document, String name, T t) {
		tox(document, document.getDocumentElement(), name, t);
	}

	public final void tox(Document document, Element root, T t) {
		tox(document, root, getDefaultName(), t);
	}

	public final void tox(Document document, T t) {
		tox(document, document.getDocumentElement(), getDefaultName(), t);
	}

	public final Document tox(String name, T t) {
		Document document = Tox.createDocument();
		tox(document, document.getDocumentElement(), name, t);
		return document;
	}

	public final Document tox(Element root, T t) {
		Document document = Tox.createDocument();
		tox(document, root, getDefaultName(), t);
		return document;
	}

	public final Document tox(T t) {
		Document document = Tox.createDocument();
		tox(document, document.getDocumentElement(), getDefaultName(), t);
		return document;
	}

	protected abstract String getDefaultName();
}
