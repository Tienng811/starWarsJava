package starwars.entities.actors.behaviors;

import edu.monash.fit2099.simulator.matter.Affordance;
import starwars.SWEntityInterface;

public class NeighbourInformation {

	public SWEntityInterface entity;
	public Affordance affordance;
	public NeighbourInformation(SWEntityInterface e, Affordance a) {
		entity = e;
		affordance = a;
	}
}
