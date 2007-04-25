package graph3d.exception;

import javax.swing.JOptionPane;

/**
 * This class define an exception when you try to add Event to a Canvas3D whitout define this canvas.
 * @author Erwan Daubert
 * @version 1.0
 *
 */
public class AddEventToNullCanvasException extends GException {

	/**
	 * The constructor of this class
	 *
	 */
	public AddEventToNullCanvasException() {
		super("You must define a Canvas3D before to add EventListener.");
		this.setTitle("AddEventToNullCanvasException");
		this.setMessageType(1);
	}
	
	@Override
	public void printStackTrace() {
		super.printStackTrace();
	}
}
