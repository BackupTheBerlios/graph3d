package graph3d.exception;

import javax.swing.JOptionPane;

/**
 * This class is the superclass for all Exception generated by the graph3D API.
 * You can use this class to generate special Exception which is not define on
 * the subclass.
 * 
 * @author Erwan Daubert
 * @version 2
 */
public class GException extends Exception {

	/**
	 * ERROR define an Exception which must stop the execution of the
	 * application because it's a critical error for the coherence of the
	 * program.
	 */
	public static final int ERROR = 0;

	/**
	 * WARNING define an exception which is not critical and the application can
	 * be continued to work
	 */
	public static final int WARNING = 1;

	private int messageType;

	private String title;

	/**
	 * This constructor can be used to generate an unknown Exception. By default
	 * the error is a critical error.
	 * 
	 * @deprecated It's better to create an exception which a message to
	 *             describe the Exception
	 */
	public GException() {
		this("Exception Unknown", "Exception unknown", 0);
	}

	/**
	 * This constructor define a GException with a message which describe the
	 * error By defautl, the error is a critical error
	 * 
	 * @param _message
	 *            define the exception
	 */
	public GException(String _message) {
		this("Unknown Exception", _message, 0);
	}

	/**
	 * This constructor define a title and a message to the error. It's defined
	 * also the type of the message ERROR or WARNING
	 * 
	 * @param _title
	 *            the title which define the exception
	 * @param _message
	 *            the message which describe the exception
	 * @param _typeMessage
	 *            ERROR or WARNING
	 */
	public GException(String _title, String _message, int _typeMessage) {
		super(_message);
		this.messageType = _typeMessage;
		this.title = _title;

	}

	/**
	 * The getter of the type of the message
	 * 
	 * @return ERROR or WARNING
	 */
	public int getMessageType() {
		return this.messageType;
	}

	/**
	 * The setter of messageType
	 * 
	 * @param messageType
	 *            the new value of the messageType (ERROR or WARNING)
	 */
	public void setMessageType(int messageType) {
		this.messageType = messageType;
	}

	/**
	 * The getter of the title of this Exception
	 * 
	 * @return title
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * The setter of the title
	 * 
	 * @param title
	 *            the new value of the title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public void printStackTrace() {
		super.printStackTrace();
	}

	/**
	 * This function is used to create a pop-up which contains informations
	 * about the exception
	 * 
	 * @return true or false if necessary
	 */
	public boolean showError() {
		if (this.messageType == 0) {
			Object[] options = new Object[] { "OK" };
			JOptionPane.showOptionDialog(null, this.getMessage(), this.title,
					JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE,
					null, options, options[0]);
		} else {
			Object[] options = new Object[] { "OK" };
			JOptionPane.showOptionDialog(null, this.getMessage(), this.title,
					JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
					null, options, options[0]);
		}
		return true;
	}
}
