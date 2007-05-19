package graph3d.exception;

import graph3d.elements.GLink;
import graph3d.elements.GNode;

import javax.swing.JOptionPane;

/**
 * This class define an exception which is generated when an element have not
 * enough attribute into the XML file. For example : if a Computer have an
 * attribute IP-address, and into the XML file, this attribute is not define,
 * there will be a MissingAttributeForClassException generated
 * 
 * @author Erwan Daubert
 * @version 1.0
 * 
 */
public class MissingAttributeForClassException extends GException {

	/**
	 * it the constructor of this classe define for a GLink
	 * 
	 * @param _link
	 *            the link which have attribute which is not define
	 */
	public MissingAttributeForClassException(GLink _link) {
		super(
				"For this link(\""
						+ _link.getName()
						+ "\"), you must define more attributes.\nPlease add attributes for this element.\nTo know attribute of a class see the definition of the class.");
		this.setTitle("MissingAttributeForClassException");
		this.setMessageType(0);

	}

	/**
	 * it the constructor of this classe define for a GNode
	 * 
	 * @param _node
	 *            the link which have attribute which is not define
	 */
	public MissingAttributeForClassException(GNode _node) {
		super(
				"For this node(\""
						+ _node.getName()
						+ "\"), you must define more attributes.\nPlease add attributes for this element\nTo know attribute of a class see the definition of the class.");
		this.setTitle("MissingAttributeForClassException");
		this.setMessageType(0);

	}

	@Override
	public void printStackTrace() {
		super.printStackTrace();
	}

	public boolean showError() {
		Object[] options = new Object[] { "OK" };
		JOptionPane.showOptionDialog(null, this.getMessage(),
				"MissingAttributeForClassException",
				JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null,
				options, options[0]);

		return true;
	}
}
