package mypackage;
/**
 * @(#) PetriNet.java
 */

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PetriNet
{	
	public List<Place> places;
	private List<Transition> transitions;
	
	public PetriNet( List<Place> places, List<Transition> transitions )
	{
		this.transitions = transitions;
		this.places = places;
	}
	
	public PetriNet( List<Place> places)
	{
		this.transitions = new ArrayList<Transition>();
		this.places = places;
	}
	
	public void replayTrace( List<Trace> traces )
	{
		
		for( int index = 0; index < traces.size(); ++index )
		{
			List<Event> events = traces.get(index).getEvents();
			List<Transition> enabledTransition = new ArrayList<Transition>();
			Set<Transition> enabledSet = new HashSet<Transition>();
			
			for( int place = 0; place < places.size(); ++place)
			{
				if( places.get(place).isInitialPlace() )
				{
					places.get(place).setAmountOfTokens(1);
					traces.get(index).updateProduced(1);
					break;
				}
			}
			
			for( int event = 0; event < events.size(); ++event )
			{
				String eventName = events.get(event).getEventName();

				for( int transition = 0; transition < transitions.size(); ++transition )
				{
					List<Place> placeWithoutTokens = transitions.get(transition).getPlacesWithoutTokens();
					
					
					String transitionName = transitions.get(transition).getTransitionName();
					if( placeWithoutTokens.size() == 0 )
					{
						enabledSet.add(transitions.get(transition));
					}
					
					if( transitionName.equals(eventName) )
					{
						traces.get(index).updateMissing(placeWithoutTokens);
						if( placeWithoutTokens.size() > 0 )
						{
							List<Place> inputPlaces = transitions.get(transition).getInputPlaces();
							for( int indexPlacesWithoutTokens = 0; indexPlacesWithoutTokens < placeWithoutTokens.size(); ++indexPlacesWithoutTokens )
							{
								for( int indexInputPlaces = 0; indexInputPlaces < inputPlaces.size(); ++indexInputPlaces )
								{
									if( placeWithoutTokens.get(indexPlacesWithoutTokens) == inputPlaces.get(indexInputPlaces) )
										placeWithoutTokens.get(indexPlacesWithoutTokens).addToken();
								}
							}
						}
						
						transitions.get(transition).fire();
						
						List<Place> inputPlaces = transitions.get(transition).getInputPlaces();
						traces.get(index).updateConsumed(inputPlaces.size());
						
						List<Place> outputPlaces = transitions.get(transition).getOutputPlaces();
						traces.get(index).updateProduced(outputPlaces.size());
					}
				}
			}
			
			enabledTransition = new ArrayList<Transition>(enabledSet);
			traces.get(index).updateEnabledTransitions(enabledTransition.size());
			
			int remaining = 0;
			for( int place = 0; place < places.size(); ++place)
			{	
				remaining += places.get(place).getAmountOfTokens();
				if(places.get(place).isFinalPlace()) {
					traces.get(index).updateConsumed(1);
					remaining -= 1;
				}

				places.get(place).setAmountOfTokens(0);
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
