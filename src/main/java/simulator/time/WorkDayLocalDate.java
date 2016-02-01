package simulator.time;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import simulator.utils.Constants;

public class WorkDayLocalDate {
    private WorkDayLocalDate() {
    }

    public static String toRealDate(long minutes) {
	long daysOfWork = minutes / Constants.WORK_DAY_DURATION_MINUTES;
	LocalDate localDate = LocalDate.now();
	localDate = localDate.plusDays(daysOfWork);

	return localDate.format(DateTimeFormatter.ofPattern("uuuu/MM/dd"));
    }
}
