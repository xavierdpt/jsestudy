package example.company.tox.common;

public class Common {

	public static byte bit(int integer) {
		return (byte) integer;
	}

	public static byte[] bytes(int... integers) {
		byte[] bytes = new byte[integers.length];
		for (int i = 0; i < integers.length; ++i) {
			bytes[i] = (byte) integers[i];
		}
		return bytes;
	}

}
