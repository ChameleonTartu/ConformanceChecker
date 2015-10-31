package mypackage;
/**
 * @(#) Transition.java
 */

import java.util.ArrayList;
import java.util.List;

/**
 * @(#) Transition.java
 */
public class Transition
{
	private String name;
	private List<Place> inputPlaces;
	private List<Place> outputPlaces;
	
	public Transition( List<Place> inputPlaces, List<Place> outputPlaces, String name )
	{
		this.inputPlaces = inputPlaces;
		this.outputPlaces = outputPlaces;
		this.name = name;
	}
	
	public Transition(String name){
		this.name = name;
		this.inputPlaces = new ArrayList<Place>();
		this.outputPlaces = new ArrayList<Place>();
		
	}
	
	public String getName() {
		return name;
	}
	
	public void AddOutPlace(Place place) {
		outputPlaces.add(place);
	}
	
	public void AddInPlace(Place place) {
		inputPlaces.add(place);
	}
	
	public List<Place> getPlacesWithoutTokens()
	{
		List<Place> placesWithoutTokens = new ArrayList<Place>();
		for( int i = 0; i < inputPlaces.size(); ++i)
		{
			if( inputPlaces.get(i).getAmountOfTokens() == 0 )
				placesWithoutTokens.add(inputPlaces.get(i));
		}
		return placesWithoutTokens;
	}
	
	public List<Place> getInputPlaces( )
	{
		return inputPlaces;
	}
	
	public List<Place> getOutputPlaces( )
	{
		return outputPlaces;
	}

	public String getTransitionName( )
	{
		return this.name;
	}
	
	
	public void fire( )
	{
		for( int i = 0; i < inputPlaces.size(); ++i )
			inputPlaces.get(i).consumeToken();
		for( int i = 0; i < outputPlaces.size(); ++i )
			outputPlaces.get(i).produceToken();
	}

}