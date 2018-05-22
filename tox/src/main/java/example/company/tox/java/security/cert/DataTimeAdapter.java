package example.company.tox.java.security.cert;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.xml.bind.DatatypeConverter;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class DataTimeAdapter extends XmlAdapter<String, Date> {

	@Override
	public Date unmarshal(String v) throws Exception {
		return DatatypeConverter.parseDateTime(v).getTime();
	}

	@Override
	public String marshal(Date v) throws Exception {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		cal.setTimeInMillis(v.getTime());
		return DatatypeConverter.printDateTime(cal);
	}
}
