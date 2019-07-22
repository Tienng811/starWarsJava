package starwars.entities;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Capability;
import starwars.SWActor;
import starwars.SWEntity;
import starwars.SWWorld;
import starwars.actions.SwitchWorld;
import starwars.actions.Take;

/**
 * A portal door inside a Jawa Sandcrawler that lets actors
 * travel back to the outside world
 * 
 * @author biondiwiyono
 *
 */

public class Door extends SWEntity {
	
	/**
	 * This attribute contains the object reference to the outside/main world.
	 */
	private SWWorld toWorld;
	
	/**
     * Constructor for the <code>Door</code> class. This constructor will,
	 * <ul>
	 * 	<li>Initialize the message renderer for the <code>Door</code></li>
	 * 	<li>Set the short description of this <code>Door</code>>
	 * 	<li>Set the long description of this <code>Door</code> 
	 *  <li>Set hitpoints of the Door to 100</li>
	 *  <li>Initialize the outside world the door is leading to</li>
	 * 	<li>Add a <code>SwitchWorld</code> affordance to this <code>Door</code> so it can be taken</li> 
	 * </ul>
	 * 
	 * @param m <code>MessageRenderer</code> to display messages.
	 * @param world the outside world that the door will lead to.
	 */
	public Door(MessageRenderer m, SWWorld world) {
		super(m);
		
		this.shortDescription = "A mysterious door";
		this.longDescription = "A mysterious door that might lead to a different world!";
		this.hitpoints = 100;
		toWorld = world;
		SwitchWorld switchWorld = new SwitchWorld(this, m);
		this.addAffordance(switchWorld);
	}
	
	/**
	 * This method returns the symbol of the Door.
	 * 
	 * @return "|_|" a string indicating the symbol of the Door
	 */
	@Override
	public String getSymbol() {
		return "|_|";
	}
	
	/**
	 * Door will not be affected by any kinds of damage
	 * 
	 */
	@Override
	public void takeDamage(int damage) {
		return;
	}
	
	/**
	 * Return the outside world object.
	 * 
	 * @return toWorld the outside world the door is leading to
	 */
	public SWWorld getToWorld(){
		return toWorld;
	}
}
