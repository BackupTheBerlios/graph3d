package graph3d.universe;

import graph3d.elements.GLink;

import java.awt.Color;

import javax.media.j3d.Appearance;
import javax.media.j3d.LineArray;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;

public abstract class GLinkView extends TransformGroup {

	private Shape3D shape;
	private LineArray line;
	private Appearance appearence;
	private Transform3D transform3D;
	private GLink link;
	
	
	public void add() {
		this.setShape(new Shape3D());
		this.getShape().setGeometry(this.getLine());
		this.addChild(this.getShape());
	}
	
	public Appearance getAppearence() {
		return appearence;
	}
	public void setAppearence(Appearance appearence) {
		this.appearence = appearence;
	}
	public LineArray getLine() {
		return line;
	}
	public void setLine(LineArray line) {
		this.line = line;
	}
	public GLink getLink() {
		return link;
	}
	public void setLink(GLink link) {
		this.link = link;
	}
	public Transform3D getTransform3D() {
		return transform3D;
	}
	public void setTransform3D(Transform3D transform3D) {
		this.transform3D = transform3D;
	}
	public Shape3D getShape() {
		return shape;
	}
	public void setShape(Shape3D shape) {
		this.shape = shape;
	}
	
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
		}	else {
			return new Color3f(Color.white);
		}
	}
}
