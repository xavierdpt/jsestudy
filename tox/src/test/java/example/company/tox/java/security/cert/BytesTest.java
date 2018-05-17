package example.company.tox.java.security.cert;

import org.junit.Assert;
import org.junit.Test;

public class BytesTest {

	@Test
	public void test1() {

		Bytes bytes = new Bytes(new byte[] { 0 });

		Assert.assertEquals(1, bytes.length());
		Assert.assertEquals(0, bytes.at(0));
	}

	@Test(expected = ArrayIndexOutOfBoundsException.class)
	public void test2() {
		Bytes bytes = new Bytes(new byte[] { 0 });
		bytes.at(1);
	}

	@Test
	public void test3() {

		Bytes bytes = new Bytes(new byte[] { 0, 1, 2 }, 1, 2);

		Assert.assertEquals(1, bytes.length());
		Assert.assertEquals(1, bytes.at(0));
	}
}
