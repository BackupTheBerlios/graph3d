package graph3d.exception;

public class CollisionException extends GException {

	public CollisionException() {
		super();
	}

	@Override
	public void printStackTrace() {
		super.printStackTrace();
	}
	
	public void showError() {

		super.showError();
		this.getJdialog().setTitle("Collision between two nodes");

		// message = you mustn't declared two nodes "trop près l'un de l'autre"
	}
}
