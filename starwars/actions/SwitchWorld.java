package starwars.actions;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.ForceActor;
import starwars.SWAction;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntityInterface;
import starwars.SWInsideWorld;
import starwars.SWLocation;
import starwars.entities.Door;
import starwars.entities.actors.Droid;
import starwars.entities.actors.JawaSandcrawler;
import starwars.swinterfaces.SWGridController;
import starwars.swinterfaces.SWGridTextInterface;

/**
 * <code>SWAction</code> that lets a <code>SWActor</code> switch between worlds.
 * 
 * @author biondiwiyono
 *
 */

public class SwitchWorld extends SWAffordance {
	
	/**
	 * Constructor for the <code>SwitchWorld</code> Class. Will initialize the message renderer, the target and 
	 * set the priority of this <code>Action</code> to 1 (lowest priority is 0).
	 * 
	 * @param theTarget a <code>SWEntity</code> that is used as the portal to switch world
	 * @param m the message renderer to display messages
	 */
	public SwitchWorld(SWEntityInterface theTarget, MessageRenderer m) {
		super(theTarget, m);
		priority = 1;
	}
	
	
	/**
	 * A String describing what this action will do, suitable for display in a user interface
	 * 
	 * @author biondiwiyono
	 * @return String comprising "switch to " and the short description of the target of this <code>Leave</code>
	 */
	@Override
	public String getDescription() {
		if (target instanceof Door) {
			return "switch back to outside world";
		} else {
			return "switch to " + target.getShortDescription() + "'s world";
		}
	}
	
	/**
	 * Returns if or not this <code>SwitchWorld</code> can be performed by the <code>SWActor a</code>.
	 * <p>
	 * This method returns true if and only if <code>a</code> is an instance of ForceActor
	 *  
	 * @author 	biondiwiyono
	 * @param 	a the <code>SWActor</code> being queried
	 * @return 	true if the <code>SWActor</code> is can switch to different world, false otherwise
	 */
	@Override
	public boolean canDo(SWActor a) {
		if(a instanceof ForceActor) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Perform the <code>SwitchWorld</code> depending on the target entity.
	 * If the target is a <code>JawaSandcrawler</code>, the actor performing the <code>SwitchWorld</code> action will
	 * go into <code>JawaSandcrawler</code>'s inside world.
	 * 
	 * If the target is a <code>Door</code>, it will take the actor back to the outside world.
	 * 
	 * @author 	biondiwiyono
	 * @param 	a the <code>SWActor</code> performing the SwitchWorld action
	 * @see 	{@link #theTarget}
	 */
	public void act(SWActor a) {
		
		if (target instanceof JawaSandcrawler) {
			JawaSandcrawler theTarget = (JawaSandcrawler) target;
			SWGridTextInterface.setGrid(theTarget.getText());
			SWAction.getEntitymanager().remove(a);
			a.setWorld(theTarget.getInsideWorld());
			SWLocation loc = theTarget.getInsideWorld().getGrid().getLocationByCoordinates(2, 2);
			SWAction.getEntitymanager().setLocation(a, loc);
		}
		else if (target instanceof Door) {
			Door theDoor = (Door) target;
			SWInsideWorld actorWorld = (SWInsideWorld) a.getWorld();
			SWActor owner = actorWorld.getOwner();
			SWGridTextInterface.setGrid(theDoor.getToWorld().getGridController().getGridText());
			a.setWorld(theDoor.getToWorld());
			SWLocation loc = theDoor.getToWorld().getEntitymanager().whereIs(owner);
			SWAction.getEntitymanager().setLocation(a, loc);
		}
	}
}
