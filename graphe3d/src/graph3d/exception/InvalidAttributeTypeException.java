package graph3d.exception;

import graph3d.elements.GLink;
import graph3d.elements.GNode;

import javax.swing.JOptionPane;

/**
 * This class is used to generated an exception when an attribute to a Gnode or a GLink is not valid
 * @author Erwan Daubert
 * @version 1
 * @see GNode
 * @see GLink
 *
 */
public class InvalidAttributeTypeException extends GException {
	
	/**
	 * This constructor is used to describe an exception generate with a GNode
	 * @param _node the GNode which generate this exception
	 * @param _attributeName the name of the attribute which is not valid
	 * @param _type the type of the attribute
	 * @param _value the value of the attribute
	 */
	public InvalidAttributeTypeException(GNode _node, String _attributeName, String _type, String _value) {
		super("The value (\"" + _value + "\") is not define for the type (\"" + _type + "\") in the element (\"" + _node.getName() + "\").");
		this.setTitle("InvalidAttributeTypeException");
		this.setMessageType(0);
	}
	
	/**
	 * This constructor is used to describe an exception generate with a GLink
	 * @param _link the GLink which generate this exception
	 * @param _attributeName the name of the attribute which is not valid
	 * @param _type the type of the attribute
	 * @param _value the value of the attribute
	 */
	public InvalidAttributeTypeException(GLink _link, String _attributeName, String _type, String _value) {
		super("The value (\"" + _value + "\") is not define for the type (\"" + _type + "\") in the element (\"" + _link.getName() + "\").");
		this.setTitle("InvalidAttributeTypeException");
		this.setMessageType(0);
	}
	
	@Override
	public void printStackTrace() {
		System.err.println(this.getMessage());
		super.printStackTrace();
	}
}
