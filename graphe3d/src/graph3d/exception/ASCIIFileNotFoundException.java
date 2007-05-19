package graph3d.exception;

import javax.swing.JOptionPane;

/**
 * This class define an exception when you try to read the ASCII file which
 * define all types existing to create GNode and GLink.
 * 
 * @author Erwan Daubert
 * @version 1.0
 * 
 */
public class ASCIIFileNotFoundException extends GException {

	/**
	 * The constructor of this class
	 * 
	 * @param _ASCIIFileName
	 *            the path of the file which is not find
	 */
	public ASCIIFileNotFoundException(String _ASCIIFileName) {
		super(
				"the ASCII file (\""
						+ _ASCIIFileName
						+ "\") is not found.\nWe use only GNode and Glink to define nodes and links.");
	}

	@Override
	public void printStackTrace() {
		super.printStackTrace();
	}

	public boolean showError() {
		Object[] options = new Object[] { "OK" };
		JOptionPane.showOptionDialog(null, this.getMessage(),
				"ASCIIFileNotFoundException", JOptionPane.DEFAULT_OPTION,
				JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
		return true;
	}
}
