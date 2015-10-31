/**
 * @(#) PetriNet.java
 */

import java.util.ArrayList;
import java.util.List;

public class PetriNet
{	
	private List<Place> places;
	private List<Transition> transitions;
	
	public PetriNet( List<Place> places, List<Transition> transitions )
	{
		this.transitions = transitions;
		this.places = places;
	}
	
	
	public void replayTrace( List<Trace> traces )
	{
		for( int index = 0; index < traces.size(); ++index )
		{
			List<Event> events = traces.get(index).getEvents();
			List<Transition> enabledTransition = new ArrayList<Transition>();;
			
			for( int event = 0; event < events.size(); ++event )
			{
				String eventName = events.get(event).getEventName();
				for( int transition = 0; transition < transitions.size(); ++transition )
				{
					List<Place> placeWithoutTokens = transitions.get(transition).getPlacesWithoutTokens();
					if( placeWithoutTokens.size() == 0 )
						enabledTransition.add(transitions.get(transition));
					
					// I have doubts about this line!
					traces.get(index).updateMissing(placeWithoutTokens);
					
					String transitionName = transitions.get(transition).getTransitionName();
					if( transitionName == eventName )
					{
						if( placeWithoutTokens.size() > 0 )
						{
							List<Place> places = transitions.get(transition).getInputPlaces();
							for( int indexPlacesWithoutTokens = 0; indexPlacesWithoutTokens < placeWithoutTokens.size(); ++indexPlacesWithoutTokens )
							{
								for( int indexInputPlaces = 0; indexInputPlaces < places.size(); ++indexInputPlaces )
								{
									if( placeWithoutTokens.get(indexPlacesWithoutTokens) == places.get(indexInputPlaces) )
										placeWithoutTokens.get(indexPlacesWithoutTokens).addToken();
								}
							}
						}
						traces.get(index).updateEnabledTransitions(enabledTransition);
						transitions.get(transition).fire();
						
						List<Place> inputPlaces = transitions.get(transition).getInputPlaces();
						traces.get(index).updateConsumed(inputPlaces);
						
						List<Place> outputPlaces = transitions.get(transition).getOutputPlaces();
						traces.get(index).updateProduced(outputPlaces);
					}
				}
			}
			int remaining = 0;
			for( int place = 0; place < places.size(); ++place)
			{
				remaining += places.get(place).getAmountOfTokens();
			}
			traces.get(index).updateRemaining(remaining);
		}
	}
	
	public List<Transition> getTransitions( )
	{
		return transitions;
	}
	
	public List<Place> getPlaces( )
	{
		return places;
	}
	
}
