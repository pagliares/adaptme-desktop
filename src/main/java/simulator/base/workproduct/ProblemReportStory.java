package simulator.base.workproduct;

public class ProblemReportStory extends UserStory {

    private UserStory storyToDebug;

    public ProblemReportStory(String name) {
	super(name);
    }

    public UserStory getStoryToDebug() {
	return storyToDebug;
    }

    public void setStoryToDebug(UserStory storyToDebug) {
	this.storyToDebug = storyToDebug;
    }

}
