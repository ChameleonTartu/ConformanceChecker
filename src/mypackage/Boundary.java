package mypackage;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class Boundary
{
	private static Controller controller;
	
	public static void main( String [] args )
	{
		controller = new Controller();
		JFileChooser choice = new JFileChooser();
        choice.showOpenDialog(new JFrame());
        File file1 = choice.getSelectedFile();
        choice.showOpenDialog(new JFrame());
        File file2 = choice.getSelectedFile();
		controller.execute(file1.getAbsolutePath(), file2.getAbsolutePath());
		System.out.println( controller.computeFitness() );
		System.out.println( controller.computeBehavioralAppropriateness());
		System.out.println( controller.computeStructuralAppropriateness());
	}
}
