// Queue.java
// Used to exend the Vector type into a queue
// M. Pidd 11/8/2000

package executive.queue;

import java.util.ArrayList;

/**
 * Simple class to model queues.
 */
public class Queue<T> {

    ArrayList<T> queue;
    /** Total number added to the queue since its creation */
    private int numberAdded;
    // * Total number removed from the queue since its creation*/
    private int numberRemoved;

    /**
     * Constructor, just sets the two data fields to zero.
     */
    public Queue() {
	queue = new ArrayList<T>();
	numberAdded = 0;
	numberRemoved = 0;
    }

    /**
     * Puts the object on the tail of the queue. Updates numAdded.
     */
    public void addToTail(T thisObject) {
	queue.add(thisObject);
	numberAdded++;
    }

    /**
     * Returns with the object currently at the head of the queue. Throws a
     * fatal error if the queue is empty.
     */
    public Object takeFromHead() {
	int qSize = queue.size();
	T thisObject = queue.get(0);
	try {
	    if (qSize == 0) {
		throw new Exception("Queue empty in takeFromHead");
	    }
	    for (int loop = 0; loop < qSize - 1; loop++) {
		queue.set(loop, queue.get(loop + 1));
	    }
	    queue.remove(qSize - 1);
	} catch (Exception e) {
	    System.err.println("ERROR: " + e.getMessage());
	    System.exit(99);
	}
	numberRemoved++;
	return thisObject;
    }

    /**
     * Returns with the object currently at the tail of the queue. Throws a
     * fatal error if the queue is empty.
     */
    public T takeFromTail() {
	int qSize = queue.size();
	try {
	    if (qSize == 0) {
		throw new Exception("Queue empty in takeFromTail");
	    }
	} catch (Exception e) {
	    System.err.println("ERROR: " + e.getMessage());
	    System.exit(99);
	}
	T thisObject = queue.get(qSize - 1);
	queue.remove(qSize - 1);
	numberRemoved++;
	return thisObject;
    }

    /**
     * Returns with the number added to the queue so far.
     */
    public int getNumberAdded() {
	return numberAdded;
    }

    /**
     * Returns with the number removed from the queue so far.
     */
    public int getNumberRemoved() {
	return numberRemoved;
    }

    public int size() {
	return queue.size();
    }
}
