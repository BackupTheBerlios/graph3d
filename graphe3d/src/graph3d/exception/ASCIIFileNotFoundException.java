package graph3d.exception;

public class ASCIIFileNotFoundException extends GException {

	private String ASCIIFileName;
	
	public ASCIIFileNotFoundException(String _ASCIIFileName) {
		super();
		this.ASCIIFileName = _ASCIIFileName;
	}

	@Override
	public void printStackTrace() {
		super.printStackTrace();
	}
	
	public void showError() {

		super.showError();
		this.getJdialog().setTitle(/* nom du fichier ASCII */"not found");

		// message = "the ASCII file "nom du fichier" is not found.\nWe use only
		// GNode and Glink to define nodes and links.
	}
}
