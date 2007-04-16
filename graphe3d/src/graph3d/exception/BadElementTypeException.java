package graph3d.exception;

import javax.swing.JOptionPane;

public class BadElementTypeException extends GException {

	public BadElementTypeException(String _type) {
		super("The type (\"" + _type + "\") is not define to describe a link or a node into a graph.\nPlease see your definitions' file of classes.");
	}
	
	@Override
	public void printStackTrace() {
		System.err.println(this.getMessage());
		super.printStackTrace();
	}

	public boolean showError() {
		Object[] options = new Object[] {"OK"};
		JOptionPane.showOptionDialog(null, this.getMessage(), "BadElementException", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[0]);
		return true;
	}
}
