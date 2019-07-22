package starwars.entities.actors.behaviors;

import java.util.ArrayList;
import java.util.List;

import edu.monash.fit2099.simulator.matter.Affordance;
import edu.monash.fit2099.simulator.matter.EntityManager;
import starwars.ForceActor;
import starwars.SWActor;
import starwars.SWEntityInterface;
import starwars.SWLocation;
import starwars.SWWorld;
import starwars.actions.Train;

/**
 * get all the entities in the same location with the actor that are trainable
 * @author tdngu38
 *
 */
public class TrainNeighbours {
	public static NeighbourInformation trainLocals(SWActor actor, SWWorld world) {
		SWLocation location = world.getEntityManager().whereIs(actor);
		EntityManager<SWEntityInterface, SWLocation> em = world.getEntityManager();
		List<SWEntityInterface> entities = em.contents(location);
		
		ArrayList<NeighbourInformation> trainables = new ArrayList<NeighbourInformation>();
		
		for (SWEntityInterface e : entities) {
			// Figure out if we should be training this entity
			if( e != actor && 
					(e instanceof ForceActor && 
							((SWActor)e).getTeam() == actor.getTeam())) {
				for (Affordance a : e.getAffordances()) {
					if (a instanceof Train) {
						trainables.add(new NeighbourInformation(e, a));
						break;
					}
				}
			}
		}

		// if there's at least one thing we can train, randomly choose
		// something to train
		if (trainables.size() > 0) {
			return trainables.get((int) (Math.floor(Math.random() * trainables.size())));
		} else {
			return null;
		}
	}
}