package starwars;

import edu.monash.fit2099.simulator.time.Scheduler;
import starwars.swinterfaces.SWGridController;
import starwars.swinterfaces.SWGridTextInterface;

/**
 * Driver class for the Star Wars package with <code>GridController</code>.  Contains nothing but a main().
 * 
 * @author ram
 */
/*
 * Change log
 * 2017-02-02	The TextInterface handles the responsibly of displaying the grid not the SWGrid or SWWorld classes (asel)
 * 2017-02-10	GridController controls the interactions with the user and will determine which UI it should use to do this. 
 * 			    Therefore there is tight coupling with the user interfaces and the driver. The application no longer has to worry about the
 * 				UI(asel)
 * 2017-02-19	Removed the show banner method. The text interface will deal with showing the banner. (asel)
 */

public class Application {
	public static void main(String args[]) {
		
		//Create a new world with 10 by 10 grid size
		SWWorld world = new SWWorld(10);
		
		//Grid controller controls the data and commands between the UI and the model
		SWGridController uiController = new SWGridController(world);
		
		//Set the grid text for the game in the beginning.
		SWGridController.setTextUi(uiController);
		SWGridTextInterface.setGrid(uiController.getGridText());
		world.setGridController(uiController);
		
		Scheduler theScheduler = new Scheduler(1, world);
		SWActor.setScheduler(theScheduler);
		
		// set up the world
		world.initializeWorld(uiController);
	
		// kick off the scheduler
		while(true) {
			uiController.render();
			theScheduler.tick();
		}
		
		
	}
	
	

}
