package starwars.actions;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWAction;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntityInterface;
import starwars.SWLocation;
import starwars.entities.actors.Droid;

/**
 * <code>SWAction</code> that lets a <code>SWActor</code> own a Droid.
 * 
 * @author biondiwiyono
 */

public class Own extends SWAffordance{
	
	/**
	 * Constructor for the <code>Own</code> Class. Will initialize the message renderer, the target and 
	 * set the priority of this <code>Action</code> to 1 (lowest priority is 0).
	 * 
	 * @param theTarget a <code>SWEntity</code> that is being owned
	 * @param m the message renderer to display messages
	 */
	public Own(SWEntityInterface theTarget, MessageRenderer m) {
		super(theTarget, m);
		priority = 1;
	}
	
	/**
	 * A String describing what this action will do, suitable for display in a user interface
	 * 
	 * @author biondiwiyono
	 * @return String comprising "Own " and the short description of the target of this <code>Own</code>
	 */
	@Override
	public String getDescription() {
		return "Own " + target.getShortDescription();
	}
	
	/**
	 * Returns if or not this <code>Own</code> can be performed by the <code>SWActor a</code>.
	 * <p>
	 * This method returns true if and only if <code>a</code> is not a Droid, and the target Droid does not have an owner.
	 *  
	 * @author 	biondiwiyono
	 * @param 	a the <code>SWActor</code> being queried
	 * @return 	true if the <code>SWActor</code> can own the target Droid, false otherwise
	 */	
	@Override
	public boolean canDo(SWActor a) {
		if (!(a instanceof Droid )){
			Droid droidTarget = (Droid) this.target;
			return droidTarget.getOwner() == null;
		}
		else {
			return false;
		}
	}
	
	/**
	 * This method will essentially set the owner of the target Droid
	 * to the SWActor <code>a</code> and then remove this instance of Own affordance
	 * from the target Droid.
	 */
	public void act(SWActor a) {
		if (target instanceof Droid) {
			Droid droidTarget = (Droid) this.target;
			
			droidTarget.setOwner(a);
			droidTarget.removeAffordance(this);
			
			droidTarget.say(droidTarget.getShortDescription() + " is now owned by " + droidTarget.getOwner().getShortDescription());
			
		}
	}
}
