package example.company.tox.proxy;

import java.lang.reflect.Method;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import example.company.tox.java.lang.ExceptionDescription;

public final class AsnElementMarshaller {

	public static void marshal(Document document, Element root, String string, byte[] encoded) {
		try {
			Class<?> asnMarshallerClass = Class.forName("example.company.tox.asn.AsnElementMarshaller");
			Method method = asnMarshallerClass.getMethod("marshal", Document.class, Element.class, String.class,
					byte[].class);
			method.invoke(null, document, root, "asn", encoded);
		} catch (Exception e) {
			ExceptionDescription.marshal(document, root, "asnException", e);
		}
	}

}
