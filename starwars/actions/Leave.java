package starwars.actions;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWAction;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntityInterface;
import starwars.entities.Grenade;
import starwars.*;

/**
 * <code>SWAction</code> that lets a <code>SWActor</code> leave an object.
 * 
 * @author biondiwiyono
 */

public class Leave extends SWAffordance{

	
	/**
	 * Constructor for the <code>Leave</code> Class. Will initialize the message renderer, the target and 
	 * set the priority of this <code>Action</code> to 1 (lowest priority is 0).
	 * 
	 * @param theTarget a <code>SWEntity</code> that is being left
	 * @param m the message renderer to display messages
	 */
	public Leave (SWEntityInterface theTarget, MessageRenderer m) {
		super(theTarget, m);
		priority = 1;
	}
	
	/**
	 * Returns if or not this <code>Leave</code> can be performed by the <code>SWActor a</code>.
	 * <p>
	 * This method returns true if and only if <code>a</code> is carrying an item.
	 *  
	 * @author 	biondiwiyono
	 * @param 	a the <code>SWActor</code> being queried
	 * @return 	true if the <code>SWActor</code> is can leave this item, false otherwise
	 * @see		{@link starwars.SWActor#getItemCarried()}
	 */
	@Override
	public boolean canDo(SWActor a) {
		return a.getItemCarried()!=null;
	}
	
	/**
	 * Perform the <code>Leave</code> action by setting the item carried by the <code>SWActor</code> to the target
	 * and putting the item back to the map again (
	 * the <code>SWActor a</code>'s item carried would be the target of this <code>Take</code>).
	 * <p>
	 * This method should only be called if the <code>SWActor a</code> is alive.
	 * 
	 * @author 	biondiwiyono
	 * @param 	a the <code>SWActor</code> that is leaving the target item
	 * @see 	{@link #theTarget}
	 */
	@Override
	public void act(SWActor a) {
		if (target instanceof SWEntityInterface) {
			SWEntityInterface theItem = (SWEntityInterface) target;
			a.setItemCarried(null);
			
			SWLocation playerLocation = SWAction.getEntitymanager().whereIs(a);
			SWAction.getEntitymanager().setLocation(theItem, playerLocation);
			
			target.removeAffordance(this);
			target.addAffordance(new Take(theItem, this.messageRenderer));
			
		}
	}
	
	/**
	 * A String describing what this action will do, suitable for display in a user interface
	 * 
	 * @author biondiwiyono
	 * @return String comprising "leave " and the short description of the target of this <code>Leave</code>
	 */
	@Override
	public String getDescription() {
		return "Leave " + target.getShortDescription();
	}
	
}
