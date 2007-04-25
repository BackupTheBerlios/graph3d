package graph3d.exception;

import javax.swing.JOptionPane;

/**
 * This class define an exception which is generated when you want create two or more GNode with the same coordonates.
 * @author Erwan Daubert
 * @version 1.0
 *
 */
public class CollisionException extends GException {

	/**
	 * The constructor of this class
	 *
	 */
	public CollisionException() {
		super("Two nodes have coordonates which are too near.\nOne of the node is moved.");
	}

	@Override
	public void printStackTrace() {
		super.printStackTrace();
	}
	
	public boolean showError() {
		Object[] options = new Object[] {"OK"};
		JOptionPane.showOptionDialog(null, this.getMessage(), "CollisionException", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
		return true;
	}
}
