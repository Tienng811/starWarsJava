package starwars.entities.actors;

import edu.monash.fit2099.simulator.space.Direction;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWWorld;
import starwars.Team;
import starwars.actions.Attack;
import starwars.actions.Eat;
import starwars.actions.Move;
import starwars.actions.Own;
import starwars.entities.actors.behaviors.DroidMovement;

/**
 * Droid 
 * is a robot that wanders around the map following its owner
 * But it gets lost sometimes and will just randomly walk somewhere
 * When droid does not have any owner it will be idle and immobile
 * 
 * You can create multiple Droid objects
 * @author biondiwiyono
 *
 */

public class Droid extends SWActor{
	
	/**
	 * This attribute represents the owner of the Droid which is an <code>SWActor</code>
	 */
	private SWActor owner;
	
	/**
	 * This attribute represents the random direction a Droid will move towards when they are lost
	 */
	private Direction randomDirection = null;
	
	
	/**
	 * Constructor for the <code>Droid</code> class. This constructor will,
	 * <ul>
	 * 	<li>Initialize the message renderer for the <code>Droid</code></li>
	 * 	<li>Initialize the world for this <code>Droid</code></li>
	 *  <li>Initialize the <code>Team</code> for this <code>Droid</code></li>
	 * 	<li>Initialize the hit points for this <code>Droid</code></li>
	 * 	<li>Set this <code>Droid</code> an initial owner as an <code>SWActor</code></li>
	 * 	<li>add an Own affodance to the Droid so that other actors can own it</li>
	 * 	<li>add an Eat affodance to the Droid so that JawaSandcrawler can eat it</li> 
	 * </ul>
	 * 
	 * @param team the <code>Team</code> to which the this <code>Droid</code> belongs to
	 * @param hitpoints the hit points of this <code>Droid</code> to get started with
	 * @param m <code>MessageRenderer</code> to display messages.
	 * @param world the <code>SWWorld</code> world to which this <code>Droid</code> belongs to
	 * @param initialOwner the initial owner the Droid is assigned to
	 * 
	 */
	public Droid(Team team, int hitpoints, MessageRenderer m, SWWorld world, SWActor initialOwner) {
		super(team, hitpoints, m, world);
		owner = initialOwner;
		
		SWAffordance own = new Own(this, m);
		SWAffordance eat = new Eat(this,m);
		this.addAffordance(own);
		this.addAffordance(eat);
	}
	
	/**
	 * Returns the owner of the Droid
	 * @return the owner of the Droid as an <code>SWActor</code>
	 */
	public SWActor getOwner() {
		return owner;
	}
	
	/**
	 * Set a new owner for the Droid
	 * It will also set the team of the Droid according to the new owner's team
	 * If the owner is removed (set to null), Droid will be in team Neutral.
	 * 
	 * @param newOwner the new owner of the Droid.
	 */
	public void setOwner(SWActor newOwner) {
		this.owner = newOwner;
		
		if (newOwner != null) {
			this.setTeam(newOwner.getTeam());
		} else {
			this.setTeam(Team.NEUTRAL);
		}
	}
	
	/**
	 * Returns a random Direction the Droid is moving towards to
	 * @return a random Direction the Droid is moving towards to
	 */
	public Direction getRandomDirection() {
		return randomDirection;
	}
	
	/**
	 * Set the new Direction for the Droid
	 * @param newDirection the new Direction the Droid will be moving towards to
	 */
	public void setRandomDirection(Direction newDirection) {
		this.randomDirection = newDirection;
	}
	
	/**
	 * 
	 * This method will make the Droid make its next move based on the DroidMovement class.
	 * However, here are several conditions where Droid will stay immobile:
	 * <ul>
	 * 	<li>Droid's HP is below 0 / dead</li>
	 * 	<li>Droid does not have any owner</li>
	 *  <li>Droid's owner is dead</li>
	 *  <li>Droid is on the same location with its owner</li>
	 * </ul>
	 * 
	 * This method also determines if the Droid is standing on badlands.
	 * If yes, it will take 5 damage to the Droid.
	 * 
	 * @author biondiwiyono
	 */
	@Override
	public void act() {
		
		if(isDead()) {
			say(this.getShortDescription() + "[" + this.getHitpoints() + "]" + " runs out of hp. It is now immobile.");
			return;
		}
		
		if(this.owner == null) {
			return;
		}
		
		if(this.owner.isDead()) {
			this.setOwner(null);
			return;
		}
		
		SWWorld droidWorld = this.world;
		DroidMovement droidMove = new DroidMovement();
		Direction nextMove = droidMove.getDroidMovement(this, droidWorld);

		
		if (nextMove != null) {
			//say(getShortDescription() + " is moving");
			Move myMove = new Move(nextMove, messageRenderer, world);
			scheduler.schedule(myMove, this, 0);
		}
		
		char standingLocation = droidWorld.getEntityManager().whereIs(this).getSymbol();
		
		if(standingLocation == 'b') {
			say(this.getShortDescription() + " [" + this.getHitpoints() + "] is moving on badlands, and losing 5 hp!");
			this.takeDamage(5);
			
		}
		
	}
}
