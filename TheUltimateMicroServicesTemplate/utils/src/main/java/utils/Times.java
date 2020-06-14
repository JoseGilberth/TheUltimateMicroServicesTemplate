package utils;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class Times {

	public  static int converTimeUnitToCalendar(TimeUnit timeUnit) {
		if (timeUnit.equals(TimeUnit.NANOSECONDS)) {
			return 0;
		} else if (timeUnit.equals(TimeUnit.MICROSECONDS)) {
			return 0;
		} else if (timeUnit.equals(TimeUnit.MILLISECONDS)) { 
			return Calendar.MILLISECOND;
		} else if (timeUnit.equals(TimeUnit.SECONDS)) {
			return Calendar.SECOND;
		} else if (timeUnit.equals(TimeUnit.MINUTES)) {
			return Calendar.MINUTE;
		} else if (timeUnit.equals(TimeUnit.HOURS)) {
			return Calendar.HOUR;
		} else if (timeUnit.equals(TimeUnit.DAYS)) {
			return 0;
		}
		return 0;
	}

}
