package example.company.tox.common;

import java.io.OutputStream;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.lang3.concurrent.ConcurrentException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

public class Tox {

	public static Document createDocument(String name) {
		try {
			Document document = ToxInitializers.documentBuilder.get().newDocument();
			Tox.appendChild(document, name);
			return document;
		} catch (ConcurrentException e) {
			throw new ToxException(e);
		}
	}

	public static Document createDocument() {
		return createDocument("root");
	}

	public static void print(Document doc, OutputStream out) {
		DOMSource domSource = new DOMSource(doc);
		StreamResult result = new StreamResult(out);
		try {
			ToxInitializers.transformer.get().transform(domSource, result);
		} catch (Exception e) {
			throw new ToxException(e);
		}
	}

	private static Map<Class<?>, JAXBContext> contexts = new HashMap<>();
	private static Map<Class<?>, Marshaller> marshallers = new HashMap<>();

	public static void marshall_(Object o, OutputStream out) throws JAXBException {
		getMarshaller(o.getClass()).marshal(o, out);
	}

	private static Marshaller getMarshaller(Class<? extends Object> clazz) throws JAXBException {
		if (!contexts.containsKey(clazz)) {
			contexts.put(clazz, JAXBContext.newInstance(clazz));
		}
		if (!marshallers.containsKey(clazz)) {
			Marshaller m = contexts.get(clazz).createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshallers.put(clazz, m);
		}
		return marshallers.get(clazz);
	}

	public static void setAttribute(Element element, String name, Integer value) {
		if (value != null) {
			element.setAttribute(name, value.toString());
		}
	}

	public static void setAttribute(Element element, String name, String value) {
		if (value != null) {
			element.setAttribute(name, value);
		}
	}

	public static Element appendChild(Document document, String name) {
		Element element = document.createElement(name);
		document.appendChild(element);
		return element;
	}

	public static Element appendChild(Document document, Element root, String name, byte[] value) {
		if (value != null) {
			Text content = document.createTextNode(Common.bytesToString(value));

			Element element = document.createElement(name);
			element.appendChild(content);

			root.appendChild(element);

			return element;
		}
		return null;
	}

	public static Element appendChild(Document document, Element root, String name) {
		Element element = document.createElement(name);
		if (root != null) {
			root.appendChild(element);
		} else {
			document.appendChild(element);
		}
		return element;
	}

	public static Element appendChild(Document document, Element root, String name, boolean[] value) {
		if (value != null) {
			Text text = document.createTextNode(Common.booleansToString(value));

			Element element = document.createElement(name);
			element.appendChild(text);

			root.appendChild(element);

			return element;
		}
		return null;
	}

	public static void setAttribute(Element element, String name, BigInteger value) {
		if (value != null) {
			element.setAttribute(name, value.toString());
		}
	}

	public static void setAttribute(Element element, String name, Date value) {
		if (value != null) {
			element.setAttribute(name, Common.dateToString(value));
		}
	}

	public static Element appendChild(Document document, Element root, String name, String value) {

		if (value != null) {
			Text text = document.createTextNode(value);

			Element element = document.createElement(name);
			element.appendChild(text);

			root.appendChild(element);

			return element;
		}
		return null;
	}

	public static Element appendChild(Document document, Element root, String name, Object value) {
		if (value != null) {

			Text text;
			if (value instanceof String) {
				text = document.createTextNode(value.toString());
			} else {
				text = document.createTextNode(Common.tos(value));
			}

			Element element = document.createElement(name);
			element.appendChild(text);

			root.appendChild(element);

			return element;
		}
		return null;
	}

	public static Element appendChild(Document document, Element root, String name, Long value) {
		if (value != null) {

			Text text = document.createTextNode(value.toString());

			Element element = document.createElement(name);
			element.appendChild(text);

			root.appendChild(element);

			return element;
		}
		return null;
	}

	public static void appendText(Document document, Element element, String value) {
		Text text = document.createTextNode(value);
		element.appendChild(text);
	}

	public static void setAttribute(Element element, String name, Long value) {
		if (value != null) {
			element.setAttribute(name, value.toString());
		}
	}

}
