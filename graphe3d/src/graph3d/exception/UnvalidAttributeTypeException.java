package graph3d.exception;

public class UnvalidAttributeTypeException extends GException {

	private String type;

	@Override
	public void printStackTrace() {
		System.err.println("This attribute (" + type + ") was not define for the ");
		super.printStackTrace();
	}
	
	public UnvalidAttributeTypeException(String type) {
		super();
		this.type = type;
	}
}
