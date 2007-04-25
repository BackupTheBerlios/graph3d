package graph3d.exception;

import javax.swing.JOptionPane;

import graph3d.elements.GLink;
import graph3d.elements.GNode;

/**
 * This class define an exception which is generated when you want add to connect a GNode with a GLink but this GLink is already connect to this GNode(Except for a loop)
 * @author Erwan Daubert
 * @version 1.0
 *
 */
public class GLinkAlreadyExistException extends GException {

	/**
	 * The constructor of this class
	 * @param _node the GNode on which you want connect the _link
	 * @param _link the GLink which you want connect to the _node
	 */
	public GLinkAlreadyExistException(GNode _node, GLink _link) {
		super("For the node (\"" + _node.getName() + "\"), the link (\"" + _link.getName() + "\") already exist.\n\nWould you like replace the older by the newest ?");
	}
	@Override
	public void printStackTrace() {
		super.printStackTrace();
	}
	
	public boolean showError() {
		Object[] options = new Object[] {"Yes", "No"};
		int result = JOptionPane.showOptionDialog(null, this.getMessage(), "GException", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
		if (result == 0) {
			return true;
		} else {
			return false;
		}
		
	}
}
