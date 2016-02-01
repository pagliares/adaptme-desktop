package simulator.utils;

import executive.Executive;
import simulator.base.entity.Project;
import simulator.utils.log.Logger;

public class ApplicationContext {

    private Executive executive;
    private Project project;
    private Logger logger;

    public ApplicationContext(Executive executive, Project project, Logger logger) {
	this.executive = executive;
	this.project = project;
	this.logger = logger;
    }

    public Executive getExecutive() {
	return executive;
    }

    public Project getProject() {
	return project;
    }

    public Logger getLogger() {
	return logger;
    }
}
