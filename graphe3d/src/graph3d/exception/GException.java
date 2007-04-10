package graph3d.exception;

import javax.swing.JDialog;

public class GException extends RuntimeException {

	private JDialog jdialog;

	private String message;

	public GException() {
		jdialog = new JDialog();
	}

	public GException(String message) {
		this.message = message;
	}
	
	@Override
	public void printStackTrace() {
		super.printStackTrace();
	}

	public void showError() {
		jdialog.setSize(100, 100);
		// affichage d'un pop-up qui nous fourni un message d'erreur.
		// dans le cas générale afficher "printStackTrace"????
	}

	public JDialog getJdialog() {
		return jdialog;
	}

	public void setJdialog(JDialog jdialog) {
		this.jdialog = jdialog;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
