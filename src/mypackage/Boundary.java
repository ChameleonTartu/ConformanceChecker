package mypackage;
public class Boundary
{
	private static Controller controller;
	
	public static void main( String [] args )
	{
		controller = new Controller();
		controller.execute("C:\\Users\\andre_000\\Documents\\GitHub\\ConformanceChecker\\src\\test.xes", "C:\\Users\\andre_000\\Documents\\GitHub\\ConformanceChecker\\src\\test.pnml");
		System.out.println( controller.computeFitness() );
		System.out.println( controller.computeBehavioralAppropriateness());
		System.out.println( controller.computeStructuralAppropriateness());
	}
}
