package mypackage;
/**
 * @(#) Controller.java
 */

import java.util.List;

/**
 * @(#) Controller.java
 */
public class Controller
{
	//private Boundary boundary;
	private EntityManager entityManager;
	private Log eventLog;
	private PetriNet petriNet;
	
	public Controller ()
	{
		entityManager = new EntityManager();
	}
	
	public void execute( String filePathLog, String filePathPetriNet)
	{
		eventLog = entityManager.getLog(filePathLog);
		petriNet = entityManager.getPetriNet(filePathPetriNet);
		List<Trace> traces = eventLog.getTraces();
		petriNet.replayTrace(traces);
	}
	
	private double calculateFitness( List<Trace> traces )
	{
		double missing = 0, consumed = 0, remaining = 0, produced = 0;
		for( int i = 0; i < traces.size(); ++i)
		{
			//System.out.println( traces.get(i).getNumber() + " " + traces.get(i).getMissing() + " " + traces.get(i).getConsumed() + " " + traces.get(i).getRemaining() + " " + traces.get(i).getProduced() );
			missing += ( traces.get(i).getNumber() * traces.get(i).getMissing() );
			consumed += ( traces.get(i).getNumber() * traces.get(i).getConsumed() );
			remaining += ( traces.get(i).getNumber() * traces.get(i).getRemaining() );
			produced += ( traces.get(i).getNumber() * traces.get(i).getProduced() );
		}
		
		return (1.0 - (missing/consumed + remaining/produced)/2.0);
	}
	
	public double computeFitness()
	{
		List<Trace> traces = eventLog.getTraces();
		return ( this.calculateFitness(traces) );
	}
	
	private double calculateBehavioralAppropriateness( List<Trace> traces, int transitionLength )
	{
		List<Transition> transitions = petriNet.getTransitions();
		double sumNumberMultyiplyByDisabledTransitions = 0, number = 0, numberTransitions = transitions.size();
		for( int i = 0; i < traces.size(); ++i)
		{	
			sumNumberMultyiplyByDisabledTransitions += (traces.get(i).getNumber() * (numberTransitions - traces.get(i).getEnabled()/(double)(traces.get(i).getEvents().size()) ));
			number += traces.get(i).getNumber();
		}
		return sumNumberMultyiplyByDisabledTransitions/((numberTransitions-1) * number) ;
	}
	
	public double computeBehavioralAppropriateness()
	{
		List<Trace> traces = eventLog.getTraces();
		List<Transition> transitions = petriNet.getTransitions();
		return (this.calculateBehavioralAppropriateness(traces, transitions.size() ));
	}
	
	private double calculateStructuralAppropriateness( int transitionLength, int placesLength )
	{
		double label = (double)transitionLength + 2.0;
		double node = (double)transitionLength + (double)placesLength;
		return label/node;
	}
	
	public double computeStructuralAppropriateness()
	{
		List<Transition> transitions = petriNet.getTransitions();
		List<Place> places = petriNet.getPlaces();
		return (this.calculateStructuralAppropriateness( transitions.size(), places.size() ));
	}
	
	
}
