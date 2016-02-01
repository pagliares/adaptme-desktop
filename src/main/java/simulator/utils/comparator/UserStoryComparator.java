package simulator.utils.comparator;

import java.util.Comparator;

import simulator.base.workproduct.UserStory;

public class UserStoryComparator implements Comparator<UserStory> {

    @Override
    public int compare(UserStory userStoryOne, UserStory userStoryTwo) {

	if (userStoryOne.getQueueStatus().getValue() < userStoryTwo.getQueueStatus().getValue()) {
	    return 1;
	} else if (userStoryOne.getQueueStatus().getValue() > userStoryTwo.getQueueStatus().getValue()) {
	    return -1;
	} else {
	    if (userStoryOne.getPriority().getValue() < userStoryTwo.getPriority().getValue()) {
		return 1;
	    } else if (userStoryOne.getPriority().getValue() > userStoryTwo.getPriority().getValue()) {
		return -1;
	    } else {
		if (userStoryOne.getDevelopmentStatus().getValue() > userStoryTwo.getDevelopmentStatus().getValue()) {
		    return 1;
		} else if (userStoryOne.getDevelopmentStatus().getValue() < userStoryTwo.getDevelopmentStatus()
			.getValue()) {
		    return -1;
		} else {
		    if (userStoryOne.getTargetPoints() > userStoryTwo.getTargetPoints()) {
			return 1;
		    } else if (userStoryOne.getTargetPoints() < userStoryTwo.getTargetPoints()) {
			return -1;
		    } else {
			return 0;
		    }
		}
	    }
	}

    }
}
