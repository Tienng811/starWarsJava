package starwars.actions;

import java.util.ArrayList;
import java.util.List;
import edu.monash.fit2099.simulator.matter.EntityManager;
import edu.monash.fit2099.simulator.space.Direction;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWAction;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntityInterface;
import starwars.SWLocation;
import starwars.entities.Grenade;

/**
 * <code>SWAction</code> that lets a <code>SWActor</code> throw a grenade.
 * @author biondiwiyono
 *
 */

public class Throw extends SWAffordance{
	
	/**
	 * Damage dealt for entities on the same spot where the grenade is thrown
	 */
	private final int DROPDAMG = 20;
	
	/**
	 * Damage dealt for entities in 1 space around the explosion
	 */
	private final int ONESTEPDAMG = 10;
	
	/**
	 * Damage dealt for entities in 2 spaces around the explosion
	 */
	private final int TWOSTEPDAMG = 5;
	
	/**
	 * Constructor for the <code>Throw</code> Class. Will initialize the message renderer, the target and 
	 * set the priority of this <code>Action</code> to 1 (lowest priority is 0).
	 * 
	 * @param theTarget a <code>SWEntity</code> that is being thrown
	 * @param m the message renderer to display messages
	 */
	public Throw (SWEntityInterface theTarget, MessageRenderer m) {
		super(theTarget, m);
		priority = 1;
	}
	
	/**
	 * Returns if or not this <code>Throw</code> can be performed by the <code>SWActor a</code>.
	 * <p>
	 * This method returns true if and only if <code>a</code> is carrying a grenade.
	 * 
	 * @author 	biondiwiyono
	 * @param 	a the actor who is performing the Throw
	 * @return 	true if the <code>SWActor</code> is can throw this item, false otherwise
	 * @see		{@link starwars.SWActor#getItemCarried()}
	 */
	@Override
	public boolean canDo(SWActor a) {
		return a.getItemCarried() instanceof Grenade;
	}
	
	/**
	 *  Perform the Throw action on the entity
	 *   
	 * @author 	tdngu38
	 * @param a the actor who is performing the Throw
	 */
	@Override
	public void act(SWActor a) {
		if (target instanceof Grenade) {
			EntityManager<SWEntityInterface, SWLocation> em = SWAction.getEntitymanager();
			
			SWLocation dropLocation = em.whereIs(a);
			
			ArrayList<Direction> directions = new ArrayList<Direction>();
			ArrayList<SWLocation> oneStepProx = new ArrayList<SWLocation>();
			ArrayList<SWLocation> twoStepProx = new ArrayList<SWLocation>() ;
			
			List<SWEntityInterface> dropLocationEntities = new ArrayList<SWEntityInterface>();
			dropLocationEntities = em.contents(dropLocation);
			dropLocationEntities.remove(a);
			
			directions = a.getPossibleDirections();
			
			for (Direction dir: directions){
				SWLocation loc = (SWLocation) dropLocation.getNeighbour(dir);
				oneStepProx.add(loc);
				List<SWEntityInterface> neighbourEntities = em.contents(loc);
				explode(neighbourEntities, ONESTEPDAMG);
			}

			for (SWLocation loc: oneStepProx){
				for (Direction dir: directions){
					SWLocation location = (SWLocation) loc.getNeighbour(dir);
					if (!twoStepProx.contains(location)){
						twoStepProx.add((SWLocation) loc.getNeighbour(dir));
					}
			  }
			}
			
			twoStepProx.removeAll(oneStepProx);
			twoStepProx.remove(dropLocation);
			
			for (SWLocation loc: twoStepProx){
				List<SWEntityInterface> neighbourEntities = em.contents(loc);
				explode(neighbourEntities, TWOSTEPDAMG);
			}
			
			explode(dropLocationEntities, DROPDAMG);
			
			a.setItemCarried(null);
		}
	}
	
	/**
	 * A String describing what this action will do, suitable for display in a user interface
	 * 
	 * @author biondiwiyono
	 * @return String comprising "Throw " and the short description of the target of this <code>Throw</code>
	 */
	@Override
	public String getDescription() {
		return "Throw " + target.getShortDescription();
	}
	
	/**
	 * This method damage the entities inside the exploding area
	 * 
	 * @param entities the entities being damaged
	 * @param proximityDamg the damage for that particular area
	 */
	private void explode(List<SWEntityInterface> entities, int proximityDamg){
		if (entities != null){
			for (SWEntityInterface en: entities){
				en.takeDamage(proximityDamg);
			}
		}
	}
	
}
