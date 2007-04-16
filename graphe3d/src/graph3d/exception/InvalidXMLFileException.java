package graph3d.exception;

import javax.swing.JOptionPane;

import graph3d.elements.GLink;
import graph3d.elements.GNode;

public class InvalidXMLFileException extends GException {

	private String details;
	
	public InvalidXMLFileException(String _details) {
		super("The file is not a valid XML file.");
		this.details = _details;

	}

	@Override
	public void printStackTrace() {
		super.printStackTrace();
	}
	
	public boolean showError() {

		Object[] options = new Object[] {"OK", "DETAILS"};
		int result = JOptionPane.showOptionDialog(null, this.getMessage(), "InvalidXMLFileException", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[0]);
		if (result == 1) {
			options = new Object[] {"OK"};
			JOptionPane.showOptionDialog(null, this.details, "Details", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
		}
		return true;
	}
}
