package starwars.entities.actors.behaviors;

import java.util.*;
import java.util.List;
import edu.monash.fit2099.gridworld.Grid.CompassBearing;
import edu.monash.fit2099.simulator.matter.EntityManager;
import edu.monash.fit2099.simulator.space.Direction;
import starwars.SWActor;
import starwars.SWEntityInterface;
import starwars.SWLocation;
import starwars.SWWorld;
import starwars.entities.actors.Droid;

/**
 * This class is responsible for generating the next direction of the Droid 
 * @author biondiwiyono
 *
 */

public class DroidMovement {
	
	/**
	 * This method will return null if Droid is on the same location with the owner.
	 * Moreover, this method looks at the neighbouring positions around the Droid
	 * If the owner is around the neighbours, Droid will move towards the owner's location.
	 * However, when Droid can't find its owners/lost, it will walk to a random direction
	 * until it can't move to that direction again, and a new random direction will be returned.
	 * 
	 * @param a is the Droid associated with the DroidMovement.
	 * @param world is the world the Droid is located in.
	 * @return the next direction that will be scheduled for the Droid's move action
	 */
	public Direction getDroidMovement(Droid a, SWWorld world) {
		SWActor owner = a.getOwner();
		
		SWLocation droidLocation = world.getEntityManager().whereIs(a);
		SWLocation ownerLocation = world.getEntityManager().whereIs(owner);
		EntityManager<SWEntityInterface, SWLocation> em = world.getEntityManager();
		
		ArrayList<Direction> directionss = new ArrayList<Direction>();
		directionss = a.getPossibleDirections();
		
		if(droidLocation == ownerLocation) {
			a.say(a.getShortDescription() + " found the owner! Not moving");
			return null;
		}
		else {
			for (Direction dir : directionss) {
				SWLocation neighbourLocation = (SWLocation) droidLocation.getNeighbour(dir);
				List<SWEntityInterface> neighbourEntities = em.contents(neighbourLocation);
				
				if (neighbourEntities != null) {
					for (SWEntityInterface entity:neighbourEntities) {
						if (entity == owner) {
							a.say(a.getShortDescription() + " will follow its master!");
							return dir;
						}
					}
				}
			}
			
			
			Direction randomDirection = a.getRandomDirection();
			
			if (randomDirection == null || world.canMove(a, randomDirection) == false) {				
				do {
			
					randomDirection = directionss.get((int) Math.floor(Math.random()*directionss.size()));
					directionss.remove(randomDirection);
					a.setRandomDirection(randomDirection);
					
				} while(world.canMove(a, randomDirection) == false);

			}
			
			a.say(a.getShortDescription() + " can't find its owner. Walking randomly.");
			return randomDirection;
		}
	}
	
}
