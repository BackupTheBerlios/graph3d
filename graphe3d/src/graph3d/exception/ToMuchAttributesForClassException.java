package graph3d.exception;

import graph3d.elements.GLink;
import graph3d.elements.GNode;

public class ToMuchAttributesForClassException extends GException {

	private String name;

	public ToMuchAttributesForClassException(GLink link, String name) {
		super();
		this.name = name;

	}

	public ToMuchAttributesForClassException(GNode node, String name) {
		super();
		this.name = name;

	}

	@Override
	public void printStackTrace() {
		super.printStackTrace();
	}
	
	public void showError() {

		super.showError();
		// if () { si node
		this.getJdialog().setTitle("Unvalid parameter to the node");
		// else { sinon
		this.getJdialog().setTitle("Unvalid parameter to the link");

		// mettre en place les boutons et le texte du message
		// texte = This elements contains too many attributes.\nYou must
		// specified only attributes define in the class which represents this
		// element.
	}

}
