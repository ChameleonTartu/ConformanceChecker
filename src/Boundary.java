public class Boundary
{
	private static Controller controller;
	
	public static void Main( String [] args )
	{
		controller = new Controller();
		controller.execute(args[0], args[1]);
		System.out.println( controller.computeFitness() );
		System.out.println( controller.computeBehavioralAppropriateness());
		System.out.println( controller.computeStructuralAppropriateness());
	}
}
