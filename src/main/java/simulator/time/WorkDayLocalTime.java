package simulator.time;

import java.time.LocalTime;

import simulator.utils.Constants;

public class WorkDayLocalTime {

    private WorkDayLocalTime() {
    }

    public static String toRealTimeNight(long minutes) {
	if (minutes == 0) {
	    return LocalTime.of(8, 0, 1).toString();
	}
	long minutesOfWork = minutes % 480;
	LocalTime localTime;
	if (minutesOfWork == 0) {
	    localTime = LocalTime.of(17, 0, 1);
	} else {
	    localTime = LocalTime.of(8, 0, 1);
	    if (minutesOfWork > Constants.WORK_DAY_DURATION_MINUTES / 2) {
		localTime = localTime.plusHours(1);
	    }
	    localTime = localTime.plusMinutes(minutesOfWork);
	}
	return localTime.toString();
    }

    public static String toRealTimeAfternoon(long minutes) {
	if (minutes == 0) {
	    return LocalTime.of(8, 0, 1).toString();
	}
	long minutesOfWork = minutes % 480;
	LocalTime localTime = LocalTime.of(8, 0, 1);
	if (minutesOfWork >= Constants.WORK_DAY_DURATION_MINUTES / 2) {
	    localTime = localTime.plusHours(1);
	}
	localTime = localTime.plusMinutes(minutesOfWork);
	return localTime.toString();
    }
}
