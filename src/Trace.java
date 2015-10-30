import java.util.List;

public class Trace
{
	private int number;
	
	private int reamining;
	
	private int missing;
	
	private int produced;
	
	private int consumed;
	
	private int enabled;
	
	private Event event;
	
	private Log log;
	
	

	public List<Event> getEvents( )
	{
		return null;
	}
	
	
	public void updateMissing( List<Place> placeWithoutTokens )
	{
		
	}
	
	
	public void updateEnabledTransitions( List<Transition> enabledTransitions )
	{
		
	}
	
	
	public void updateConsumed( List<Place> inputPlaces )
	{
		
	}
	
	
	public void updateProduced( List<Place> outputPlaces )
	{
		
	}
	
	
	public void updateRemaining( int remaining )
	{
		
	}
	
	
}
