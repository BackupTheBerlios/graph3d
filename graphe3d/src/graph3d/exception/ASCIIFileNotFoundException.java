package graph3d.exception;

import javax.swing.JOptionPane;

public class ASCIIFileNotFoundException extends GException {
	
	public ASCIIFileNotFoundException(String _ASCIIFileName) {
		super("the ASCII file (\"" + _ASCIIFileName + "\") is not found.\nWe use only GNode and Glink to define nodes and links.");
	}

	@Override
	public void printStackTrace() {
		super.printStackTrace();
	}
	
	public boolean showError() {
		Object[] options = new Object[] {"OK"};
		JOptionPane.showOptionDialog(null, this.getMessage(), "ASCIIFileNotFoundException", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
		return true;
	}
}
