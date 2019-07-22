package starwars;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;

/**
 * Super class for Luke and Ben Kenobi as they are the only actors that can use Force power.
 * @author biondiwiyono
 *
 */

public abstract class ForceActor extends SWActor {
	/**
	 * an attribute that represents how strong the force level of a ForceActor has
	 */
	private int forceLevel;
	
	/**
	 * Constructor for the <code>ForceActor</code> class. This constructor will,
	 * <ul>
	 * 	<li>Initialize the message renderer for the <code>ForceActor</code></li>
	 * 	<li>Initialize the world for this <code>ForceActor</code></li>
	 *  <li>Initialize the <code>Team</code> for this <code>ForceActor</code></li>
	 * 	<li>Initialize the hit points for this <code>ForceActor</code></li>
	 * </ul>
	 * 
	 * @param team the <code>Team</code> to which the this <code>ForceActor</code> belongs to
	 * @param hitpoints the hit points of this <code>ForceActor</code> to get started with
	 * @param m <code>MessageRenderer</code> to display messages.
	 * @param world the <code>SWWorld</code> world to which this <code>ForceActor</code> belongs to
	 * 
	 */
	public ForceActor(Team team, int hitpoints, MessageRenderer m, SWWorld world) {
		super(team, hitpoints, m, world);
	}
	
	/**
	 * Returns the level of force a ForceActor has
	 * 
	 * @return the level of force a ForceActor has
	 */
	public int getForceLevel() {
		return forceLevel;
	}
	
	/**
	 * Set the force level of a ForceActor to the specified parameter
	 * 
	 * @param fl is the force level in integer to be set to the ForceActor
	 */
	public void setForceLevel(int fl){
			this.forceLevel = fl;
	}
}
