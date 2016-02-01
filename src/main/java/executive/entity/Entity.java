// GEntity.java
// M.Pidd
package executive.entity;

import executive.event.Activity;

/**
 * General entity class for three phase simulation. Will be used to define the
 * entities required in a particular simulation. GEntity needs to be known by
 * the Executive class, since it creates several Vectors to contain GEntity
 * objects.
 */
public abstract class Entity {

    public String name;
    /**
     * The time cell (time of next state change) of the entity
     */
    protected long timeCell = 0;
    /**
     * The previous time cell of the entity
     */
    protected long previousTime = 0;
    /**
     * If true, the entity is currently not committed to a B
     */
    protected boolean available = true;
    /**
     * Total time for which the entity has been committed thus far
     */
    protected long util = 0;
    /**
     * Counter for number of entities created so far
     */
    private static int numberOfInstances = 0; // duplicado nas subclasses?
    /**
     * Unique counter ID for this entity
     */
    protected int entityID;
    /**
     * The B in which this entity will engage when clock reaches the time cell
     */
    protected Activity nextBEvent; // abtes chamado NextB

    /**
     * Simple constructor. Gives the entity a name and keeps count of how many
     * entities have been created in total.
     */
    public Entity(String name) {
	numberOfInstances++;
	entityID = numberOfInstances;
	this.name = name;
    }

    public static void reset() {
	numberOfInstances = 0;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public int getEntityID() {
	return entityID;
    }

    /**
     * Returns with the current value of the time cell of the entity.
     */
    public long getTimeCell() {
	return timeCell;
    }

    /**
     * Returns with a boolean state showing whether the entity is currently
     * committed to a B.
     */
    public boolean isAvailable() {
	return available;
    }

    /**
     * Updates the time cell, availability and next B of the entity. Usually
     * called by Executive.schedule after checking that the values passed are
     * sensible. Allows Executive to be recoded if necessary.
     */
    public void commit(long thisTime, Activity nextAct) {
	timeCell = thisTime;
	nextBEvent = nextAct;
	util += thisTime;
	available = false;
    }

    /**
     * Releases an entity by putting avail to false and updating tCell.
     * DANGEROUS unless called from Executive!
     */
    public void release() {
	try {
	    if (available) {
		throw new Error("Tried to release an available entity");
	    }
	    available = true;
	    previousTime = timeCell;
	} catch (Error e) {
	    System.err.println("ERROR: " + e.getMessage());
	    System.exit(99);
	}
    }

    /**
     * Calls the method representing the next B.
     */
    public void doNextB() {
	release();
	nextBEvent.doThisNow();
    }

    /**
     * Returns with the current utilisation of the entity. Utilisation is the
     * total time for which the entity has been committed thus far. This could
     * exceed current simulation clock if the entity is currently scheduled for
     * some B in the distant future.
     */
    public long getUtil() {
	return util;
    }

    @Override
    public String toString() {
	return name;
    }

}
