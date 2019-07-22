package starwars;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWWorld;
import starwars.entities.Door;

public class SWInsideWorld extends SWWorld{
	/**
	 * This attribute stores the reference to the outside world.
	 */
	private SWWorld outsideWorld;
	
	/**
	 * This attribute is the owner of the InsideWorld
	 */
	private SWActor owner;
	
	/**
	 * The constructor of the InsideWorld which is largely using the
	 * Super class' constructor (<code>SWWorld</code>).
	 * 
	 * @param gridSize the gridSize of the inside world
	 * @param world the outside world
	 * @param a owner of the inside world
	 */
	public SWInsideWorld(int gridSize, SWWorld world, SWActor a) {
		super(gridSize);
		owner = a;
		outsideWorld = world;
	}
	
	/**
	 * Overriding the super class initializeWorld method.
	 * This initializeWorld method will set the short and long descriptions of
	 * all the locations in the insideWorld.
	 * 
	 * Moreover, this will also initialize a Door object in the middle of the
	 * map.
	 * 
	 * @param iface messageRenderer
	 */
	@Override
	public void initializeWorld(MessageRenderer iface) {
		SWLocation loc;
		
		// Set default location string
		for (int row=0; row < height(); row++) {
			for (int col=0; col < width(); col++) {
				loc = this.getGrid().getLocationByCoordinates(col, row);
				loc.setLongDescription("SWInsideWorld (" + col + ", " + row + ")");
				loc.setShortDescription("SWInsideWorld (" + col + ", " + row + ")");
				loc.setSymbol('.');				
			}
		}
		
		Door exitDoor = new Door(iface, outsideWorld);
		loc = this.getGrid().getLocationByCoordinates(2, 2);
		getEntityManager().setLocation(exitDoor, loc);
	}
	
	/**
	 * Return the owner of the inside world.
	 * 
	 * @return SWActor the owner of the inside World
	 */
	public SWActor getOwner(){
		return owner;
	}
}
