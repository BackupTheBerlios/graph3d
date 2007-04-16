package graph3d.universe;

import graph3d.elements.GLink;

import java.awt.Color;

import javax.media.j3d.GeometryArray;
import javax.media.j3d.LineArray;
import javax.media.j3d.Shape3D;
import javax.vecmath.Color3f;

public class GArrowView extends GLinkView {

	public GArrowView(GLink _link) {
		this.setLink(_link);
		this.createLine();
		this.add();
	}
	
	private void createLine() {
		this.setLine(new LineArray(6, GeometryArray.COORDINATES | GeometryArray.COLOR_3));
		this.getLine().setCoordinate(0, this.getLink().getFirstNode().getCoordonates());
		this.getLine().setCoordinate(1, this.getLink().getSecondNode().getCoordonates());
		
		this.getLine().setCoordinate(2, this.getLink().getFirstNode().getCoordonates());//mettre coordonnées pour créer la flèche
		this.getLine().setCoordinate(3, this.getLink().getFirstNode().getCoordonates());
		this.getLine().setCoordinate(4, this.getLink().getFirstNode().getCoordonates());
		
		this.getLine().setColor(0, this.getColor());
		this.getLine().setColor(1, this.getColor());
		this.getLine().setColor(2, this.getColor());
		this.getLine().setColor(3, this.getColor());
		this.getLine().setColor(4, this.getColor());
	}
}
