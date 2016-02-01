package simulator.base.workproduct;

import org.eclipse.epf.uma.WorkProductDescriptor;

import simulator.utils.Round;
import simulator.utils.enums.UserStoryDevelopmentStatus;
import simulator.utils.enums.UserStoryPriority;
import simulator.utils.enums.UserStoryQueueStatus;

public class UserStory {

    private static int userStoriesCreated;

    private String name;
    private UserStoryPriority priority = UserStoryPriority.COULD;
    private UserStoryDevelopmentStatus developmentStatus = UserStoryDevelopmentStatus.TODO;
    private UserStoryQueueStatus queueStatus = UserStoryQueueStatus.IN_PROJECT;
    private double estimatedPoints;
    private double pointsDone;
    private double estimatedError;
    private double classes;
    private double methods;
    private double linesOfCodeDeveloped;
    private double defects;
    private String splitOf = null;

    private WorkProductDescriptor workProductDescriptor;

    public UserStory(String name) {
	this(name, null);
    }

    public UserStory(String name, String splitOf) {
	this.name = name;
	if (splitOf != null) {
	    this.splitOf = " Split of " + splitOf;
	}
	userStoriesCreated++;
    }

    public static void reset() {
	userStoriesCreated = 0;
    }

    public static int getUserStoriesCreated() {
	return userStoriesCreated;
    }

    public String getName() {
	return name;
    }

    public UserStoryPriority getPriority() {
	return priority;
    }

    public UserStoryDevelopmentStatus getDevelopmentStatus() {
	return developmentStatus;
    }

    public double getEstimatedPoints() {
	return Round.off(estimatedPoints, 2);
    }

    public double getPointsDone() {
	return Round.off(pointsDone, 2);
    }

    public double getEstimatedError() {
	return Round.off(estimatedError, 2);
    }

    public double getClasses() {
	return Round.off(classes, 2);
    }

    public double getMethods() {
	return Round.off(methods, 2);
    }

    public double getLinesOfCodeDeveloped() {
	return Round.off(linesOfCodeDeveloped, 2);
    }

    public double getDefects() {
	return Round.off(defects, 2);
    }

    public double getTargetPoints() {
	return Round.off(getEstimatedPoints() * getEstimatedError(), 2);
    }

    public String getSplitOf() {
	return splitOf == null ? "" : splitOf;
    }

    public WorkProductDescriptor getWorkProductDescriptor() {
	return workProductDescriptor;
    }

    public void setWorkProductDescriptor(WorkProductDescriptor workProductDescriptor) {
	this.workProductDescriptor = workProductDescriptor;
    }

    public void setName(String name) {
	this.name = name;
    }

    public void setPriority(UserStoryPriority priority) {
	this.priority = priority;
    }

    public void setDevelopmentStatus(UserStoryDevelopmentStatus status) {
	developmentStatus = status;
    }

    public void setEstimatedPoints(double estimatedPoints) {
	this.estimatedPoints = estimatedPoints;
    }

    public void addPointsDone(double pointsDone) {
	this.pointsDone += pointsDone;
    }

    public void setEstimatedError(double estimatedError) {
	this.estimatedError = estimatedError;
    }

    public void addClassesDone(double classes) {
	this.classes += classes;
    }

    public void addMethodsDone(double methods) {
	this.methods += methods;
    }

    public void setDefects(int defects) {
	this.defects = defects;
    }

    public void addLinesOfCodeDevelopedDone(double linesOfCodeDeveloped) {
	this.linesOfCodeDeveloped += linesOfCodeDeveloped;
    }

    @Override
    public String toString() {
	return getName() + getSplitOf() + "; Estimated Points: " + String.format("%.2f", getEstimatedPoints())
		+ "; Target Points: " + String.format("%.2f", getTargetPoints()) + "; Points Done: "
		+ String.format("%.2f", getPointsDone()) + "; Classes: " + (int) getClasses() + "; Methods: "
		+ getMethods() + "; Lines: " + getLinesOfCodeDeveloped() + "; Defects: " + (int) getDefects()
		+ "; Status " + getDevelopmentStatus().toString() + "; Priority: " + getPriority().toString()
		+ "; Queue Status: " + getQueueStatus().toString();
    }

    public UserStoryQueueStatus getQueueStatus() {
	return queueStatus;
    }

    public void setQueueStatus(UserStoryQueueStatus queueStatus) {
	this.queueStatus = queueStatus;
    }

}
