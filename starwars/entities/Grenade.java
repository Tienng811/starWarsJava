package starwars.entities;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Capability;
import starwars.SWEntity;
import starwars.actions.Take;
import starwars.actions.Throw;

/**
 * A grenade is an entitiy that can be taken by actors
 * and then later thrown to explode and cause substantial damage
 * to the entities up to 2 squares away from the explosion.
 * 
 * @author biondiwiyono
 *
 */

public class Grenade extends SWEntity{
	
	/**
	 * Constructor for the <code>Grenade</code> class. This constructor will,
	 * <ul>
	 * 	<li>Initialize the message renderer for the <code>Grenade</code></li>
	 * 	<li>Set the short description of this <code>Grenade</code>>
	 * 	<li>Set the long description of this <code>Grenade</code> 
	 *  <li>Set hitpoints of the Grenade to 10</li>
	 * 	<li>Add a <code>Take</code> affordance to this <code>Grenade</code> so it can be taken</li> 
	 * </ul>
	 * 
	 * @param m <code>MessageRenderer</code> to display messages.
	 * 
	 * @see {@link starwars.actions.Throw}
	 */
	public Grenade(MessageRenderer m) {
		super(m);
		
		this.shortDescription = "A grenade";
		this.longDescription = "A grenade. Boom!";
		this.hitpoints = 10; // start with a nice powerful, sharp axe
		
		this.addAffordance(new Take(this, m));//add the take affordance so that the LightSaber can be taken by SWActors

	}
	
	/**
	 * This method returns the symbol of the Grenade.
	 * 
	 * @return "G" a string indicating the symbol of the grenade
	 */
	@Override
	public String getSymbol() {
		return "G";
	}
	
	/**
	 * Grenade will not be affected by any kinds of damage
	 * 
	 */
	@Override
	public void takeDamage(int damage) {
		return;
	}
}
