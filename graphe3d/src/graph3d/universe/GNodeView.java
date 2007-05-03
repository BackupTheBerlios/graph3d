package graph3d.universe;

import graph3d.elements.GNode;

import javax.media.j3d.Appearance;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.Group;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.geometry.Sphere;

/**
 * This class define the view of the GNode
 * @author Erwan Daubert && Nicolas Magnin
 * 
 *
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
		this.setCapability(Group.ALLOW_CHILDREN_WRITE);
		this.setCapability(Group.ALLOW_CHILDREN_READ);
		this.setCapability(Group.ALLOW_CHILDREN_EXTEND);
		this.node = _node;
		this.transformGroup = new TransformGroup();
		this.transformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		this.transformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		this.update();
		this.createAppearence();
		this.sphere = new Sphere(this.node.getRadius(), Primitive.GENERATE_TEXTURE_COORDS, this.appearence);
		this.transformGroup.addChild(this.sphere);
		this.addChild(this.transformGroup);
	}

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

		/*TextureLoader loader;
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
*/
		ColoringAttributes bleu=new ColoringAttributes();
		bleu.setColor(0.1f,0.1f,1.0f);
		this.appearence.setColoringAttributes(bleu);
		

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
