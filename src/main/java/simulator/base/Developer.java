package simulator.base;

import org.eclipse.epf.uma.Role;

import executive.entity.Entity;

public class Developer extends Entity {

    private Role role;

    public static int developerCreated;
    private double experience;
    private double skill;
    private double maximumSkill;
    private double initialVelocity;
    private double currentVelocity;
    private double initialSkill;
    private boolean idle = true;

    public Developer(String name, double initialSkill, double maximumSkill, double initialVelocity) {
	super(name);
	this.initialSkill = initialSkill;
	this.maximumSkill = maximumSkill;
	skill = initialSkill;
	this.initialVelocity = initialVelocity;
	currentVelocity = initialVelocity;
	developerCreated++;
    }

    public Role getRole() {
	return role;
    }

    public void setRole(Role role) {
	this.role = role;
    }

    public static void reset() {
	developerCreated = 0;
    }

    public void avaiable() {
	available = true;
    }

    public double getExperience() {
	return experience;
    }

    public double getInitialVelocity() {
	return initialVelocity;
    }

    public double getMaxSkill() {
	return maximumSkill;
    }

    public double getSkill() {
	return skill;
    }

    public void setInitialVelocity(double initialVelocity) {
	this.initialVelocity = initialVelocity;
	currentVelocity = initialVelocity;
    }

    public void setExperience(double experience) {
	this.experience = experience;
    }

    public void setMaxSkill(double maxSkill) {
	maximumSkill = maxSkill;
    }

    public void setSkill(double skill) {
	this.skill = skill;
    }

    public double getInitialSkill() {
	return initialSkill;
    }

    public void increaseExperience(double increment) {
	experience += increment;
    }

    public void increaseSkill(double skillToAdd) {
	skill = skillToAdd;
    }

    public void increaseCurrentVelocity() {
	currentVelocity = initialVelocity + Math.log(getSkill());
    }

    public double getCurrentVelocity() {
	return currentVelocity;
    }

    @Override
    public String toString() {
	return getName() + "; Skill " + String.format("%.4f", getSkill()) + "; Initial Skill "
		+ String.format("%.4f", getInitialSkill()) + "; Velocity: "
		+ String.format("%.2f", getCurrentVelocity()) + "; Initial Velocity: "
		+ String.format("%.2f", getInitialVelocity()) + "; Experience: "
		+ String.format("%.2f", getExperience());
    }

    public boolean isIdle() {
	return idle;
    }

    public void setIdle(boolean idle) {
	this.idle = idle;
    }
}
