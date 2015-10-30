/**
 * @(#) Controller.java
 */

import java.util.List;

/**
 * @(#) Controller.java
 */
public class Controller
{
	private Boundary boundary;
	
	private EntityManager entityManager;
	
	public List<Trace> getTraces( )
	{
		return null;
	}
	
	public int computeStructuralAppropriateness( int transitionLength, int placesLength )
	{
		return 0;
	}
	
	public List<Place> getPlaces( )
	{
		return null;
	}
	
	public int computeFitness( Trace traces )
	{
		return 0;
	}
	
	public void replayTrace( String trace )
	{
		
	}
	
	public List<Transition> getTransitions( )
	{
		return null;
	}
	
	public PetriNet getPetriNet( String filePathPetriNet )
	{
		return null;
	}
	
	public Log getLog( String filePathLog )
	{
		return null;
	}
	
	public int computeFitness( List<Trace> inputTraces )
	{
		return 0;
	}
	
	public int computeBehavioralAppropriateness( List<Trace> inputTraces, int inputTransitionLength )
	{
		return 0;
	}
	
	public int computeBehavioralAppropriateness( Trace traces, int transitionLength )
	{
		return 0;
	}
	
	public void replayTrace( String trace )
	{
		
	}
	
	
}
