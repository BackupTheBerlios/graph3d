package graph3d.exception;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

import graph3d.elements.GLink;
import graph3d.elements.GNode;

public class SameNameException extends GException {
	
	public SameNameException(String _name) {
		super("Two elements are the same name : " + _name);
	}

	@Override
	public void printStackTrace() {
		super.printStackTrace();
	}
	
	public boolean showError() {

		Object[] options = new Object[] {"OK"};
		int result = JOptionPane.showOptionDialog(null, this.getMessage(), "SameNameException", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[0]);

		return true;
	}
}
