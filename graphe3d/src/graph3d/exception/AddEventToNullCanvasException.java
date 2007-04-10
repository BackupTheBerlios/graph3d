package graph3d.exception;

public class AddEventToNullCanvasException extends GException {

	public AddEventToNullCanvasException() {
		super();
	}
	
	@Override
	public void printStackTrace() {
		super.printStackTrace();
	}

	public void showError() {

		super.showError();
		this.getJdialog().setTitle("Canvas3D is not created");

		// message = "You must define a Canvas3D before to add EventListener."
	}
}
