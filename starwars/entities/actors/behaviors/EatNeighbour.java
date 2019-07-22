package starwars.entities.actors.behaviors;

import java.util.ArrayList;
import java.util.List;

import edu.monash.fit2099.simulator.matter.Affordance;
import edu.monash.fit2099.simulator.matter.EntityManager;
import starwars.SWActor;
import starwars.SWEntityInterface;
import starwars.SWLocation;
import starwars.SWWorld;
import starwars.actions.Attack;
import starwars.actions.Eat;
import starwars.entities.actors.Droid;

/**
 * get all the entities in the same location with the actor that are eatable
 * @author biondiwiyono
 *
 */

public class EatNeighbour {
	
	/**
	 * 
	 * This method will supply information about the target where the Eat
	 * affordance will be performed to.
	 * 
	 * This method will find all entities on the same location with the actor
	 * and determine one main target (which will be a <code>Droid</code>).
	 * 
	 * @param actor actor who is performing the Eat affordance
	 * @param world the world where the action is performed at
	 * @return
	 */
	public static NeighbourInformation eatlocal(SWActor actor, SWWorld world) {
		SWLocation location = world.getEntityManager().whereIs(actor);
		EntityManager<SWEntityInterface, SWLocation> em = world.getEntityManager();
		List<SWEntityInterface> entities = em.contents(location);
		
		ArrayList<NeighbourInformation> eatable = new ArrayList<NeighbourInformation>();
		
		for (SWEntityInterface e : entities) {
			// Figure out if we should be attacking this entity
			if( e != actor && e instanceof Droid ) {
				for (Affordance a : e.getAffordances()) {
					if (a instanceof Eat) {

						eatable.add(new NeighbourInformation(e, a));
						break;
					}
				}
			}
		}
		
		if (eatable.size() > 0) {
			return eatable.get((int) (Math.floor(Math.random() * eatable.size())));
		} else {
			return null;
		}
		
	}
}
