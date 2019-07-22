package starwars.entities.actors;

import java.util.List;


import edu.monash.fit2099.simulator.matter.EntityManager;
import edu.monash.fit2099.simulator.space.Direction;
import edu.monash.fit2099.simulator.time.Scheduler;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActor;
import starwars.SWEntityInterface;
import starwars.SWInsideWorld;
import starwars.SWLocation;
import starwars.SWWorld;
import starwars.Team;
import starwars.actions.Move;
import starwars.actions.SwitchWorld;
import starwars.entities.Door;
import starwars.entities.actors.behaviors.AttackNeighbours;
import starwars.entities.actors.behaviors.EatNeighbour;
import starwars.entities.actors.behaviors.NeighbourInformation;
import starwars.entities.actors.behaviors.Patrol;
import starwars.swinterfaces.SWGridController;
import starwars.swinterfaces.SWGridTextInterface;

/**
 * Jawa Sandcrawler
 * is a wandering starcraft around the starwars world
 * that eats every droid along the way and send them to
 * a small world inside its body.
 * 
 * You can create multiple Jawa Sandcrawler objects
 * @author biondiwiyono
 *
 */

public class JawaSandcrawler extends SWActor{
	
	/**
	 * This attribute contains the inside world in Jawa Sandcrawler's body.
	 */
	private SWWorld insideWorld;
		
	/**
	 * This attribute is a Patrol object that contains a list of directions
	 * which the Jawa Sandcrawler will move towards.
	 */
	private Patrol path;
	
	/**
	 * This boolean attribute is used to identify whether the Jawa Sandcrawler
	 * can move or not since it can only move every second turn.
	 */
	private boolean canMove;
	
	/**
	 * This attribute stores the Text Interface object of the insideWorld.
	 */
	private SWGridTextInterface insideGrid;
	
	/**
	 * Constructor for the <code>JawaSandcrawler</code> class. This constructor will,
	 *  <ul>
	 * 	<li>Initialize the message renderer for the <code>JawaSandcrawler</code></li>
	 * 	<li>Initialize the world for this <code>JawaSandcrawler</code></li>
	 *  <li>Initialize the <code>Team</code> for this <code>JawaSandcrawler</code></li>
	 * 	<li>Initialize the hit points for this <code>JawaSandcrawler</code></li>
	 * 	<li>add an SwitchWorld affordance to the JawaSandcrawler so that Force actors can go to the insideWorld it</li> 
	 *  <li>Initialize a 5 by 5 world inside the <code>JawaSandcrawler</code></li>
	 * </ul>
	 * 
	 * @param team the <code>Team</code> to which the this <code>JawaSandcrawler</code> belongs to
	 * @param hitpoints the hit points of this <code>JawaSandcrawler</code> to get started with
	 * @param m <code>MessageRenderer</code> to display messages.
	 * @param world the <code>SWWorld</code> world to which this <code>JawaSandcrawler</code> belongs to
	 * @param moves is the list of directions that will be patrolled by the Jawa Sandcrawler
	 */
	public JawaSandcrawler(Team team, int hitpoints, MessageRenderer m, SWWorld world, Direction [] moves) {
		
		super(team, hitpoints, m, world);
		path = new Patrol(moves);
		canMove = true;
		
		SWWorld newWorld = new SWInsideWorld(5, world, this);
		insideWorld = newWorld;
		insideGrid = new SWGridTextInterface(insideWorld.getGrid());
		insideWorld.initializeWorld(m);
		
		SwitchWorld switchWorld = new SwitchWorld(this, m);
		this.addAffordance(switchWorld);
	}
	
	/**
	 * Return the text interface of the inside world
	 * @return insideGrid 
	 */
	public SWGridTextInterface getText() {
		return insideGrid;
	}
	
	/**
	 * Return the inside world
	 * @return insideWorld
	 */
	public SWWorld getInsideWorld() {
		return insideWorld;
	}
	
	/**
	 * This method will determine what the next action for the JawaSandcrawler is
	 * The Sandcrawler will eat a droid and put it inside its small world in its body
	 * if there is a droid on the same location as the Sandcrawler.
	 * 
	 * Otherwise, it will move according to its Patrol movements which can only
	 * be done every second turn.
	 */
	@Override
	public void act() {
		
		if(isDead()) {
			return;
		}
		
		NeighbourInformation eat;
		eat = EatNeighbour.eatlocal(this,  this.world);
		
		if (eat != null){
			
			say(getShortDescription() + " eats " + eat.entity.getShortDescription());
			scheduler.schedule(eat.affordance, this, 1);
			
			canMove = true;
			
		} else if (canMove) {
			
			Direction newdirection = path.getNext();
			Move myMove = new Move(newdirection, messageRenderer, world);
			scheduler.schedule(myMove, this, 1);
			
			canMove = false;
			
		} else if (!canMove){
			canMove = true;
		}
	}
	
}
