package starwars.actions;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWAction;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntityInterface;
import starwars.SWGrid;
import starwars.entities.Grenade;
import starwars.entities.actors.Droid;
import starwars.entities.actors.JawaSandcrawler;

/**
 * <code>SWAction</code> that lets a <code>Jawa Sandcrawler</code> eats a Droid.
 * 
 * @author biondiwiyono
 *
 */

public class Eat extends SWAffordance{
	
	/**
	 * Constructor for the <code>Eat</code> Class. Will initialize the message renderer, the target and 
	 * set the priority of this <code>Action</code> to 1 (lowest priority is 0).
	 * 
	 * @param theTarget a <code>SWEntity</code> that is being eaten
	 * @param m the message renderer to display messages
	 */
	public Eat(SWEntityInterface theTarget, MessageRenderer m) {
		super(theTarget, m);
		priority = 1;
	}
	
	/**
	 * Returns if or not this <code>Eat</code> can be performed by the <code>SWActor a</code>.
	 * <p>
	 * This method returns true if and only if <code>a</code> is a Jawa Sandcrawler
	 *  
	 * @author 	biondiwiyono
	 * @param 	a the <code>SWActor</code> being queried
	 * @return 	true if the <code>SWActor</code> can perform the eat affordance
	 */
	@Override
	public boolean canDo(SWActor a) {
		return a instanceof JawaSandcrawler;
	}
	
	/**
	 * This method checks if the target entity is a <code>Droid</code>
	 * Next the <code>Droid</code>'s location in the entitymanager will be modified
	 * to reflect the new insideWorld that the <code>Droid</code> will be placed to.
	 * 
	 * The exact coordinate of the <code>Droid</code>'s location will be randomised
	 * Afterwards, remove the eat affordance from the <code>Droid</code>.
	 * 
	 * @author 	biondiwiyono
	 * @param 	a the <code>SWActor</code> that is eating the target item
	 */
	@Override
	public void act(SWActor a) {
		if (target instanceof Droid) {
			SWEntityInterface targetDroid = (SWEntityInterface) target;
			SWGrid jawaWorldGrid = ((JawaSandcrawler) a).getInsideWorld().getGrid();
			
			a.setWorld(((JawaSandcrawler) a).getInsideWorld());
			
			int xCoordinate = (int) Math.floor(Math.random() * jawaWorldGrid.getWidth());
			int yCoordinate = (int) Math.floor(Math.random() * jawaWorldGrid.getHeight());
			SWAction.getEntitymanager().setLocation(targetDroid, jawaWorldGrid.getLocationByCoordinates(xCoordinate, yCoordinate));
			
			
			//remove the Eat affordance
			target.removeAffordance(this);
		}
	}
	
	/**
	 * A String describing what this action will do, suitable for display in a user interface
	 * 
	 * @author ram
	 * @return String comprising "leave " and the short description of the target of this <code>Leave</code>
	 */
	@Override
	public String getDescription() {
		return "eat " + target.getShortDescription();
	}

	
}
