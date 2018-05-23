package example.company.tox.common;

import java.io.StringWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.xml.bind.DatatypeConverter;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

public class Common {

	public static byte bit(int integer) {
		return (byte) integer;
	}

	public static byte bit(long l) {
		return (byte) l;
	}

	public static byte[] bytes(int... integers) {
		byte[] bytes = new byte[integers.length];
		for (int i = 0; i < integers.length; ++i) {
			bytes[i] = (byte) integers[i];
		}
		return bytes;
	}

	public static String tos(Object o) {
		if (o == null) {
			return "n$";
		} else if (o instanceof String) {
			return "s$" + o.toString();
		} else {
			return "c$" + o.getClass().getName();
		}
	}

	public static String bytesToString(byte[] value) {
		return new HexBinaryAdapter().marshal(value);
	}

	public static String booleansToString(boolean[] value) {
		StringWriter sw = new StringWriter();
		for (int i = 0; i < value.length; ++i) {
			sw.append(value[i] ? "1" : "0");
		}
		return sw.toString();
	}

	public static String dateToString(Date value) {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		cal.setTimeInMillis(value.getTime());
		return DatatypeConverter.printDateTime(cal);
	}

	public static byte[] toArray(List<Byte> bytes) {
		byte[] array = new byte[bytes.size()];
		for (int i = 0; i < bytes.size(); ++i) {
			array[i] = bytes.get(i);
		}
		return array;
	}

}
