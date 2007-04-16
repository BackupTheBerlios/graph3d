package graph3d.exception;

import javax.swing.JOptionPane;

import graph3d.elements.GLink;
import graph3d.elements.GNode;

public class MissingAttributeForClassException extends GException {

	public MissingAttributeForClassException(GLink link) {
		super("For this link(\"" + link.getName() + "\"), you must define more attributes.\nPlease add attributes for this element.\nTo know attribute of a class see the definition of the class.");

	}

	public MissingAttributeForClassException(GNode node) {
		super("For this node(\"" + node.getName() + "\"), you must define more attributes.\nPlease add attributes for this element\nTo know attribute of a class see the definition of the class.");

	}

	@Override
	public void printStackTrace() {
		super.printStackTrace();
	}
	
	public boolean showError() {
		Object[] options = new Object[] {"OK"};
		JOptionPane.showOptionDialog(null, this.getMessage(), "MissingAttributeForClassException", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[0]);

		return true;
	}
}
