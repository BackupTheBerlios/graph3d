package graph3d.exception;

import javax.swing.JDialog;
import javax.swing.JPopupMenu;

import graph3d.elements.GLink;
import graph3d.elements.GNode;

public class SameNameException extends GException {

	public SameNameException(GLink link, GLink theOtherLink) {
		super();

	}

	public SameNameException(GNode node, GNode theOtherNode) {
		super();

	}

	@Override
	public void printStackTrace() {
		super.printStackTrace();
	}
	
	public void showError() {

		super.showError();
		// if () { si node
		this.getJdialog().setTitle("Same name for two nodes");
		// else { sinon
		this.getJdialog().setTitle("Same name for two links");

		// mettre en place les boutons et le texte du message
		// message = Two elements have the same name : "le nom".
	}
}
