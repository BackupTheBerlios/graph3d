package graph3d.universe;

import graph3d.elements.GNode;
import graph3d.lists.GAttributesList;
import graph3d.lists.GConnectionsList;
import graph3d.universe.behaviors.PickSelectionBehavior;
import graph3d.universe.behaviors.SelectionBehavior;

import javax.media.j3d.Appearance;
import javax.media.j3d.Bounds;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.ImageComponent2D;
import javax.media.j3d.Texture;
import javax.media.j3d.Texture2D;
import javax.media.j3d.TextureAttributes;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.image.ImageException;
import com.sun.j3d.utils.image.TextureLoader;

/**
 * This class create a GNodeView.
 */
public class GNodeView extends BranchGroup {

	private Sphere sphere;

	private Appearance appearence;

	private Transform3D transform3D;

	private TransformGroup transformGroup;

	private GNode node;

	/**
	 * This constructor is used to create a GNodeView.
	 * 
	 * @param _node
	 *            of type GNode.
	 */
	public GNodeView(GNode _node) {
		this.setCapability(BranchGroup.ALLOW_DETACH);
		this.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
		this.setCapability(BranchGroup.ALLOW_CHILDREN_READ);
		this.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
		this.node = _node;
		this.transformGroup = new TransformGroup();
		this.transformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		this.transformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		this.update();
		this.createAppearence();
		this.sphere = new Sphere(this.node.getRadius(),
				Primitive.GENERATE_TEXTURE_COORDS, this.appearence);
		this.transformGroup.addChild(this.sphere);
		this.addChild(this.transformGroup);
	}

	/*
	 * public void addSelectionBehavior(GAttributesList _attributesList,
	 * GConnectionsList _connectionsList) { PickSelectionBehavior
	 * selectionBehavior = new PickSelectionBehavior(this, _canvas, _bounds,
	 * _attributesList) //SelectionBehavior selectionBehavior = new
	 * SelectionBehavior(_attributesList, _connectionsList, this,
	 * this.transformGroup); this.addChild(selectionBehavior); }
	 */

	public void update() {
		this.transform3D = new Transform3D();
		this.transform3D
				.setTranslation(new Vector3f(this.node.getCoordonates()));
		this.transformGroup.setTransform(this.transform3D);
	}

	/**
	 * This function is used to create an appearence.
	 */
	private void createAppearence() {
		this.appearence = new Appearance();

		TextureLoader loader;
		try {
			loader = new TextureLoader("/textures/sphere.jpg", null);
		} catch (ImageException e) {
			loader = new TextureLoader("textures/sphere.jpg", null);
		}
		ImageComponent2D image = loader.getImage();
		Texture2D texture = new Texture2D(Texture.BASE_LEVEL, Texture.RGBA,
				image.getWidth(), image.getHeight());
		texture.setImage(0, image);
		texture.setEnable(true);
		texture.setMagFilter(Texture.BASE_LEVEL_LINEAR);
		texture.setMinFilter(Texture.BASE_LEVEL_LINEAR);

		this.appearence.setTexture(texture);
		this.appearence.setTextureAttributes(new TextureAttributes());

	}

	public GNode getNode() {
		return node;
	}

	public void setNode(GNode node) {
		this.node = node;
	}

	public TransformGroup getTransformGroup() {
		return transformGroup;
	}

	public void setTransformGroup(TransformGroup transformGroup) {
		this.transformGroup = transformGroup;
	}

}
