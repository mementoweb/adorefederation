package gov.lanl.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class HttpDate {
    public final static Locale LOCALE_US = Locale.US;
	public final static TimeZone GMT_ZONE = TimeZone.getTimeZone("GMT");
    public final static String RFC1123_PATTERN = "EEE, dd MMM yyyyy HH:mm:ss z";
    public final static SimpleDateFormat rfc1123Format = new SimpleDateFormat(RFC1123_PATTERN, LOCALE_US);
    
    public static String getHttpDate() {
    	Calendar calendar = new GregorianCalendar(GMT_ZONE, LOCALE_US);
        return getHttpDate(calendar, new Date(System.currentTimeMillis()));
    }
    
    public static String getHttpDate(Date time) {
    	Calendar calendar = new GregorianCalendar(GMT_ZONE, LOCALE_US);
        return rfc1123Format.format(calendar.getTime());
    }
    
    public static String getHttpDate(Calendar calendar, Date time) {
        calendar.setTime(time);
        return rfc1123Format.format(calendar.getTime());
    }
}
