import java.util.List;

public class Trace
{
	private int number;
	
	private int remaining;
	
	private int missing;
	
	private int produced;
	
	private int consumed;
	
	private int enabled;
	
	private List<Event> events;
	
	private Log log;
	
	public Trace(int number, List<Event> events){
		this.number = number;
		remaining = 0;
		missing = 0;
		produced = 0;
		consumed = 0;
		enabled = 0;
		this.events = events;
		
	}
	
	public int getNumber(){
		return number;
	}
	
	public int getRemaining(){
		return remaining;
	}
	
	public int getMissing(){
		return missing;
	}
	
	public int getProduced(){
		return produced;
	}
	
	public int getConsumed(){
		return consumed;
	}
	
	public int getEnabled(){
		return enabled;
	}

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
