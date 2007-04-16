package graph3d.exception;

import javax.swing.JOptionPane;

import graph3d.elements.GLink;
import graph3d.elements.GNode;

public class InvalidAttributeTypeException extends GException {

	@Override
	public void printStackTrace() {
		System.err.println(this.getMessage());
		super.printStackTrace();
	}
	
	public InvalidAttributeTypeException(GNode _node, String _attributeName, String _type, String _value) {
		super("The value (\"" + _value + "\") is not define for the type (\"" + _type + "\") in the element (\"" + _node.getName() + "\").");
	}
	
	public InvalidAttributeTypeException(GLink _link, String _attributeName, String _type, String _value) {
		super("The value (\"" + _value + "\") is not define for the type (\"" + _type + "\") in the element (\"" + _link.getName() + "\").");
	}
	
	public boolean showError() {
		Object[] options = new Object[] {"OK"};
		int result = JOptionPane.showOptionDialog(null, this.getMessage(), "InvalidAttributeTypeException", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[0]);

		return true;
	}
}
