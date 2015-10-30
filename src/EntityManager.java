import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.deckfour.xes.extension.std.XConceptExtension;
import org.deckfour.xes.extension.std.XLifecycleExtension;
import org.deckfour.xes.extension.std.XTimeExtension;
import org.deckfour.xes.model.XAttributeMap;
import org.deckfour.xes.model.XEvent;
import org.deckfour.xes.model.XLog;
import org.deckfour.xes.model.XTrace;

import com.sun.javafx.collections.MappingChange.Map;

public class EntityManager
{
	private Controller controller;
	
	public static void main(String[] args){
		EntityManager manager = new EntityManager();
		manager.getLog("C:\\Users\\andre_000\\Documents\\GitHub\\ConformanceChecker\\src\\test.xes");
	}

	public Log getLog( String filePathLog )
	{
		try {
			XLog log = XLogReader.openLog(filePathLog);
			
			HashMap<String, Integer> traces =  new HashMap<String, Integer>();
			
			
			for(XTrace trace:log){
				String traceName = XConceptExtension.instance().extractName(trace);
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
		
			List<String> tracesString = new ArrayList<String>();
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
		
	
	public PetriNet getPetriNet( String filePathPetriNet )
	{
		return null;
	}
	
	
}
