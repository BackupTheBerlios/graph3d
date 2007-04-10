package graph3d.exception;

public class XMLDefinitionException extends GException {

	public XMLDefinitionException() {
		super();

	}

	@Override
	public void printStackTrace() {
		System.err.println("The XML file was not validated by the XSD schema");
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
