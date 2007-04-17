package graph3d.universe;

import graph3d.elements.GLink;

import javax.media.j3d.GeometryArray;
import javax.media.j3d.LineArray;

/**
 * This class create a GBridgeView.
 */
public class GBridgeView extends GLinkView {
	
	/**
	 * This constructor is used to create a GBridgeView.
	 * @param _link of type GLink.
	 */
	public GBridgeView(GLink _link) {
		this.setLink(_link);
		this.createLine();
		this.add();
		
	}
	
	/**
	 * This function is used to create a line of type LineArray.
	 */
	private void createLine() {
		this.setLine(new LineArray(2, GeometryArray.COORDINATES | GeometryArray.COLOR_3));
		
		this.getLine().setCoordinate(0, this.getLink().getFirstNode().getCoordonates());
		this.getLine().setCoordinate(1, this.getLink().getSecondNode().getCoordonates());
		
		this.getLine().setColor(0, this.getColor());
		this.getLine().setColor(1, this.getColor());
	}

}
