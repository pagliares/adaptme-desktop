package model.spem.config;

import model.spem.derived.BestFitDistribution;

public class RoleConfig {

    private String name;
    private double experienceMean;
    private double experienceStdDev;
    private double skillMean;
    private double skillStdDev;
    private int quantiry;
    private BestFitDistribution bestFitDistribution;

    public double getExperienceMean() {
	return experienceMean;
    }

    public void setExperienceMean(double experienceMean) {
	this.experienceMean = experienceMean;
    }

    public double getExperienceStdDev() {
	return experienceStdDev;
    }

    public void setExperienceStdDev(double experienceStdDev) {
	this.experienceStdDev = experienceStdDev;
    }

    public double getSkillMean() {
	return skillMean;
    }

    public void setSkillMean(double skillMean) {
	this.skillMean = skillMean;
    }

    public double getSkillStdDev() {
	return skillStdDev;
    }

    public void setSkillStdDev(double skillStdDev) {
	this.skillStdDev = skillStdDev;
    }

    public BestFitDistribution getBestFitDistribution() {
	return bestFitDistribution;
    }

    public void setBestFitDistribution(BestFitDistribution bestFitDistribution) {
	this.bestFitDistribution = bestFitDistribution;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public int getQuantiry() {
	return quantiry;
    }

    public void setQuantiry(int quantiry) {
	this.quantiry = quantiry;
    }

}
