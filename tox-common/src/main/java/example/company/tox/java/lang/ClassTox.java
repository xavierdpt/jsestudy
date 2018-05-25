package example.company.tox.java.lang;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import example.company.tox.common.Tox;
import example.company.tox.common.ToxTox;

public class ClassTox extends ToxTox<Class<?>> {

	@Override
	public void tox(Document document, Element root, String name, Class<?> clazz) {
		Element element = Tox.appendChild(document, root, name);
		element.setAttribute("name", clazz.getName());
		Class<?> s = clazz.getSuperclass();
		if (s != null && !Object.class.equals(s)) {
			new ClassTox().tox(document, element, "super", s);
		}
		for (Class<?> i : clazz.getInterfaces()) {
			new ClassTox().tox(document, element, "interface", i);
		}

	}

	@Override
	protected String getDefaultName() {
		return "class";
	}

}
