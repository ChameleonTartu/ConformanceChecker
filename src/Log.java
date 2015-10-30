import java.util.ArrayList;
import java.util.List;

public class Log
{
	//private String name;
	
	private List<Trace> traces;
	
	public Log(List<Trace> traces){
		this.traces = traces;
	}
	
	//public String getName(){
	//	return name;
	//}
	
	public List<Trace> getTraces( )
	{
		return traces;
	}
	
	
}
