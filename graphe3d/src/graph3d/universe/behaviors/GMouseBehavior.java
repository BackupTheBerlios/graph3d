package graph3d.universe.behaviors;


import java.awt.AWTEvent;
import java.awt.event.MouseEvent;
import java.util.Enumeration;

import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.WakeupCriterion;
import javax.media.j3d.WakeupOnAWTEvent;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.behaviors.mouse.MouseBehavior;

/**
 * This class define the behavior of the interaction with the mouse and the view.
 * THIS CLASS is NOT USEFUL!!!!!!! 
 * @author Erwan Daubert
 * @version 1.0
 *
 */
/*
 * it's necessary to rewrite this class because the rotation don't work like we want
 * and it will be better if we write this class like GOneBehavior and this subclasses with a GView in parameter.
 */
public class GMouseBehavior extends MouseBehavior implements GBehavior {

private WakeupOnAWTEvent mouseEvent=new WakeupOnAWTEvent(MouseEvent.MOUSE_DRAGGED );
private Vector3f camera;
private TransformGroup transformGroup;
private double angleX,angleY;
private float xFactor;
private float yFactor;
	
/**
 * The constructor of this class
 * @param TG
 * @param _camera
 */
	public GMouseBehavior(TransformGroup TG, Vector3f _camera) {
		super(TG);
		this.xFactor = 0.0002f;
		this.yFactor = 0.0002f;
		this.setTransformGroup(TG);
		this.setCamera(_camera);
	}
	
	public void initialize() {
		this.wakeupOn(this.mouseEvent);
	}
		
	 public void processStimulus (Enumeration criteria) {
			WakeupCriterion wakeup;
			AWTEvent[] events;
		 	MouseEvent evt;
		 	//don't work like we want
		 	wakeup = (WakeupCriterion) criteria.nextElement();
		 	events = ((WakeupOnAWTEvent)wakeup).getAWTEvent();
		 	evt = (MouseEvent) events[0];
		 	float id;
			processMouseEvent(evt);
			id = evt.getID();
			if ((id == MouseEvent.MOUSE_DRAGGED)) {
				Transform3D transformX = new Transform3D();
				Transform3D transformY = new Transform3D();
				
				while (evt.isConsumed());
				this.x = evt.getX();
				this.y = evt.getY();
				int dx = this.x - this.x_last;
				int dy = this.y - this.y_last;
					if (dx != 0) {
						float factor =  dy * this.yFactor;
						if (this.x < this.x_last) {
							transformX = this.rotateX(-factor);
						} else {
							transformX = this.rotateX(factor);
						}
					}
					if (dy != 0) {
						float factor = dx * this.xFactor;
						if (this.y < this.y_last) {
							transformX = this.rotateY(-factor);
						} else {
							transformX = this.rotateY(factor);
						}
					}
					
					transformX.mul(transformY);
					this.getTransformGroup().setTransform(transformX);
			}
			wakeupOn(this.mouseEvent);
	 }

	/**
	 * This fonction is used to move the camera's view to do a zoom
	 * @param distance type of float
	 */
	public Transform3D zoom(float distance){
		
		Transform3D zoom = new Transform3D();
		Transform3D angleXRot = new Transform3D();
		Transform3D angleYRot = new Transform3D();
		
		angleXRot.rotY(this.angleX);
		angleYRot.rotX(this.angleY);
		
		zoom.setTranslation(new Vector3f(this.camera.x*distance, this.camera.y*distance , this.camera.z*distance ));
		
		this.camera.x=this.camera.x*distance;
		this.camera.y=this.camera.y*distance;
		this.camera.z=this.camera.z*distance;
		
		zoom.mul(angleXRot);
		zoom.mul(angleYRot);
		
		return zoom;	
	}
	
	
	
	/**
	 * 
	 * @param factor
	 */
	public Transform3D rotateX(float factor){
		
		Transform3D translate=new Transform3D();
		Transform3D angleXRot = new Transform3D();
		Transform3D angleYRot = new Transform3D();

		
		if(this.camera.x+(Math.abs(factor)) >= 0 && this.camera.z-(Math.abs(factor)) >= 0){
			if(factor<0){
				this.angleX = Math.atan(this.camera.x/this.camera.z);
			}else{
				this.angleX = -(Math.toRadians(270)+Math.atan(this.camera.z/this.camera.x));
			}
			
			translate.setTranslation(new Vector3f(this.camera.x+factor, this.camera.y , this.camera.z-factor ));
			this.camera.x += factor;
			this.camera.z -= factor;

			angleXRot.rotY(this.angleX);
			angleYRot.rotX(this.angleY);
		}
		if(this.camera.x-(Math.abs(factor)) >= 0 && this.camera.z-(Math.abs(factor)) <= 0){
			if(factor<0){
				this.angleX = Math.toRadians(90)+Math.atan(Math.abs(this.camera.z)/this.camera.x);
			}else{
				this.angleX = -(Math.toRadians(180)+Math.atan(this.camera.x/Math.abs(this.camera.z)));
			}
			
			translate.setTranslation(new Vector3f(this.camera.x-factor, this.camera.y , this.camera.z-factor ));
			this.camera.x -= factor;
			this.camera.z -= factor;

			angleXRot.rotY(this.angleX);
			angleYRot.rotX(this.angleY);
		}
		if(this.camera.x-(Math.abs(factor)) <= 0 && this.camera.z+(Math.abs(factor)) <= 0){
			if(factor<0){
				this.angleX = Math.toRadians(180)+Math.atan(Math.abs(this.camera.x)/Math.abs(this.camera.z));
			}else{
				this.angleX = -(Math.toRadians(90)+Math.atan(Math.abs(this.camera.z)/Math.abs(this.camera.x)));
			}
			
			translate.setTranslation(new Vector3f(this.camera.x-factor, this.camera.y , this.camera.z+factor ));
			this.camera.x -= factor;
			this.camera.z += factor;

			angleXRot.rotY(angleX);
			angleYRot.rotX(angleY);
			
		}
		if(this.camera.x+(Math.abs(factor)) <= 0 && this.camera.z+(Math.abs(factor)) >= 0){
			if(factor<0){
				this.angleX = Math.toRadians(270)+Math.atan(this.camera.z/Math.abs(this.camera.x));
			}else{
				this.angleX = -(Math.atan(Math.abs(this.camera.x)/this.camera.z));
			}
			
			translate.setTranslation(new Vector3f(this.camera.x+factor, this.camera.y , this.camera.z+factor ));
			this.camera.x += factor;
			this.camera.z += factor;

			angleXRot.rotY(this.angleX);
			angleYRot.rotX(this.angleY);
			
		}

		translate.mul(angleXRot);
		translate.mul(angleYRot);		

		return translate;	
	}
	
	/**
	 * 
	 * @param factor
	 */
	public Transform3D rotateY(float factor){
		
		Transform3D translate=new Transform3D();
		Transform3D angleYRot = new Transform3D();
		Transform3D angleXRot = new Transform3D();
		
		if(this.camera.y+(Math.abs(factor)) >= 0 && this.camera.z-(Math.abs(factor)) >= 0){
			if(factor<0){
				this.angleY = -(Math.atan(this.camera.y/this.camera.z));
			}else{
				this.angleY = -(-(Math.toRadians(270)+Math.atan(this.camera.z/this.camera.y)));
			}
			
			translate.setTranslation(new Vector3f(camera.x, camera.y+factor , camera.z-factor ));
			this.camera.y += factor;
			this.camera.z -= factor;
			
			angleXRot.rotY(angleX);
			angleYRot.rotX(angleY);
		
		}
			
		if(camera.y-(Math.abs(factor)) >= 0 && camera.z-(Math.abs(factor)) <= 0){
			if(factor<0){
				angleY = -(Math.toRadians(90)+Math.atan(Math.abs(camera.z)/camera.y));
			}else{
				angleY = -(-(Math.toRadians(180)+Math.atan(camera.y/Math.abs(camera.z))));
			}
				
			translate.setTranslation(new Vector3f(camera.x, camera.y-factor , camera.z-factor ));
			this.camera.y -= factor;
			this.camera.z -= factor;
				
			angleXRot.rotY(angleX);
			angleYRot.rotX(angleY);
		
		}
			
			if(camera.y-(Math.abs(factor)) <= 0 && camera.z+(Math.abs(factor)) <= 0){
				if(factor<0){
					angleY = -(Math.toRadians(180)+Math.atan(Math.abs(camera.y)/Math.abs(camera.z)));
				}else{
					angleY = -(-(Math.toRadians(90)+Math.atan(Math.abs(camera.z)/Math.abs(camera.y))));
				}
					
				translate.setTranslation(new Vector3f(camera.x, camera.y-factor , camera.z+factor ));
				this.camera.y -= factor;
				this.camera.z += factor;
				
				angleXRot.rotY(angleX);
				angleYRot.rotX(angleY);
					
			}
					
				if(camera.y+(Math.abs(factor)) <= 0 && camera.z+(Math.abs(factor)) >= 0){
					if(factor<0){
						angleY = -(Math.toRadians(270)+Math.atan(camera.z/Math.abs(camera.y)));
					}else{
						angleY = -(-(Math.atan(Math.abs(camera.y)/camera.z)));
					}
					
					translate.setTranslation(new Vector3f(camera.x, camera.y+factor , camera.z+factor ));
					this.camera.y += factor;
					this.camera.z += factor;
					
					angleXRot.rotY(angleX);
					angleYRot.rotX(angleY);
						
				}

		translate.mul(angleXRot);
		translate.mul(angleYRot);
		
		return translate;
	}
	
	public void setAngles(double _angleX, double _angleY){
		this.angleX = _angleX;
		this.angleY = _angleY;
	}
	
	/**
	 * 
	 * @return
	 */
	public Vector3f getCamera() {
		return camera;
	}

	/**
	 * 
	 * @param camera
	 */
	public void setCamera(Vector3f camera) {
		this.camera = camera;
	}

	/**
	 * 
	 * @return
	 */
	public TransformGroup getTransformGroup() {
		return transformGroup;
	}

	/**
	 * 
	 * @param transformGroup
	 */
	public void setTransformGroup(TransformGroup transformGroup) {
		this.transformGroup = transformGroup;
	}
	
	/**
	 * 
	 * @return
	 */
	public float getXCamera() {
		return this.camera.x;
	}
	
	/**
	 * 
	 * @return
	 */
	public float getYCamera() {
		return this.camera.y;
	}

	/**
	 * 
	 * @return
	 */
	public float getZCamera() {
		return this.camera.z;
	}
	
	/**
	 * 
	 * @param _x
	 */
	public void setXCamera(float _x) {
				this.camera.x = _x;
	}
	
	/**
	 * 
	 * @param _y
	 */
	public void setYCamera(float _y) {
		this.camera.y = _y;
	}

	/**
	 * 
	 * @param _z
	 */
	public void setZCamera(float _z) {
		this.camera.z = _z;
	}

	public float getXFactor() {
		return xFactor;
	}

	public void setXFactor(float factor) {
		xFactor = factor;
	}

	public float getYFactor() {
		return yFactor;
	}

	public void setYFactor(float factor) {
		yFactor = factor;
	}
}
