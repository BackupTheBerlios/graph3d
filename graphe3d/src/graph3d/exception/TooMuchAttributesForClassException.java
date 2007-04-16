package graph3d.exception;

import javax.swing.JOptionPane;

import graph3d.elements.GLink;
import graph3d.elements.GNode;

public class TooMuchAttributesForClassException extends GException {

	public TooMuchAttributesForClassException(GLink _link, String _name) {
		super("For this link (\"" + _link.getName() + "\"), there are too much attributes (\"" + _name + "\" for example).\nThis attribute is not define into the class (\"" + _link.getClass().getName() + "\").\nThat's why this attribute is not used.");

	}

	public TooMuchAttributesForClassException(GNode _node, String _name) {
		super("For this node (\"" + _node.getName() + "\"), there are too much attributes (\"" + _name + "\" for example).\nThis attribute are not define into the class (\"" + _node.getClass().getName() + "\").\nThat's why this attribute is not used.");

	}

	@Override
	public void printStackTrace() {
		super.printStackTrace();
	}
	
	public boolean showError() {
		Object[] options = new Object[] {"OK"};
		JOptionPane.showOptionDialog(null, this.getMessage(), "TooMuchAttributesForClassException", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
		return true;
	}

}
