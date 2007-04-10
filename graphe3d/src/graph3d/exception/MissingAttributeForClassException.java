package graph3d.exception;

import graph3d.elements.GLink;
import graph3d.elements.GNode;

public class MissingAttributeForClassException extends GException {

	public MissingAttributeForClassException(GLink link) {
		super();

	}

	public MissingAttributeForClassException(GNode node) {
		super();

	}

	@Override
	public void printStackTrace() {
		super.printStackTrace();
	}
	
	public void showError() {

		super.showError();
		this.getJdialog().setTitle("Missing attributes for this elements : "); // mettre
		// le
		// nom
		// de
		// l'élément

		// mettre en place les boutons et le texte du message
		// texte = This elements doesn't contain enough attributes.\nYou must
		// specified all attributes define in the class which represents this
		// element.
	}
}
