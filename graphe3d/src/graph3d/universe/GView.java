package graph3d.universe;

import graph3d.universe.behaviors.GKeyBehavior;

import java.awt.AWTEvent;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyEvent;
import java.util.Enumeration;

import javax.media.j3d.Behavior;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.GraphicsConfigTemplate3D;
import javax.media.j3d.PhysicalBody;
import javax.media.j3d.PhysicalEnvironment;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.View;
import javax.media.j3d.ViewPlatform;
import javax.media.j3d.WakeupOnAWTEvent;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.behaviors.keyboard.KeyNavigatorBehavior;
import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.behaviors.mouse.MouseTranslate;
import com.sun.j3d.utils.behaviors.mouse.MouseWheelZoom;

/**
 * This class create the graph3D's view.
 */
public class GView extends BranchGroup {
	
	private final PhysicalBody PHYSICALBODY = new PhysicalBody();
	private final PhysicalEnvironment PHYSICALENVIRONMENT = new PhysicalEnvironment();


	private TransformGroup transformGroup;
	private ViewPlatform viewPlatform ;
	private View view;
	private Canvas3D canvas;
	
	private Vector3f bestPointToSee;
	private Vector3f camera;
	private double angleX;
	private double angleY;
	
	private GKeyBehavior keyBehavior;
	
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

	    // Creation du groupe de transformation qui permet de modifier la position
	    // de la camera
	    this.transformGroup = new TransformGroup();
	    this.transformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
	    this.transformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
	    this.transformGroup.addChild(this.viewPlatform);
	    
	    // Creation de l'objet parent qui est pere de tous les nodes de la classe
	    // Vue
	    //this.setCapability(BranchGroup.ALLOW_DETACH); sans doute pas nécessaire	    
	    this.addChild(this.transformGroup);
	}
	
	public void putOnBestPointToSee() {
		 Transform3D transform = new Transform3D();
		 transform.setTranslation(this.bestPointToSee);
		 this.camera=new Vector3f(this.bestPointToSee);
		 this.angleX=0;
		 this.angleY=0;
		 this.transformGroup.setTransform(transform);
	}
	
	void putOnBestPointToSee(float[] _bestPointToSee) {
		this.bestPointToSee = new Vector3f(_bestPointToSee);
		if (this.keyBehavior != null) {
			this.keyBehavior.setCamera(new Vector3f(_bestPointToSee));
		}
this.camera=new Vector3f(this.bestPointToSee);
		if (this.keyBehavior != null) {
			this.keyBehavior.setCamera(new Vector3f(_bestPointToSee));
		}
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
	 * This fonction is used to move the camera's view to do a zoom
	 * @param distance type of float
	 */
	/*public void zoom(float distance){
		
		Transform3D zoom = new Transform3D();
		
		zoom.setTranslation(new Vector3f(camera.x, camera.y , (camera.z+(distance)) ));
		this.camera.z += distance;
		this.transformGroup.setTransform(zoom);	
	}*/
	
	/**
	 * This function is used to add the mouse's implements to the scene.
	 * for rotate add MouseRotate.
	 * for zoom add MouseZoom.
	 */
	public void addMouseListener(){
	
	    
	
	    // Creation comportement zoom a la souris avec la molette
	    MouseWheelZoom zoom = new MouseWheelZoom(this.transformGroup);
	    zoom.setFactor(1.2);
	    zoom.setSchedulingBounds(new BoundingSphere(new Point3d(0,0,0), 10));
	    this.addChild(zoom);
	}
	
	/**
	 * This function is used to add the keyboard's implements to the view.
	 * add KeyNavigatorBehavior.
	 */
	public void addKeyListener(){
		this.keyBehavior = new GKeyBehavior(this.transformGroup, (Vector3f)this.bestPointToSee.clone());
		this.keyBehavior.setSchedulingBounds(new BoundingSphere(new Point3d(0,0,0), 10));
		this.addChild(this.keyBehavior);
	}

	public float getBackClipDistance() {
		return (float)this.view.getBackClipDistance();
	}

	public void setBackClipDistance(float backClipDistance) {
		this.view.setBackClipDistance(backClipDistance);
	}
}
