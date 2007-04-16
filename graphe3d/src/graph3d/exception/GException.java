package graph3d.exception;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class GException extends RuntimeException {

	public static final int ERROR = 0;
	public static final int WARNING = 1;
	
	private int messageType;
	
	public GException() {
		this("Exception unknown", 1); //mettre icone
	}
	
	public GException(String _message, int _typeMessage) {
		super(_message);
		this.messageType = _typeMessage;
		
		
	}
	
	public GException(String _message) {
		this(_message, 1);
	}

	@Override
	public void printStackTrace() {
		super.printStackTrace();
	}
	
	public boolean showError() {
		if (this.messageType == 0) {
			Object[] options = new Object[] {"OK"};
			JOptionPane.showOptionDialog(null, this.getMessage(), "GException", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[0]);
		} else {
			Object[] options = new Object[] {"OK"};
			JOptionPane.showOptionDialog(null, this.getMessage(), "GException", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
		}
		return true;
	}
}
