package adaptme.util;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

public class DateUtil {
    public static XMLGregorianCalendar asXMLGregorianCalendar(Date date) {
	GregorianCalendar calDate = new GregorianCalendar();
	calDate.setTime(date);
	XMLGregorianCalendar calendar = null;
	try {
	    DatatypeFactory factory = DatatypeFactory.newInstance();
	    calendar = factory.newXMLGregorianCalendar(calDate.get(GregorianCalendar.YEAR),
		    calDate.get(GregorianCalendar.MONTH) + 1, calDate.get(GregorianCalendar.DAY_OF_MONTH),
		    calDate.get(GregorianCalendar.HOUR_OF_DAY), calDate.get(GregorianCalendar.MINUTE),
		    calDate.get(GregorianCalendar.SECOND), calDate.get(GregorianCalendar.MILLISECOND), 0);
	} catch (DatatypeConfigurationException dce) {
	    dce.printStackTrace();
	}
	return calendar;
    }
}
