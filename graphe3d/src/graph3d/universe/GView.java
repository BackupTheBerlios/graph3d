package graph3d.universe;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.GraphicsConfigTemplate3D;
import javax.media.j3d.PhysicalBody;
import javax.media.j3d.PhysicalEnvironment;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.View;
import javax.media.j3d.ViewPlatform;
import javax.vecmath.Vector3f;

/**
 * This class create the graph3D's view.
 */
public class GView extends BranchGroup{
	
	private final PhysicalBody PHYSICALBODY = new PhysicalBody();
	private final PhysicalEnvironment PHYSICALENVIRONMENT = new PhysicalEnvironment();


	private TransformGroup transformGroup;
	private ViewPlatform viewPlatform ;
	private View view;
	private Canvas3D canvas;
	
	/**
	 * This constructor is used to create a GView.
	 * @param _bestViewToSee of type float [].
	 */
	public GView(float [] _bestViewToSee) {

		super();
		
	    GraphicsConfigTemplate3D gconfigTemplate = new GraphicsConfigTemplate3D();
	    GraphicsConfiguration gconfig = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getBestConfiguration(gconfigTemplate);

	    // Creation du canvas 3D, de l'objet viewPlatform et de l'objet view
	    // associe au canvas et a viewPlatform
	    this.canvas = new Canvas3D(gconfig);
	    this.viewPlatform = new ViewPlatform();
	    this.view = new View();

	    // Initialisation de l'objet view
	    this.view.addCanvas3D(this.canvas);
	    this.view.attachViewPlatform(this.viewPlatform);
	    this.view.setPhysicalBody(this.PHYSICALBODY);
	    this.view.setPhysicalEnvironment(this.PHYSICALENVIRONMENT);

	    // Creation du groupe de transformation qui permet de modifier la position
	    // de la camera
	    this.transformGroup = new TransformGroup();
	    this.transformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
	    this.transformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
	    this.transformGroup.addChild(this.viewPlatform);
	    
	    // Creation de l'objet parent qui est pere de tous les nodes de la classe
	    // Vue
	    this.setCapability(BranchGroup.ALLOW_DETACH);
	    this.addChild(this.transformGroup);
	    
	    Transform3D transform = new Transform3D();
	    transform.setTranslation(new Vector3f(0f, 0f, 15f));
	    //transform.setTranslation(new Vector3f(_bestViewToSee[0], _bestViewToSee[1], _bestViewToSee[2]));
	    this.transformGroup.setTransform(transform);
	    System.out.println(this.view.getFieldOfView());
	    System.out.println(Math.PI/4);
	}
	
	/**
	 * The getter of the Canvas3D.
	 * @return a Canvas3D component.
	 */
	public Canvas3D getCanvas() {		
		return this.canvas;
	}

	/**
	 * The setter of the Canvas3D.
	 * @param a Canvas3D component.
	 */
	public void setCanvas(Canvas3D canvas) {
		this.canvas = canvas;
	}

}
