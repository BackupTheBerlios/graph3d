package graph3d.exception;

import javax.swing.JOptionPane;

import graph3d.elements.GLink;
import graph3d.elements.GNode;

public class GLinkAlreadyExistException extends GException {

	
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
