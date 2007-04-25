package graph3d.exception;

import javax.swing.JOptionPane;

/**
 * This class define an Exception which is generate when the XML file which describe a graph doesn't respect the XML schema
 * @author Erwan Daubert
 * @version 1
 */
public class XMLDefinitionException extends GException {

	private String details;
	
	/**
	 * The constructor of this class
	 * @param _details all details about this exception (error which appears into the XML file)
	 */
	public XMLDefinitionException(String _details) {
		super("The XML file doesn't respect the XSD schema.\nPlease correct errors or load an other XML File.");
		this.details = _details;
	}

	@Override
	public void printStackTrace() {
		System.err.println(this.getMessage());
		System.err.println(this.details);
	}
	
	public boolean showError() {
		Object[] options = new Object[] {"OK", "DETAILS"};
		int result = JOptionPane.showOptionDialog(null, this.getMessage(), "GException", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[0]);
		if (result == 1) {
			options = new Object[] {"OK"};
			JOptionPane.showOptionDialog(null, this.details, "Details", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
		}
		return true;
	}
}
