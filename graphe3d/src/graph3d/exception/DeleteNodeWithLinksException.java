package graph3d.exception;

public class DeleteNodeWithLinksException extends GException {

	public DeleteNodeWithLinksException() {
		super();
	}

	@Override
	public void printStackTrace() {
		super.printStackTrace();
	}
	
	public void showError() {

		super.showError();
		this.getJdialog().setTitle("node linked with an other");

		// message = "Would You deleted all links of this node?"
	}
}
