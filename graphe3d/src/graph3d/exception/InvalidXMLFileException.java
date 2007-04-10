package graph3d.exception;

import graph3d.elements.GLink;
import graph3d.elements.GNode;

public class InvalidXMLFileException extends GException {

	public InvalidXMLFileException() {
		super();

	}

	@Override
	public void printStackTrace() {
		super.printStackTrace();
	}
	
	public void showError() {

		super.showError();
		this.getJdialog().setTitle(
				"The XML file doesn't respect the XSD schema");

		// mettre en place les boutons et le texte du message
		// message = the XML file doesn't respect the XSD schema.\n Please
		// correct errors or load an other XML File.
	}
}
