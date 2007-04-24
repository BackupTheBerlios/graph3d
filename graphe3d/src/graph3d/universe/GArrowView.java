package graph3d.universe;

import graph3d.elements.GLink;

import javax.media.j3d.GeometryArray;
import javax.media.j3d.LineArray;

/**
 * This class create a GArrowView.
 */
public class GArrowView extends GLinkView {

	/**
	 * This constructor is used to create a GArrowView.
	 * @param _link of type GLink.
	 */
	public GArrowView(GLink _link) {
		this.setLink(_link);
		this.createLine();
		this.add();
	}
	
	/**
	 * This function is used to create a line (with an arrow) of type LineArray.
	 */
	private void createLine() {
		this.setLine(new LineArray(6, GeometryArray.COORDINATES | GeometryArray.COLOR_3));
		this.update();
		this.getLine().setColor(0, this.getColor());
		this.getLine().setColor(1, this.getColor());
		this.getLine().setColor(2, this.getColor());
		this.getLine().setColor(3, this.getColor());
		this.getLine().setColor(4, this.getColor());
	}
	
	public void update() {
		this.getLine().setCoordinate(0, this.getLink().getFirstNode().getCoordonates());
		this.getLine().setCoordinate(1, this.getLink().getSecondNode().getCoordonates());
		
		//this.getCoordonatesToArrow();
		this.getLine().setCoordinate(2, this.getLink().getFirstNode().getCoordonates());//mettre coordonnées pour créer la flèche
		this.getLine().setCoordinate(3, this.getLink().getFirstNode().getCoordonates());
		this.getLine().setCoordinate(4, this.getLink().getFirstNode().getCoordonates());
	}

}
