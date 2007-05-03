package graph3d.exception;


/**
 * This class define an exception which is generated when you define an element on a type which is not valid
 * for Exemple, a ethernetConnection is a type of GLink and you try to  define a GNode with this type
 * @author Erwan Daubert
 * @version 1.0
 *
 */
public class BadElementTypeException extends GException {

	/**
	 * The constructor of this class where you know also the type which is not valid
	 * @param _type the type which is not valid
	 */
	public BadElementTypeException(String _type) {
		super("The type (\"" + _type + "\") is not define to describe a link or a node into a graph.\nPlease see your definitions' file of classes.");
		this.setTitle("BadElementException");
		this.setMessageType(0);
	}
	
	/**
	 * The constructor of this class where you know the type which is not valid and also the line where you try to use this type
	 * @param _type the type which is not valid
	 * @param _line the line where you try to use this type
	 */
	public BadElementTypeException(String _type, int _line) {
		super("line : " + _line + "\n" + "The type (\"" + _type + "\") is not define to describe a link or a node into a graph.\nPlease see your definitions' file of classes.");
		this.setTitle("BadElementException");
		this.setMessageType(0);
	}
	
	@Override
	public void printStackTrace() {
		System.err.println(this.getMessage());
		super.printStackTrace();
	}
}
