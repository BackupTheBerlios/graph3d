package graph3d.exception;

import javax.swing.JOptionPane;

public class InexistantClassException extends GException {

	public InexistantClassException(String _class) {
		super("The class (\"" + _class + "\") does not exist or not accessible.");
	}
	
	@Override
	public void printStackTrace() {
		System.err.println(this.getMessage());
		super.printStackTrace();
	}

	public boolean showError() {
		Object[] options = new Object[] {"OK"};
		JOptionPane.showOptionDialog(null, this.getMessage(), "InexistantClassException", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[0]);
		return true;
	}
	
	public boolean showError(int ligne) {
		Object[] options = new Object[] {"OK"};
		JOptionPane.showOptionDialog(null, "line "+ligne+" : "+this.getMessage(), "InexistantClassException", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[0]);
		return true;
	}
}
