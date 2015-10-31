package mypackage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.deckfour.xes.extension.std.XConceptExtension;
import org.deckfour.xes.extension.std.XLifecycleExtension;
import org.deckfour.xes.extension.std.XTimeExtension;
import org.deckfour.xes.model.XAttributeMap;
import org.deckfour.xes.model.XEvent;
import org.deckfour.xes.model.XLog;
import org.deckfour.xes.model.XTrace;
import org.processmining.models.connections.GraphLayoutConnection;
import org.processmining.models.graphbased.directed.petrinet.PetrinetEdge;
import org.processmining.models.graphbased.directed.petrinet.PetrinetGraph;
import org.processmining.models.graphbased.directed.petrinet.PetrinetNode;
import org.processmining.models.graphbased.directed.petrinet.elements.Place;
import org.processmining.models.graphbased.directed.petrinet.elements.Transition;
import org.processmining.models.graphbased.directed.petrinet.impl.PetrinetFactory;
import org.processmining.models.semantics.petrinet.Marking;
import org.processmining.plugins.pnml.Pnml;

import com.google.common.collect.Iterators;
import com.sun.javafx.collections.MappingChange.Map;

public class EntityManager
{
	
	public Log getLog( String filePathLog )
	{
		try {
			XLog log = XLogReader.openLog(filePathLog);
			HashMap<String, Integer> traces =  new HashMap<String, Integer>();
			for(XTrace trace:log){
				String traceString = "";
				for(XEvent event : trace){
					String activityName = XConceptExtension.instance().extractName(event);
					traceString += activityName;
				}
				
				Integer value = traces.get(traceString);
				if (value == null) {
					traces.put(traceString, 1);
				} else {
					traces.put(traceString, traces.get(traceString) + 1);
				}
			}
		
			List<Trace> tracesList = new ArrayList<Trace>();
			for(String key :traces.keySet()){
				Integer value = traces.get(key);
				List<Event> events = new ArrayList<Event>();
				for (int i=0; i < key.length(); i++){
					Event event = new Event(String.valueOf(key.charAt(i)));
					events.add(event);
				}
				
				tracesList.add(new Trace(value, events));
			}
			
			return new Log(tracesList);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public PetriNet getPetriNet( String filePathPetriNet )
	{
		PnmlImportUtils ut = new PnmlImportUtils();
		File f = new File (filePathPetriNet);
		try {
			InputStream input = new FileInputStream(f);
			Pnml pnml = ut.importPnmlFromStream(input, f.getName(), f.length());
			PetrinetGraph net = PetrinetFactory.newInhibitorNet(pnml.getLabel() + " (imported from " + f.getName() + ")");
			Marking marking = new Marking();
			pnml.convertToNet(net,marking ,new GraphLayoutConnection(net));
			
			Collection<Place> places = net.getPlaces();
			Collection<Transition> transitions = net.getTransitions();
			
			
			List<mypackage.Transition> originalTransitions = new ArrayList<mypackage.Transition>();
			List<mypackage.Place> originalPlaces = new ArrayList<mypackage.Place>();
			
			for( int place = 0; place < places.size(); ++place )
			{
				Place aPlace = Iterators.get( places.iterator(), place);
				Collection<PetrinetEdge<? extends PetrinetNode, ? extends PetrinetNode>> edgesInputPlaces = net.getInEdges(aPlace);
				if( edgesInputPlaces.size() == 0 )
				{
					originalPlaces.add(new mypackage.Place(aPlace.getLabel(),1));
				}
				else
				{
					originalPlaces.add(new mypackage.Place(aPlace.getLabel(),0));
				}
			}
			
			for( int transition = 0; transition < transitions.size(); ++transition )
			{
				List<mypackage.Place> inputPlaces = new ArrayList<mypackage.Place>();
				List<mypackage.Place> outputPlaces = new ArrayList<mypackage.Place>();
				Transition aTransition = Iterators.get( transitions.iterator(), transition );
				
				for( int place = 0; place < places.size(); ++place )
				{
					Place aPlace = Iterators.get( places.iterator(), place);
					
					Collection<PetrinetEdge<? extends PetrinetNode, ? extends PetrinetNode>> edgesOutputPlaces = net.getOutEdges(aPlace);
					Collection<PetrinetEdge<? extends PetrinetNode, ? extends PetrinetNode>> edgesInputPlaces = net.getInEdges(aPlace);
					Collection<PetrinetEdge<? extends PetrinetNode, ? extends PetrinetNode>> edgesOutputTransition = net.getOutEdges(aTransition);
					Collection<PetrinetEdge<? extends PetrinetNode, ? extends PetrinetNode>> edgesInputTransition = net.getInEdges(aTransition);
					
					
					for( int aTransitionIndex = 0; aTransitionIndex < edgesOutputTransition.size(); ++aTransitionIndex )
					{
						for( int aPlaceIndex = 0; aPlaceIndex < edgesInputPlaces.size(); ++aPlaceIndex )
						{
							if( Iterators.get( edgesOutputTransition.iterator(), aTransitionIndex) == Iterators.get( edgesInputPlaces.iterator(), aPlaceIndex) )
							{
								for( int existPlace = 0; existPlace < originalPlaces.size(); ++existPlace )
								{
									mypackage.Place originalPlace = Iterators.get( originalPlaces.iterator(), existPlace);
									
									if( aPlace.getLabel().equals( originalPlace.getName() ) )
										outputPlaces.add(originalPlace);
								}
							}
						}
					}
					
					for( int aTransitionIndex = 0; aTransitionIndex < edgesInputTransition.size(); ++aTransitionIndex )
					{
						for( int aPlaceIndex = 0; aPlaceIndex < edgesOutputPlaces.size(); ++aPlaceIndex )
						{
							if( Iterators.get( edgesInputTransition.iterator(), aTransitionIndex) == Iterators.get( edgesOutputPlaces.iterator(), aPlaceIndex) )
							{
								for( int existPlace = 0; existPlace < originalPlaces.size(); ++existPlace )
								{
									mypackage.Place originalPlace = Iterators.get( originalPlaces.iterator(), existPlace);
									
									if( aPlace.getLabel().equals( originalPlace.getName() ) )
										inputPlaces.add(originalPlace);
								}
							}
						}
					}
				}
				originalTransitions.add( new mypackage.Transition( inputPlaces, outputPlaces, aTransition.getLabel() ));
				
			}
			
			for( int i = 0; i < originalTransitions.size(); ++i )
			{
				System.out.println(originalTransitions.get(i).getName());
			}
			
			//System.out.println(originalPlaces.size());
			//System.out.println(originalTransitions.size());
			
			PetriNet petriNet = new PetriNet(originalPlaces, originalTransitions);
			//petriNet.findInitPlace();
			//System.out.println(" aSDxa " + petriNet.getTransitions().size());
			return petriNet;

			} catch (Exception e) {
			e.printStackTrace();
		}

		
		return null;
	}
	
	
}
