package executive;

import java.util.ArrayList;
import java.util.List;

import executive.entity.Entity;
import executive.event.Activity;

/**
 * Executive class to control a 3 phase simulation in which entity records are
 * held in a List (details, agora entitiesInUse) and from which a temporary List
 * (dueList) is created at each time beat to contain those entities with events
 * due at the current event time. Entities are added to the details List before
 * the start of an aPhase and unwanted entities are also deleted then. Bs are
 * assumed part of application entity classes, represented by integers & Cs as
 * belonging to the application alone Revised August 2000, consistent syntax
 */
public class Executive {

    private final long CHECK_VALUE = Long.MAX_VALUE;
    private final long MAXIMUM_TIME_CELL = Long.MAX_VALUE;

    private long currentClockTime = 0;
    private List<Entity> entitiesInUse;
    private List<Entity> entityListDueAtCurrentB;
    private List<Entity> newEntities;
    private List<Entity> entitiesToBeKilled;
    private Entity currentEntity;
    private boolean cStarted;
    private List<Activity> listOfEventsTypeC;

    /**
     * Simple constructor, sets trace and traceEverOn fields to false so that no
     * debug.txt file is created unless it is required.
     */
    public Executive() {
	entitiesInUse = new ArrayList<Entity>();
	entityListDueAtCurrentB = new ArrayList<Entity>();
	newEntities = new ArrayList<Entity>();
	entitiesToBeKilled = new ArrayList<Entity>();
	listOfEventsTypeC = new ArrayList<Activity>();
	cStarted = false;
    }

    public long getCurrentClockTime() {
	return currentClockTime;
    }

    /**
     * Returns with the current entity - that is, the entity whose B is about to
     * be processed or is being processed currently. Entity is defined as part
     * of the SimObs package.
     */
    public Entity getCurrentEntity() {
	return currentEntity;
    }

    /**
     * Called in user program when an entity is to be destroyed. Places the
     * entity on an temporary Vector, toBeKilled. The entities in toBeKilled are
     * actually killed just before the start of the next aPhase.
     */
    public void addEntitieToBeKilled(Entity genericEntity) {
	entitiesToBeKilled.add(genericEntity);
    }

    /**
     * Actually destroys the entity. Works down the toBeKilled Vector and
     * removes elements from details Vector. Called just before the aPhase.
     */
    private void destroy() {
	if (!entitiesToBeKilled.isEmpty()) {
	    for (int i = 0; i < entitiesToBeKilled.size(); i++) {
		Entity entity = entitiesToBeKilled.get(i);
		if (!entitiesInUse.remove(entity)) {
		    throw new Error(
			    "entity to be killed does not exist " + entity.getName() + "\n" + getCurrentClockTime());
		}
	    }
	}
	entitiesToBeKilled.clear();
    }

    /**
     * Called in user program when a new entity is to be added. Places the
     * entity on an temporary List, NewEntities, from which the new entities are
     * added just before the start of the aPhase.
     */
    public void addNewEntity(Entity entity) {
	newEntities.add(entity);
    }

    /**
     * Actually adds new entities to the details List from the NewEntities List.
     * Works down the NewEntities List and adds elements to the details List,
     * called before the aPhase.
     */
    private void addNewEntitiesToEntitiesInUse() {
	if (!newEntities.isEmpty()) {
	    for (Entity entity : newEntities) // Laco refatorado para uso do For
					      // Each. Nao encontrado nenhum
					      // efeito colateral
	    {
		entitiesInUse.add(entity);
	    }
	    newEntities.clear();
	}
    }

    /**
     * Used when an entity needs to be committed to a B at some future time.
     * Checks whether the entity is currently available. If not, this means that
     * it is already committed to a B and so there is a fatal error thrown.
     * Checks whether the interval between the current simulation clock time and
     * the due time of the B is within range, corrects it if not. Calls the
     * commit method of the entity to update its values in the details Vector.
     * Declared as, permits only a single executive.
     */
    public void schedule(Entity genericEntity, Activity nextActivity, long time) {
	try {
	    if (!genericEntity.isAvailable()) {
		throw new Error("Tried to schedule an unavailable entity");
	    }
	    if (time > MAXIMUM_TIME_CELL - currentClockTime) {
		time = MAXIMUM_TIME_CELL;
	    } else {
		time = currentClockTime + time;
	    }
	    genericEntity.commit(time, nextActivity);
	} catch (Error e) {
	    System.err.println("ERROR: " + e.getMessage());
	    System.exit(99);
	}
    }

    /**
     * Conventional APhase operating on the entitiesInUse List. Throws a fatal
     * error if aList is empty at the start of the aPhase. Destroys any entities
     * in the ToBeKilled List then adds any waiting in the NewEntities List.
     * Finds those entities with the smallest time cells that are currently
     * unavailable and adds this due now entities to the dueList Vector.
     */
    private void aPhase() {
	destroy();
	addNewEntitiesToEntitiesInUse();
	long minTimeCell = CHECK_VALUE;
	try {
	    if (entitiesInUse.isEmpty()) {
		throw new Error("entities in use list empty in APhase()");
	    }
	    // Pesquisa na lista de entidades para encontrar as com menor
	    // time-cell e adiciona na lista entityListDueAtCurrentB
	    for (int i = 0; i < entitiesInUse.size(); i++) {
		Entity entity = entitiesInUse.get(i);
		if (!entity.isAvailable()) {
		    if (entity.getTimeCell() < minTimeCell) {
			minTimeCell = entity.getTimeCell();
			entityListDueAtCurrentB.clear();
			entityListDueAtCurrentB.add(entity);
		    } else if (entity.getTimeCell() == minTimeCell) {
			entityListDueAtCurrentB.add(entity);
		    }
		}
	    }
	    if ((minTimeCell == CHECK_VALUE)) {
		throw new Error("Minimal Time Cell value at end of aPhase()");
	    }
	    currentClockTime = minTimeCell;
	} catch (Error e) {
	    System.err.println("ERROR: " + e.getMessage());
	    System.exit(99);
	}
    }

    /**
     * Conventional B Phase, causing the next Bs of the due now entities to be
     * executed. Does this by working down the dueList and calling each found
     * there to execute its next B. Throws a fatal error if dueList is empty at
     * the start of bPhase.
     */
    private void bPhase() {
	int numEntitiesAtCurrentB = entityListDueAtCurrentB.size();
	try {
	    if (numEntitiesAtCurrentB <= 0) {
		throw new Error("dueList empty at start of bPhase");
	    }
	    for (int i = 0; i < numEntitiesAtCurrentB; i++) {
		currentEntity = entityListDueAtCurrentB.get(i);
		currentEntity.doNextB();
	    }
	    entityListDueAtCurrentB.clear();
	} catch (Error e) {
	    System.err.println("ERROR: " + e.getMessage());
	    System.exit(99);
	}
    }

    public void addC(Activity thisC) {
	listOfEventsTypeC.add(thisC);
    }

    /**
     * Sets CStarted as true to indicate that a C has been executed during the
     * current C phase.
     */
    public void setCStarted() {
	cStarted = true;
    }

    /**
     * Causes Cs to be attempted until none succeed. Works down cList, firing
     * each C in turn. Any successful C causes cStarted to be set to true,
     * leading to a re-scan.
     */
    private void cPhase() {
	try {
	    if (listOfEventsTypeC.isEmpty()) {
		throw new Error("listOfEventsTypeC empty at start of CPhase");
	    }
	    cStarted = true;
	    while (cStarted) {
		cStarted = false;
		for (Activity activity : listOfEventsTypeC) {
		    activity.doThisNow();
		}
	    }
	} catch (Error e) {
	    System.err.println("ERROR: " + e.getMessage());
	    System.exit(99);
	}
    }

    public void simulate() {
	aPhase();
	bPhase();
	cPhase();
    }

    public void reset() {
	entitiesInUse.clear();
	entityListDueAtCurrentB.clear();
	newEntities.clear();
	entitiesToBeKilled.clear();
	listOfEventsTypeC.clear();
	currentEntity = null;
	currentClockTime = 0;
	cStarted = false;
    }
}
