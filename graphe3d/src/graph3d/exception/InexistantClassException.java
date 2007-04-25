package graph3d.exception;

import javax.swing.JOptionPane;

/**
 * This class define an exception which is generated when You want created an Element on a specific type but this type doesn't exist
 * for exemple, you want define an element Computer but you can define the class Computer.
 * @author Erwan Daubert
 * @version 1.0
 *
 */
public class InexistantClassException extends GException {

	/**
	 * The constructor of this class
	 * @param _class the name of the class which is not define
	 * @param ligne the number of the line where you try to specifiy the new class
	 */
	public InexistantClassException(String _class, int ligne) {
		super("line " + ligne + " : The class (\"" + _class + "\") does not exist or is not accessible.\nPlease see your classes definitions' file.");
		this.setTitle("InexistantClassException");
		this.setMessageType(0);
	}
	
	/**
	 * The constructor of this class
	 * @param _class the name of the class which is not define
	 */
	public InexistantClassException(String _class) {
		super("The class (\"" + _class + "\") does not exist or is not accessible.\nPlease see your classes definitions' file.");
		this.setTitle("InexistantClassException");
		this.setMessageType(0);
	}
	
	@Override
	public void printStackTrace() {
		System.err.println(this.getMessage());
		super.printStackTrace();
	}
}
