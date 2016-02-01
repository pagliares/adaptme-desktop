package simulator.uma.dynamic;

public interface Dynamic {
    long getStartTime();

    String getLocalStartTime();

    long getEndTime();

    String getLocalEndTime();

    long getDuration();

    long getDate();

    long getEndDate();

    String getDynamicName();
}
