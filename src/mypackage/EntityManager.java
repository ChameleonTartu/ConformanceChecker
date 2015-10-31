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

import com.sun.javafx.collections.MappingChange.Map;

public class EntityManager
{
	private Controller controller;
	
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
				List<Event> events = new ArrayList();
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
	/*
	public static void main(String[] args) {
		EntityManager em = new EntityManager();
		em.getPetriNet("C:\\Users\\andre_000\\Documents\\GitHub\\ConformanceChecker\\src\\test.pnml");
	}
	*/
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
			
			//init places
			Collection<Place> places = net.getPlaces();
			List<mypackage.Place> placesList = new ArrayList<mypackage.Place>(); 
			Iterator<Place> all = net.getPlaces().iterator();
			while (all.hasNext())
			{
				Place p = all.next();
				placesList.add(new mypackage.Place(p.getLabel()));
			}
	
			//init transitions
			Collection<Transition> transitions = net.getTransitions();
			Iterator<Transition> transtion = net.getTransitions().iterator();
			
			//init PetriNet only with places
			PetriNet petriNet = new PetriNet(placesList);
			
			while (transtion.hasNext())
			{
				Transition t = transtion.next();
				petriNet.AddTransition(new mypackage.Transition(t.getLabel()));
				
				//to get outgoing edges from a transition
				Iterator<PetrinetEdge<? extends PetrinetNode, ? extends PetrinetNode>> outedges = net.getOutEdges(t).iterator();
				while (outedges.hasNext())
				{
					PetrinetEdge<? extends PetrinetNode, ? extends PetrinetNode> outedge = outedges.next();
					petriNet.AddArcFromTransition(t.getLabel(), outedge.getTarget().getLabel());
				}
				//to get ingoing edges to a transition
				Iterator<PetrinetEdge<? extends PetrinetNode, ? extends PetrinetNode>> inedges = net.getInEdges(t).iterator();
				while (inedges.hasNext())
				{
					PetrinetEdge<? extends PetrinetNode, ? extends PetrinetNode> inedge = inedges.next();
					petriNet.AddArcToTransition(inedge.getSource().getLabel(), t.getLabel());
				}
				
			}

			} catch (Exception e) {
			e.printStackTrace();
		}

		
		return null;
	}
	
	
}
