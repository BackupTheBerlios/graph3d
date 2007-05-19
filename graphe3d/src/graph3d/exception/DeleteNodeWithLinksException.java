package graph3d.exception;

import graph3d.elements.GNode;

import javax.swing.JOptionPane;

/**
 * This class define an exception which is generated when you try to delete a
 * GNode which is connect to one or more GLink
 * 
 * @author Erwan Daubert
 * @version 1.0
 * 
 */
public class DeleteNodeWithLinksException extends GException {

	/**
	 * The constructor of this class
	 * 
	 * @param _node
	 *            the GNode which you want delete
	 */
	public DeleteNodeWithLinksException(GNode _node) {
		super(
				"You want delete this node (\""
						+ _node.getName()
						+ "\").\nBe Careful, If you delete this node, all links which are connected will be delete also.\n\nWould you want delete this node?");
	}

	@Override
	public void printStackTrace() {
		System.err.println(this.getMessage());
		super.printStackTrace();
	}

	public boolean showError() {
		Object[] options = new Object[] { "YES", "NO" };
		int result = JOptionPane.showOptionDialog(null, this.getMessage(),
				"DeleteNodeWithLinksException", JOptionPane.DEFAULT_OPTION,
				JOptionPane.WARNING_MESSAGE, null, options, options[0]);
		if (result == 0) {
			return true;
		} else {
			return false;
		}
	}
}
