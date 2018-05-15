package example.company.tox;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class Tox {

	private static Map<Class<?>, JAXBContext> contexts = new HashMap<>();
	private static Map<Class<?>, Marshaller> marshallers = new HashMap<>();

	public static void marshall(Object o, OutputStream out) throws JAXBException {
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

}
