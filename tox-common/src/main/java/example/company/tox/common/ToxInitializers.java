package example.company.tox.common;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;

import org.apache.commons.lang3.concurrent.ConcurrentException;
import org.apache.commons.lang3.concurrent.LazyInitializer;

public class ToxInitializers {

	static LazyInitializer<TransformerFactory> transformerFactory = new LazyInitializer<TransformerFactory>() {
		@Override
		protected TransformerFactory initialize() throws ConcurrentException {
			return TransformerFactory.newInstance();
		}
	};

	static LazyInitializer<Transformer> transformer = new LazyInitializer<Transformer>() {
		@Override
		protected Transformer initialize() throws ConcurrentException {
			try {
				Transformer t = transformerFactory.get().newTransformer();
				t.setOutputProperty(OutputKeys.INDENT, "yes");
				t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
				return t;
			} catch (TransformerConfigurationException e) {
				throw new ConcurrentException(e);
			}
		}
	};

	static LazyInitializer<DocumentBuilderFactory> documentBuilderFactory = new LazyInitializer<DocumentBuilderFactory>() {
		@Override
		protected DocumentBuilderFactory initialize() throws ConcurrentException {
			return DocumentBuilderFactory.newInstance();
		}
	};

	static LazyInitializer<DocumentBuilder> documentBuilder = new LazyInitializer<DocumentBuilder>() {
		@Override
		protected DocumentBuilder initialize() throws ConcurrentException {
			try {
				return documentBuilderFactory.get().newDocumentBuilder();
			} catch (ParserConfigurationException e) {
				throw new ConcurrentException(e);
			}
		}
	};

}
