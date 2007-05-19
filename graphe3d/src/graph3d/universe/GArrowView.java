package graph3d.universe;

import graph3d.elements.GLink;
import graph3d.elements.GNode;

import javax.media.j3d.Appearance;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.GeometryArray;
import javax.media.j3d.LineArray;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Matrix4f;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.geometry.Cone;

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

		if (this.getLink().getFirstNode().getCoordonates() == this.getLink()
				.getSecondNode().getCoordonates()) {

			this.setLine(new LineArray(6, GeometryArray.COORDINATES
					| GeometryArray.COLOR_3));
			this.update();

		} else {
			this.setLine(new LineArray(2, GeometryArray.COORDINATES
					| GeometryArray.COLOR_3));
			this.update();
		}
	}

	public void update() {

		float[] tabnode1 = null;
		tabnode1 = this.getLink().getFirstNode().getCoordonates();
		float[] tabnode2 = null;
		tabnode2 = this.getLink().getSecondNode().getCoordonates();

		Vector3f node1 = new Vector3f(tabnode1[0], tabnode1[1], tabnode1[2]);
		Vector3f node2 = new Vector3f(tabnode2[0], tabnode2[1], tabnode2[2]);

		// color - appearance pour le cone
		Appearance appearance = new Appearance();
		ColoringAttributes color = new ColoringAttributes(this.getColor(), 0);
		appearance.setColoringAttributes(color);

		if (this.getLink().getFirstNode().getName() == this.getLink()
				.getSecondNode().getName()) {

			Point3f point1 = new Point3f(node1.x + 1f, node1.y, node1.z + 1);
			Point3f point2 = new Point3f(node1.x, node1.y - 1f, node1.z - 1);

			this.getLine().setCoordinate(0,
					this.getLink().getFirstNode().getCoordonates());
			this.getLine().setCoordinate(1, point1);
			this.getLine().setCoordinate(2, point1);
			this.getLine().setCoordinate(3, point2);
			this.getLine().setCoordinate(4, point2);
			this.getLine().setCoordinate(5,
					this.getLink().getFirstNode().getCoordonates());

			this.getLine().setColor(0, this.getColor());
			this.getLine().setColor(1, this.getColor());
			this.getLine().setColor(2, this.getColor());
			this.getLine().setColor(3, this.getColor());
			this.getLine().setColor(4, this.getColor());
			this.getLine().setColor(5, this.getColor());

		} else {

			this.getLine().setCoordinate(0,
					this.getLink().getFirstNode().getCoordonates());
			this.getLine().setCoordinate(1,
					this.getLink().getSecondNode().getCoordonates());

			this.getLine().setColor(0, this.getColor());
			this.getLine().setColor(1, this.getColor());

			this.createArrow(appearance);
		}
	}

	private void createArrow(Appearance appearance) {

		Transform3D placeArrow = new Transform3D();

		GNode firstNode = this.getLink().getFirstNode();
		GNode secondNode = this.getLink().getSecondNode();

		Vector3f director;
		if (BasicFunctions.getLength(firstNode.getCoordonates()) < BasicFunctions
				.getLength(secondNode.getCoordonates())) {
			director = new Vector3f(
					(secondNode.getCoordonnateX() - firstNode.getCoordonnateX()) / 2,
					(secondNode.getCoordonnateY() - firstNode.getCoordonnateY()) / 2,
					(secondNode.getCoordonnateZ() - firstNode.getCoordonnateZ()) / 2);
			placeArrow.setTranslation(new Vector3f(firstNode.getCoordonates()));
		} else {
			director = new Vector3f(
					(firstNode.getCoordonnateX() - secondNode.getCoordonnateX()) / 2,
					(firstNode.getCoordonnateY() - secondNode.getCoordonnateY()) / 2,
					(firstNode.getCoordonnateZ() - secondNode.getCoordonnateZ()) / 2);
			placeArrow
					.setTranslation(new Vector3f(secondNode.getCoordonates()));
		}

		Transform3D translateToBarycenter = new Transform3D();
		translateToBarycenter.setTranslation(director);

		// Compute the length and direction of the line
		float deltaX = secondNode.getCoordonnateX()
				- firstNode.getCoordonnateX();
		float deltaY = secondNode.getCoordonnateY()
				- firstNode.getCoordonnateY();
		float deltaZ = secondNode.getCoordonnateZ()
				- firstNode.getCoordonnateZ();

		float theta = -(float) Math.atan2(deltaZ, deltaX);
		float phi = (float) Math.atan2(deltaY, deltaX);
		if (deltaX < 0.0f) {
			phi = (float) Math.PI - phi;
		}

		// Compute a matrix to rotate a cone to point in the line's
		// direction, then place the cone at the line's endpoint.
		Matrix4f mat = new Matrix4f();
		Matrix4f mat2 = new Matrix4f();
		mat.setIdentity();

		mat2.setIdentity();
		mat2.rotY(theta);
		mat.mul(mat2);

		mat2.setIdentity();
		mat2.rotZ(phi);
		mat.mul(mat2);

		mat2.setIdentity();
		mat2.rotZ(-1.571f);
		mat.mul(mat2);

		Transform3D rotate = new Transform3D(mat);

		placeArrow.mul(translateToBarycenter);
		placeArrow.mul(rotate);

		TransformGroup arrow = new TransformGroup(placeArrow);
		arrow.addChild(new Cone(0.1f, 0.3f, appearance));

		this.addChild(arrow);
	}

	/**
	 * This function is used to calculate the barycenter of two nodes.
	 * 
	 * @param firstNode
	 * @param secondNode
	 * @return barycentre
	 */
	/*
	 * public Vector3f getBarycentre(GNode firstNode,GNode secondNode){
	 * 
	 * Vector3f barycentre=new Vector3f(0,0,0);
	 *  // node1 barycentre.x+=firstNode.getCoordonnateX();
	 * barycentre.y+=firstNode.getCoordonnateY();
	 * barycentre.z+=firstNode.getCoordonnateZ();
	 *  // node 2 barycentre.x+=secondNode.getCoordonnateX();
	 * barycentre.y+=secondNode.getCoordonnateY();
	 * barycentre.z+=secondNode.getCoordonnateZ();
	 * 
	 * barycentre.x/=2; barycentre.y/=2; barycentre.z/=2;
	 * 
	 * return barycentre; }
	 */

}
