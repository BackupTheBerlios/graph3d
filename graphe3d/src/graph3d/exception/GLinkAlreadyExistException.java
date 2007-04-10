package graph3d.exception;

import graph3d.elements.GLink;
import graph3d.elements.GNode;

public class GLinkAlreadyExistException extends GException {

	private GNode node;
	
	private GLink link;
	
	public GLinkAlreadyExistException(GNode _node, GLink _link) {
		this.node = _node;
		this.link = _link;
	}
	@Override
	public void printStackTrace() {
		super.printStackTrace();
	}
}
