package mypackage;
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
		return events;
	}
	
	
	public void updateMissing( List<Place> placeWithoutTokens )
	{
		this.missing += placeWithoutTokens.size();
	}
	
	
	public void updateEnabledTransitions( int enabledTransitionsSize )
	{
		this.enabled += enabledTransitionsSize;
	}
	
	
	public void updateConsumed( int inputPlacesSize )
	{
		this.consumed += inputPlacesSize;
	}
	
	
	public void updateProduced( int outputPlacesSize )
	{
		this.produced += outputPlacesSize;
	}
	
	
	public void updateRemaining( int remaining )
	{
		this.remaining += remaining;
	}
	
	
}
