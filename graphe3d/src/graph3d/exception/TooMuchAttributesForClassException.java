package graph3d.exception;

import javax.swing.JOptionPane;

import graph3d.elements.GLink;
import graph3d.elements.GNode;

/**
 * This class define an Exception which is generated when there are too much attribute for an element into the XML file.
 * For exemple, a GNode doesn't have attribute. If you add into the XML an attribute to a GNode, there will a TooMuchAttributesForClassException generated
 * This exception is just a warning
 * @author Erwan Daubert
 * @version 1.0
 *
 */
public class TooMuchAttributesForClassException extends GException {

	/**
	 * This constructor is used to an exception for a GLink
	 * @param _link the GLink which generate this exception
	 * @param _name the name of the attribute which is not used
	 */
	public TooMuchAttributesForClassException(GLink _link, String _name) {
		super("For this link (\"" + _link.getName() + "\"), there are too much attributes (\"" + _name + "\" for example).\nThis attribute is not define into the class (\"" + _link.getClass().getName() + "\").\nThat's why this attribute is not used.");
		this.setTitle("TooMuchAttributesForClassException");
		this.setMessageType(1);

	}

	/**
	 * This constructor is used to an exception for a GNode
	 * @param _node the GLink which generate this exception
	 * @param _name the name of the attribute which is not used
	 */
	public TooMuchAttributesForClassException(GNode _node, String _name) {
		super("For this node (\"" + _node.getName() + "\"), there are too much attributes (\"" + _name + "\" for example).\nThis attribute are not define into the class (\"" + _node.getClass().getName() + "\").\nThat's why this attribute is not used.");
		this.setTitle("TooMuchAttributesForClassException");
		this.setMessageType(1);

	}

	@Override
	public void printStackTrace() {
		super.printStackTrace();
	}

}
