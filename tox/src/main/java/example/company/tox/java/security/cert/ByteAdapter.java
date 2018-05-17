package example.company.tox.java.security.cert;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class ByteAdapter extends XmlAdapter<String, Bytes> {

	private static final char[] hexCode = "0123456789ABCDEF".toCharArray();

	@Override
	public Bytes unmarshal(String v) throws Exception {
		throw new NoSuchMethodException();
	}

	@Override
	public String marshal(Bytes data) throws Exception {
		if (data == null) {
			return null;
		}
		StringBuilder r = new StringBuilder(data.length() * 2);
		for (int i = 0; i < data.length(); ++i) {
			byte b = data.at(i);
			r.append(hexCode[(b >> 4) & 0xF]);
			r.append(hexCode[(b & 0xF)]);

		}
		return r.toString();
	}

}
