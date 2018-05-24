package example.company.asn.elements;

import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import example.company.asn.AsnClass;
import example.company.asn.AsnEncoding;
import example.company.asn.AsnTag;
import example.company.asn.utils.AsnUtils;
import example.company.tox.common.Bytes;

public class AsnUtcTime extends AsnElement {

	private String value;

	public AsnUtcTime() {
	}

	public AsnUtcTime(String value) {
		this.value = value;
	}

	public AsnUtcTime(Bytes bytes) {
		super(bytes);

		String rawvalue = new String(contentBytes.toByteArray(), Charset.forName("UTF-8"));

		StringWriter sb = new StringWriter();

		int c = 0;
		sb.append(rawvalue.substring(c, c + 2));
		c += 2;
		sb.append("/");

		sb.append(rawvalue.substring(c, c + 2));
		c += 2;
		sb.append("/");

		sb.append(rawvalue.substring(c, c + 2));
		c += 2;
		sb.append(" ");

		sb.append(rawvalue.substring(c, c + 2));
		c += 2;
		sb.append(":");

		sb.append(rawvalue.substring(c, c + 2));
		c += 2;
		sb.append(":");

		sb.append(rawvalue.substring(c, c + 2));
		c += 2;
		sb.append(" ");

		sb.append(rawvalue.substring(c, c + 1));

		value = sb.toString();

	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public void encode(List<Byte> bytes) {
		AsnUtils.addIdentifierBytes(bytes, AsnClass.UNIVERSAL, AsnEncoding.PRIMITIVE, AsnTag.UTC_TIME);
		byte[] encoded = value.replaceAll("[ :/]", "").getBytes();
		AsnUtils.addLengthBytes(bytes, encoded.length);
		AsnUtils.addBytes(bytes, encoded);
	}

	public Date toDate() {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		String[] parts = value.split("[ :/]");
		cal.setTimeInMillis(0);
		Integer year = Integer.valueOf(parts[0]);
		cal.set(Calendar.YEAR, 2000+year);
		int month = Integer.valueOf(parts[1]) - 1;
		cal.set(Calendar.MONTH, month);
		Integer day = Integer.valueOf(parts[2]);
		cal.set(Calendar.DAY_OF_MONTH, day);
		Integer hour = Integer.valueOf(parts[3]);
		cal.set(Calendar.HOUR_OF_DAY, hour);
		Integer minute = Integer.valueOf(parts[4]);
		cal.set(Calendar.MINUTE, minute);
		Integer second = Integer.valueOf(parts[5]);
		cal.set(Calendar.SECOND, second);
		Date time = cal.getTime();
		return time;
	}

}
