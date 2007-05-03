package graph3d.universe;

import graph3d.elements.GLink;

import java.awt.Color;

import javax.media.j3d.Appearance;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Group;
import javax.media.j3d.LineArray;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;

/**
 * This abstract class create a TransformGroup which will represent a GArrowView
 * or a GBridgView.
 */
public abstract class GLinkView extends BranchGroup {

	private Shape3D shape;

	private LineArray line;

	private Appearance appearence;

	private Transform3D transform3D;

	private TransformGroup transformGroup;

	private GLink link;

	/**
	 * This function is used to add a shape3D (objet which will reprensent a
	 * GLink) to a transformGroup.
	 */
	public void add() {
		this.setCapability(BranchGroup.ALLOW_DETACH);
		this.setCapability(Group.ALLOW_CHILDREN_READ);
		this.setCapability(Group.ALLOW_CHILDREN_WRITE);
		this.setCapability(Group.ALLOW_CHILDREN_EXTEND);
		this.transformGroup = new TransformGroup();
		this.transformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		this.transformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		this.setShape(new Shape3D());
		this.getShape().setGeometry(this.getLine());
		this.transformGroup.addChild(this.getShape());
		this.addChild(this.transformGroup);
	}

	/**
	 * The getter of GLink's appearance.
	 * 
	 * @return appearence of type Appearance.
	 */
	public Appearance getAppearence() {
		return appearence;
	}

	/**
	 * The setter of GLink's appearance.
	 * 
	 * @param appearence
	 *            of type Appearance.
	 */
	public void setAppearence(Appearance appearence) {
		this.appearence = appearence;
	}

	/**
	 * The getter of the GLink's LineArray.
	 * 
	 * @return line of the type LineArray.
	 */
	public LineArray getLine() {
		return line;
	}

	/**
	 * The setter of the GLink's LineArray.
	 * 
	 * @param line
	 *            of type LineArray.
	 */
	public void setLine(LineArray line) {
		this.line = line;
	}

	/**
	 * The getter of GLink.
	 * 
	 * @return link of type GLink.
	 */
	public GLink getLink() {
		return link;
	}

	/**
	 * The setter of GLink.
	 * 
	 * @param link
	 *            of type GLink.
	 */
	public void setLink(GLink link) {
		this.link = link;
	}

	/**
	 * The getter of the GLink's Transform3D, this object can us to move the
	 * GLink (zoom,rotate...).
	 * 
	 * @return transform3D of type Transform3D.
	 */
	public Transform3D getTransform3D() {
		return transform3D;
	}

	/**
	 * The setter of the GLink's Transform3D, this object can us to move the
	 * GLink (zoom,rotate...).
	 * 
	 * @param transform3D
	 *            of type Transform3D.
	 */
	public void setTransform3D(Transform3D transform3D) {
		this.transform3D = transform3D;
	}

	/**
	 * The getter of shape3D (objet which will reprensent a GLink).
	 * 
	 * @return shape of type Shape3D.
	 */
	public Shape3D getShape() {
		return shape;
	}

	/**
	 * The setter of shape3D (objet which will reprensent a GLink).
	 * 
	 * @param shape
	 *            of type Shape3D.
	 */
	public void setShape(Shape3D shape) {
		this.shape = shape;
	}

	/**
	 * The getter of the GLink's color.
	 * 
	 * @return _color og type Color3f.
	 */
	public Color3f getColor() {
		String _color = this.getLink().getColor();
		if (_color.equals("black")) {
			return new Color3f(Color.black);
		} else if (_color.equals("red")) {
			return new Color3f(Color.red);
		} else if (_color.equals("blue")) {
			return new Color3f(Color.blue);
		} else if (_color.equals("cyan")) {
			return new Color3f(Color.cyan);
		} else if (_color.equals("gray")) {
			return new Color3f(Color.gray);
		} else if (_color.equals("green")) {
			return new Color3f(Color.green);
		} else if (_color.equals("magenta")) {
			return new Color3f(Color.magenta);
		} else if (_color.equals("orange")) {
			return new Color3f(Color.orange);
		} else if (_color.equals("pink")) {
			return new Color3f(Color.pink);
		} else if (_color.equals("yellow")) {
			return new Color3f(Color.yellow);
		} else {
			return new Color3f(Color.white);
		}
	}

	/*
	 * public void addSelectionBehavior(GAttributesList _attributesList,
	 * GConnectionsList _connectionsList) {
	 * 
	 * //SelectionBehavior selectionBehavior = new
	 * SelectionBehavior(_attributesList, _connectionsList, this.transformGroup,
	 * this.link); this.addChild(selectionBehavior); }
	 */
}
