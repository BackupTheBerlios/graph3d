package graph3d.universe.behaviors;

import javax.media.j3d.Behavior;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector3f;

public abstract class GOneActionBehavior extends Behavior implements GBehavior {

	private Vector3f camera;
	private TransformGroup transformGroup;
	private double angleX,angleY;
	
	
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
	
	/*public Transform3D rotateXX(float angle) {
		Transform3D move = new Transform3D();
		Transform3D rotateTheView = new Transform3D();
		Transform3D moveToPoint = new Transform3D();
		
		float cosZ = (float)Math.cos(angle);
		float sinX = (float)Math.sin(angle);
		
		move.setTranslation(new Vector3f(-this.camera.x, 0, -this.camera.z));
		
		rotateTheView.rotX(angle);
		this.camera.x = this.camera.x * sinX;
		this.camera.z = this.camera.z * cosZ;
		moveToPoint.setTranslation(new Vector3f(this.camera.x, 0, this.camera.z));
		
		move.mul(rotateTheView);
		move.mul(moveToPoint);
		
		return move;
	}*/
	
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

			angleXRot.rotY(this.angleX);
			angleYRot.rotX(this.angleY);
			
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
			
			translate.setTranslation(new Vector3f(this.camera.x, this.camera.y+factor , this.camera.z-factor ));
			this.camera.y += factor;
			this.camera.z -= factor;
			
			angleXRot.rotY(this.angleX);
			angleYRot.rotX(this.angleY);
		
		}
			
		if(this.camera.y-(Math.abs(factor)) >= 0 && this.camera.z-(Math.abs(factor)) <= 0){
			if(factor<0){
				this.angleY = -(Math.toRadians(90)+Math.atan(Math.abs(this.camera.z)/this.camera.y));
			}else{
				this.angleY = -(-(Math.toRadians(180)+Math.atan(this.camera.y/Math.abs(this.camera.z))));
			}
				
			translate.setTranslation(new Vector3f(this.camera.x, this.camera.y-factor , this.camera.z-factor ));
			this.camera.y -= factor;
			this.camera.z -= factor;
				
			angleXRot.rotY(this.angleX);
			angleYRot.rotX(this.angleY);
		
		}
			
			if(this.camera.y-(Math.abs(factor)) <= 0 && this.camera.z+(Math.abs(factor)) <= 0){
				if(factor<0){
					this.angleY = -(Math.toRadians(180)+Math.atan(Math.abs(this.camera.y)/Math.abs(this.camera.z)));
				}else{
					this.angleY = -(-(Math.toRadians(90)+Math.atan(Math.abs(this.camera.z)/Math.abs(this.camera.y))));
				}
					
				translate.setTranslation(new Vector3f(this.camera.x, this.camera.y-factor , this.camera.z+factor ));
				this.camera.y -= factor;
				this.camera.z += factor;
				
				angleXRot.rotY(this.angleX);
				angleYRot.rotX(this.angleY);
					
			}
					
				if(this.camera.y+(Math.abs(factor)) <= 0 && this.camera.z+(Math.abs(factor)) >= 0){
					if(factor<0){
						this.angleY = -(Math.toRadians(270)+Math.atan(this.camera.z/Math.abs(this.camera.y)));
					}else{
						this.angleY = -(-(Math.atan(Math.abs(this.camera.y)/this.camera.z)));
					}
					
					translate.setTranslation(new Vector3f(this.camera.x, this.camera.y+factor , this.camera.z+factor ));
					this.camera.y += factor;
					this.camera.z += factor;
					
					angleXRot.rotY(this.angleX);
					angleYRot.rotX(this.angleY);
						
				}

		//translate.mul(angleXRot);
		translate.mul(angleYRot);
		
		return translate;
	}
	
	public void setAngles(double angleX,double angleY){
		this.angleX=angleX;
		this.angleY=angleY;
	}
	
	public Vector3f getCamera() {
		return this.camera;
	}

	public void setCamera(Vector3f camera) {
		this.camera = camera;
	}

	public TransformGroup getTransformGroup() {
		return transformGroup;
	}

	public void setTransformGroup(TransformGroup transformGroup) {
		this.transformGroup = transformGroup;
	}
	
	public float getXCamera() {
		return this.camera.x;
	}
	
	public float getYCamera() {
		return this.camera.y;
	}
	
	public float getZCamera() {
		return this.camera.z;
	}
	
	public void setXCamera(float _x) {
				this.camera.x = _x;
	}
	
	public void setYCamera(float _y) {
		this.camera.y = _y;
	}

	public void setZCamera(float _z) {
		this.camera.z = _z;
	}
}