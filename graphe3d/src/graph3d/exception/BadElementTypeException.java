package graph3d.exception;

public class BadElementTypeException extends GException {

	String type;

	public BadElementTypeException(String type) {
		super();
	}
	
	@Override
	public void printStackTrace() {
		super.printStackTrace();
	}

	public void showError() {

		super.showError();
		this.getJdialog().setTitle(type + " is not declared");

		// message = type + "is not declared.\nSpecified this type into the file
		// " + ASCII file.
	}
}
