package graph3d.universe;

import graph3d.universe.behaviors.GButtonBehavior;
import graph3d.universe.behaviors.GKeyBehavior;
import graph3d.universe.behaviors.GMouseBehavior;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.util.Enumeration;

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
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.behaviors.mouse.MouseWheelZoom;

/**
 * This class create the graph3D's view.
 */
public class GView extends BranchGroup {

	private final PhysicalBody PHYSICALBODY = new PhysicalBody();

	private final PhysicalEnvironment PHYSICALENVIRONMENT = new PhysicalEnvironment();

	private TransformGroup transformGroup;

	private ViewPlatform viewPlatform;

	private View view;

	private Canvas3D canvas;

	private Vector3f bestPointToSee;

	private double angleX;

	private double angleY;

	private GKeyBehavior keyBehavior;
	private GMouseBehavior mouseBehavior;
	private GButtonBehavior buttonBehavior;

	/**
	 * This constructor is used to create a GView.
	 */
	public GView() {

		super();

		GraphicsConfigTemplate3D gconfigTemplate = new GraphicsConfigTemplate3D();
		GraphicsConfiguration gconfig = GraphicsEnvironment
				.getLocalGraphicsEnvironment().getDefaultScreenDevice()
				.getBestConfiguration(gconfigTemplate);

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

		// Creation du groupe de transformation qui permet de modifier la
		// position
		// de la camera
		this.transformGroup = new TransformGroup();
		this.transformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		this.transformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		this.transformGroup.addChild(this.viewPlatform);

		this.addChild(this.transformGroup);
	}

	public void putOnBestPointToSee() {
		Transform3D transform = new Transform3D();
		 transform.setTranslation(this.bestPointToSee);
		 this.angleX=0;
		 this.angleY=0;
		 this.transformGroup.setTransform(transform);
	}

	void putOnBestPointToSee(float[] _bestPointToSee) {
		this.bestPointToSee = new Vector3f(_bestPointToSee);
		this.putOnBestPointToSee();
	}

	float getFieldOfView() {
		return (float) this.view.getFieldOfView();
	}

	/**
	 * The getter of the Canvas3D.
	 * 
	 * @return a Canvas3D component.
	 */
	public Canvas3D getCanvas() {
		return this.canvas;
	}

	/**
	 * The setter of the Canvas3D.
	 * 
	 * @param a
	 *            Canvas3D component.
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
	 * 
	 * @param distance
	 *            type of float
	 */
	/*
	 * public void zoom(float distance){
	 * 
	 * Transform3D zoom = new Transform3D();
	 * 
	 * zoom.setTranslation(new Vector3f(camera.x, camera.y ,
	 * (camera.z+(distance)) )); this.camera.z += distance;
	 * this.transformGroup.setTransform(zoom); }
	 */

	/**
	 * This function is used to add the mouse's implements to the scene. for
	 * rotate add MouseRotate. for zoom add MouseZoom.
	 */
	public void addMouseListener() {

		/*this.mouseBehavior = new GMouseBehavior(this.transformGroup, this.camera);
		this.mouseBehavior.setSchedulingBounds(new BoundingSphere(new Point3d(0, 0, 0), 10));
		this.addChild(this.mouseBehavior);*/
		
		// Creation comportement zoom a la souris avec la molette
		MouseWheelZoom zoomBehavior = new MouseWheelZoom(this.transformGroup);
		zoomBehavior.setFactor(1.2);
		zoomBehavior.setSchedulingBounds(new BoundingSphere(new Point3d(0, 0, 0), 10));
		this.addChild(zoomBehavior);
		
	}

	/**
	 * This function is used to add the keyboard's implements to the view. add
	 * KeyNavigatorBehavior.
	 */
	public void addKeyListener() {
			this.keyBehavior = new GKeyBehavior(this);
			this.keyBehavior.setSchedulingBounds(new BoundingSphere(new Point3d(0,0,0), 1000));
			this.addChild(this.keyBehavior);
	}
	
	public void addButtonListener(){
		this.buttonBehavior=new GButtonBehavior(this);
		this.buttonBehavior.setSchedulingBounds(new BoundingSphere(new Point3d(0,0,0), 1000));
		this.addChild(this.buttonBehavior);
	}

	public float getBackClipDistance() {
		return (float) this.view.getBackClipDistance();
	}

	public void setBackClipDistance(float backClipDistance) {
		this.view.setBackClipDistance(backClipDistance);
	}
	
	public void zoomMore(){
		this.transformGroup.setTransform(this.buttonBehavior.zoom(0.95f));
	}
	
	public void zoomLess(){
		this.transformGroup.setTransform(this.buttonBehavior.zoom(1.05f));
	}
	
	public void rotateTop(){
		this.transformGroup.setTransform(this.buttonBehavior.rotateY(0.1f));
		//this.transformGroup.setTransform(this.buttonBehavior.rotateY(-0.1f));
	}
	
	public void rotateBottom(){
		this.transformGroup.setTransform(this.buttonBehavior.rotateY(-0.1f));
		//this.transformGroup.setTransform(this.buttonBehavior.rotateY(0.1f));
	}
	
	public void rotateLeft(){
		this.transformGroup.setTransform(this.buttonBehavior.rotateX(0.1f));
		//this.transformGroup.setTransform(this.mouseBehavior.rotateX(-0.1f));
	}
	
	public void rotateRight(){
		this.transformGroup.setTransform(this.buttonBehavior.rotateX(-0.1f));
		//this.transformGroup.setTransform(this.mouseBehavior.rotateX(0.1f));
	}
	
	
	public Vector3f getPositionToTheView() {
		Enumeration enume = this.getAllChildren();
		while (enume.hasMoreElements()) {
			Object child = enume.nextElement();
			if (child instanceof TransformGroup) {//récupération de la position de la caméra
				Transform3D t = new Transform3D();
				((TransformGroup)child).getTransform(t);
				Vector3f v = new Vector3f();
				t.get(v);
				return v;
			}
		}
		return new Vector3f();
	}

	public double getAngleX() {
		return angleX;
	}

	public void setAngleX(double angleX) {
		this.angleX = angleX;
	}

	public double getAngleY() {
		return angleY;
	}

	public void setAngleY(double angleY) {
		this.angleY = angleY;
	}

	public TransformGroup getTransformGroup() {
		return transformGroup;
	}

	public void setTransformGroup(TransformGroup transformGroup) {
		this.transformGroup = transformGroup;
	}
}
