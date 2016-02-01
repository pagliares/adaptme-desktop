package simulator.uma.dynamic;

import org.eclipse.epf.uma.Activity;

import simulator.time.WorkDayLocalTime;

public class DynamicActivity extends Activity implements Dynamic {

    private long startDate;
    private long endData;
    private long startTime;
    private long endTime;

    private String localStartTime;
    private String localEndTime;

    public long getDate() {
	return startDate;
    }

    public long getStartTime() {
	return startTime;
    }

    public long getEndTime() {
	return endTime;
    }

    public void setDate(long date) {
	this.startDate = date;
    }

    public void setStartTime(long startTime) {
	localStartTime = WorkDayLocalTime.toRealTimeAfternoon(startTime);
	this.startTime = startTime;
    }

    public void setEndTime(long endTime) {
	localEndTime = WorkDayLocalTime.toRealTimeNight(endTime);
	this.endTime = endTime;
    }

    public long getDuration() {
	return endTime - startTime;
    }

    @Override
    public String getDynamicName() {
	return getPresentationName();
    }

    @Override
    public String getLocalStartTime() {
	return localStartTime;
    }

    @Override
    public String getLocalEndTime() {
	return localEndTime;
    }

    @Override
    public long getEndDate() {
	return getEndData();
    }

    public long getEndData() {
	return endData;
    }

    public void setEndData(long endData) {
	this.endData = endData;
    }
}
