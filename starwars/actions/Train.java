package starwars.actions;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.ForceActor;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntityInterface;

/**
 * <code>SWAction</code> that lets a <code>ForceActor</code> with force level > 75 to train other <code>ForceActor</code> .
 * @author tdngu38
 *
 */

public class Train extends SWAffordance {
	
	/**
	 * Constructor for the <code>Train</code> class. Will initialize the <code>messageRenderer</code> and
	 * give <code>Train</code> a priority of 1 (lowest priority is 0).
	 * 
	 * @param theTarget the target entity that is being trained
	 * @param m messageRenderer to display messages
	 */
	public Train(SWEntityInterface theTarget, MessageRenderer m) {
		super(theTarget, m);
		priority = 1;
	}
	
	/**
	 * Determine whether a particular <code>SWActor a</code> can train the target.
	 * 
	 * @author 	tdngu38
	 * @param 	a the <code>SWActor</code> being queried
	 * @return 	true only if actor is an instance of <code>ForceActor</code> and has force level above 75 
	 * 
	 */
	@Override
	public boolean canDo(SWActor a) {
		if (a instanceof ForceActor){
			int aForceLevel = ((ForceActor) a).getForceLevel();
			if (aForceLevel > 75){
				return true;
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}
	}

	/**
	 * Perform <code>Train</code> on an entity
	 * @param a the <code>SWActor</code> that performs this action
	 * @pre 	this method should only be called if the <code>SWActor a</code> is an instance of <code>ForceActor</code>
	 * @pre		the target force level must be lower than the actor force level
	 * @post	if the target force level reaches 100, remove this affordance
	 */
	@Override
	public void act(SWActor a) {
		if (target instanceof ForceActor) {
			int targetForceLevel = ((ForceActor) target).getForceLevel();
			int aForceLevel = ((ForceActor) a).getForceLevel(); 
			
			if (targetForceLevel < aForceLevel) {
				((ForceActor) target).setForceLevel(targetForceLevel + 10);
				a.say(a.getShortDescription() + " has trained " + target.getShortDescription());
				a.say(target.getShortDescription() + "'s force level is now " + ((ForceActor) target).getForceLevel());
			}
			
			targetForceLevel = ((ForceActor) target).getForceLevel();
			
			if (targetForceLevel > 100){
				((ForceActor) target).setForceLevel(100);
				target.removeAffordance(this);
				target.say(target.getShortDescription() + " is way too strong to be trained");
			}
		}
	}

	/**
	 * get description for this action
	 */
	@Override
	public String getDescription() {
		return "train" + target.getShortDescription();
	}
}
