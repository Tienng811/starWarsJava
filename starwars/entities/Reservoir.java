package starwars.entities;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWAffordance;
import starwars.SWEntity;
import starwars.actions.Dip;

/**
 * Class to represent a water reservoir.  <code>Reservoirs</code> are currently pretty passive.
 * They can be dipped into to fill fillable entities (such as <code>Canteens</code>.  They
 * are assumed to have infinite capacity.
 * 
 * @author 	ram
 * @see 	{@link starwars.entities.Canteen}
 * @see {@link starwars.entites.Fillable}
 * @see {@link starwars.actions.Fill} 
 */
public class Reservoir extends SWEntity {

	/**
	 * Constructor for the <code>Reservoir</code> class. This constructor will,
	 * <ul>
	 * 	<li>Initialize the message renderer for the <code>Reservoir</code></li>
	 * 	<li>Set the short description of this <code>Reservoir</code> to "a water reservoir</li>
	 * 	<li>Set the long description of this <code>Reservoir</code> to "a water reservoir..."</li>
	 * 	<li>Add a <code>Dip</code> affordance to this <code>Reservoir</code> so it can be taken</li> 
	 *	<li>Set the symbol of this <code>Reservoir</code> to "T"</li>
	 *	<li>Initialise 40 hitpoints for each Reservoir</li>
	 * </ul>
	 * 
	 * @param 	m <code>Message Renderer</code> to display messages.
	 * @see 	{@link starwars.actions.Dip} 
	 */
	public Reservoir(MessageRenderer m) {
		super(m);
		SWAffordance dip = new Dip(this, m);
		this.addAffordance(dip);	
		
		this.setLongDescription("a water reservoir.");
		this.setShortDescription("a water reservoir, full of cool, clear, refreshing water");
		this.setSymbol("W");
		//Set the hitpoints of newly built reservoirs to 40 at the start
		this.setHitpoints(40);
	}

	@Override 
	public String getShortDescription() {
		return shortDescription;
	}
	
	public String getLongDescription() {
		return longDescription;
	}
	
	
	/**
	 * @Override
	 * 
	 * Reservoir does take damage when attacked by grenade
	 * When hitpoints get below 20 and 0, the descriptions and symbol of the reservoir will dynamically change
	 * 
	 * @param damage - the amount of damage that would be inflicted on the Reservoir
	 */
	public void takeDamage(int damage) {
		super.takeDamage(damage);
		
		if(this.getHitpoints() < 20) {
			this.setShortDescription("a damaged water reservoir");
			this.setLongDescription("a damaged water reservoir, leaking slowly");
			this.setSymbol("V");
		} else if(this.getHitpoints() < 0) {
			this.setShortDescription("the wreckage of a water reservoir");
			this.setLongDescription("the wreckage of a water reservoir, surrounded by a slightly damp soil");
			this.setSymbol("X");
		}
	}
	
}
