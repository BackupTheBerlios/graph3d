package graph3d.exception;

import javax.swing.JOptionPane;

public class AddEventToNullCanvasException extends GException {

	public AddEventToNullCanvasException() {
		super("You must define a Canvas3D before to add EventListener.");
	}
	
	@Override
	public void printStackTrace() {
		super.printStackTrace();
	}

	public boolean showError() {
		Object[] options = new Object[] {"OK"};
		JOptionPane.showOptionDialog(null, this.getMessage(), "AddEventToNullCanvasException", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);

		return true;
	}
}
