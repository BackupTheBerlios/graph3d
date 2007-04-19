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
import javax.vecmath.AxisAngle4d;
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
	
	private Vector3f bestPointToSee;
	
	/**
	 * This constructor is used to create a GView.
	 * @param _bestViewToSee of type float [].
	 */
	public GView() {

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
	    
	    //définition de la profondeur du champ de vision
	    this.view.setBackClipDistance(20);

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
	}
	
	public void putOnBestPointToSee() {
		 Transform3D transform = new Transform3D();
		 transform.setTranslation(this.bestPointToSee);
		 //transform.setTranslation(new Vector3f(0f, 0f, 40f));
		 this.transformGroup.setTransform(transform);
	}
	
	void putOnBestPointToSee(float[] _bestPointToSee) {
		this.bestPointToSee = new Vector3f(_bestPointToSee);
		this.putOnBestPointToSee();
	}
	
	float getFieldOfView() {
		return (float)this.view.getFieldOfView();
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

	public Vector3f getBestPointToSee() {
		return bestPointToSee;
	}

	public void setBestPointToSee(Vector3f bestPointToSee) {
		this.bestPointToSee = bestPointToSee;
	}
	
	/**
	 * The getter of the camera's position.
	 * @return a Vector3f component
	 */
	public Vector3f getVector3fCamera(){
		Transform3D transCamera=null;
		this.transformGroup.getTransform(transCamera);
		
		Vector3f vectorCamera=null;
		
		transCamera.get(vectorCamera);
		
		return vectorCamera;
	
	}	
			
	/**
	 * This fonction is used to move the camera's view to do a zoom
	 * @param distance type of float
	 */
	public void zoom(float distance){ // pb : rafraichissement de la vue ?
		
		Transform3D zoom = new Transform3D();
		Vector3f coordCamera=this.getVector3fCamera();
		
		zoom.setTranslation(new Vector3f(coordCamera.x, coordCamera.y , (coordCamera.z+(distance)) ));
		
		 this.transformGroup.setTransform(zoom);	
	}
	
	/**
	 * This fonction is used to move the camera's view to do a zoom
	 * @param distance typr of float
	 */
	public void rotate(float angle){
		
		Transform3D rotate = new Transform3D();
		Vector3f coordCamera=this.getVector3fCamera();
		
		rotate.setRotation(new AxisAngle4d(coordCamera.x,coordCamera.y,coordCamera.z,angle));
		
		this.transformGroup.setTransform(rotate);	

	}

}
