package graph3d.universe;

import graph3d.elements.GLink;

import javax.media.j3d.GeometryArray;
import javax.media.j3d.LineArray;
import javax.vecmath.Vector3f;

/**
 * This class create a GArrowView.
 */
public class GArrowView extends GLinkView {

	/**
	 * This constructor is used to create a GArrowView.
	 * 
	 * @param _link
	 *            of type GLink.
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
		this.setLine(new LineArray(8, GeometryArray.COORDINATES
				| GeometryArray.COLOR_3));
		this.update();
		this.getLine().setColor(0, this.getColor());
		this.getLine().setColor(1, this.getColor());
		this.getLine().setColor(2, this.getColor());
		this.getLine().setColor(3, this.getColor());
		this.getLine().setColor(4, this.getColor());
		this.getLine().setColor(5, this.getColor());
		this.getLine().setColor(6, this.getColor());
		this.getLine().setColor(7, this.getColor());
	}

	public void update() {
		this.getLine().setCoordinate(0,
				this.getLink().getFirstNode().getCoordonates());
		this.getLine().setCoordinate(1,
				this.getLink().getSecondNode().getCoordonates());
		if (this.getLink().getFirstNode().equals(this.getLink().getSecondNode())) {
			
			this.createDirectionForLoop();
		} else {
			this.createDirection();
		}
	}
	
	private float[] createBarycenter() {
		float[] barycenter = new float[3];
		barycenter[0] = (this.getLink().getFirstNode().getCoordonnateX() +this.getLink().getSecondNode().getCoordonnateX()) / 2;
		barycenter[1] = (this.getLink().getFirstNode().getCoordonnateY() +this.getLink().getSecondNode().getCoordonnateY()) / 2;
		barycenter[2] = (this.getLink().getFirstNode().getCoordonnateZ() +this.getLink().getSecondNode().getCoordonnateZ()) / 2;
		return barycenter;
	}
	private void createDirectionForLoop() {
		float[] point2 = this.createBarycenter();
		this.getLine().setCoordinate(2, point2);
		float[] point3 = new float[3];
		float[] vector = new float[] {this.getLink().getSecondNode().getCoordonnateX() - this.getLink().getFirstNode().getCoordonnateX(), this.getLink().getSecondNode().getCoordonnateY() - this.getLink().getFirstNode().getCoordonnateY(), this.getLink().getSecondNode().getCoordonnateZ() - this.getLink().getFirstNode().getCoordonnateZ()};
		Vector3f vectorDirector = new Vector3f(vector);
		
		point3[0] = (point2[0] + this.getLink().getFirstNode().getCoordonnateX()) / 2;
		point3[1] = (point2[1] + this.getLink().getFirstNode().getCoordonnateX()) / 2;
		point3[2] = (point2[2] + this.getLink().getFirstNode().getCoordonnateX()) / 2;
		this.getLine().setCoordinate(3, point3);
		float[] point4 = new float[3];
		this.getLine().setCoordinate(4, point4);
		
	}
	
	private void createDirection() {
		float[] point2 = this.createBarycenter();
		this.getLine().setCoordinate(2, point2);
		float[] point3 = new float[3];
		float[] vector = new float[] {this.getLink().getSecondNode().getCoordonnateX() - this.getLink().getFirstNode().getCoordonnateX(), this.getLink().getSecondNode().getCoordonnateY() - this.getLink().getFirstNode().getCoordonnateY(), this.getLink().getSecondNode().getCoordonnateZ() - this.getLink().getFirstNode().getCoordonnateZ()};
		Vector3f vectorDirector = new Vector3f(vector);
		
		point3[0] = (point2[0] + this.getLink().getFirstNode().getCoordonnateX()) / 2;
		point3[1] = (point2[1] + this.getLink().getFirstNode().getCoordonnateX()) / 2;
		point3[2] = (point2[2] + this.getLink().getFirstNode().getCoordonnateX()) / 2;
		this.getLine().setCoordinate(3, point3);
		float[] point4 = new float[3];
		this.getLine().setCoordinate(4, point4);
		
	}

}
