package graph3d.exception;


/**
 * This class define an exception which is generated when two elements of GNode(and his subclasses) or GLink(and his subclasses) are the same name
 * @author Erwan Daubert
 * @version 1.0
 *
 */
public class SameNameException extends GException {
	
	/**
	 * The constructor of this class
	 * @param _name the name which are the same for two elements.
	 */
	public SameNameException(String _name) {
		super("Two elements are the same name : " + _name);
		this.setTitle("SameNameException");
		this.setMessageType(0);
	}

	@Override
	public void printStackTrace() {
		super.printStackTrace();
	}
}
