package graph3d.universe.behaviors;

import javax.media.j3d.Behavior;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector3f;

public abstract class GBehavior extends Behavior {

	private Vector3f camera;
	private TransformGroup transformGroup;
	

	public void rotateX(float factor){
		
		float phi, theta;
		
		Transform3D translate=new Transform3D();
		Transform3D thetaRot = new Transform3D();
		Transform3D phiRot = new Transform3D();
		
System.out.println("x->"+this.getCamera().x+" y->"+this.getCamera().y+" z->"+this.getCamera().z);
		
		if(this.getCamera().x + (Math.abs(factor)) >= 0 && this.getCamera().z-(Math.abs(factor)) >= 0){
			
			theta = (float)Math.atan(this.getCamera().x/this.getCamera().z);
			phi = (float)Math.atan(this.getCamera().y/this.getCamera().z);
			
			translate.setTranslation(new Vector3f(this.getCamera().x+factor, this.getCamera().y , this.getCamera().z-factor ));
			this.setXCamera(this.getXCamera() + factor);
			this.setZCamera(this.getZCamera() - factor);

			phiRot.rotX(-phi);
			thetaRot.rotY(theta);

			translate.setTranslation(new Vector3f(this.getCamera().x+factor, this.getCamera().y , this.getCamera().z-factor ));
		} else
		if(this.getCamera().x-(Math.abs(factor)) >= 0 && this.getCamera().z-(Math.abs(factor)) <= 0){
			
			theta = (float)Math.atan(this.getCamera().x/this.getCamera().z);
			phi = (float)Math.atan(this.getCamera().y/this.getCamera().z);
			
			translate.setTranslation(new Vector3f(this.getCamera().x-factor, this.getCamera().y , this.getCamera().z-factor ));
			this.setXCamera(this.getXCamera() - factor);
			this.setZCamera(this.getZCamera() - factor);

			phiRot.rotX(-phi);
			thetaRot.rotY(theta);
			
			translate.setTranslation(new Vector3f(this.getCamera().x, this.getCamera().y , this.getCamera().z ));
		} else
		if(this.getCamera().x-(Math.abs(factor)) <= 0 && this.getCamera().z+(Math.abs(factor)) <= 0){
			
			theta = (float)Math.atan(this.getCamera().x/this.getCamera().z);
			phi = (float)Math.atan(this.getCamera().y/this.getCamera().z);
			
			translate.setTranslation(new Vector3f(this.getCamera().x-factor, this.getCamera().y , this.getCamera().z+factor ));
			this.setXCamera(this.getXCamera() - factor);
			this.setZCamera(this.getZCamera() + factor);

			phiRot.rotX(-phi);
			thetaRot.rotY(theta);
			
			translate.setTranslation(new Vector3f(this.getCamera().x-factor, this.getCamera().y , this.getCamera().z+factor ));
		} else
		if(this.getCamera().x+(Math.abs(factor)) <= 0 && this.getCamera().z+(Math.abs(factor)) >= 0){
			
			theta = (float)Math.atan(this.getCamera().x/this.getCamera().z);
			phi = (float)Math.atan(this.getCamera().y/this.getCamera().z);
			
			translate.setTranslation(new Vector3f(this.getCamera().x+factor, this.getCamera().y , this.getCamera().z+factor ));
			this.setXCamera(this.getXCamera() + factor);
			this.setZCamera(this.getZCamera() + factor);

			phiRot.rotX(-phi);
			thetaRot.rotY(theta);
			
			translate.setTranslation(new Vector3f(this.getCamera().x+factor, this.getCamera().y , this.getCamera().z+factor ));
		}

		translate.mul(phiRot);
		translate.mul(thetaRot);
		

		this.transformGroup.setTransform(translate);

		
	}
	
	/**
	 * 
	 */
	public void rotateY(float factor){
		
		System.out.println("x->"+this.getCamera().x+" y->"+this.getCamera().y+" z->"+this.getCamera().z);
		
		double phi, theta;
		
		Transform3D translate=new Transform3D();
		Transform3D thetaRot = new Transform3D();
		Transform3D phiRot = new Transform3D();
		
		if(this.getCamera().y+(Math.abs(factor)) >= 0 && this.getCamera().z-(Math.abs(factor)) >= 0){
			theta = Math.atan(this.getCamera().x/this.getCamera().z);
			phi = Math.atan(this.getCamera().y/this.getCamera().z);
			
			translate.setTranslation(new Vector3f(this.getCamera().x, this.getCamera().y+factor , this.getCamera().z-factor ));
			this.setYCamera(this.getYCamera() + factor);
			this.setZCamera(this.getZCamera() - factor);
			
			phiRot.rotX(-phi);
			thetaRot.rotY(theta);
			
			translate.setTranslation(new Vector3f(this.getCamera().x, this.getCamera().y+factor , this.getCamera().z-factor ));
		}
		if(this.getCamera().y-(Math.abs(factor)) >= 0 && this.getCamera().z-(Math.abs(factor)) <= 0){
			theta = Math.atan(this.getCamera().x/this.getCamera().z);
			phi = Math.atan(this.getCamera().y/this.getCamera().z);
			
			translate.setTranslation(new Vector3f(this.getCamera().x, this.getCamera().y-factor , this.getCamera().z-factor ));
			this.setYCamera(this.getYCamera() - factor);
			this.setZCamera(this.getZCamera() - factor);
			
			phiRot.rotX(-phi);
			thetaRot.rotY(theta);
			
			translate.setTranslation(new Vector3f(this.getCamera().x, this.getCamera().y+factor , this.getCamera().z-factor ));
		}
		if(this.getCamera().y-(factor*2) <= 0 && this.getCamera().z+(factor*2) <= 0){
			theta = Math.atan(this.getCamera().x/this.getCamera().z);
			phi = Math.atan(this.getCamera().y/this.getCamera().z);
			
			translate.setTranslation(new Vector3f(this.getCamera().x, this.getCamera().y-factor , this.getCamera().z+factor ));
			this.setYCamera(this.getYCamera() - factor);
			this.setZCamera(this.getZCamera() + factor);
			
			phiRot.rotX(-phi);
			thetaRot.rotY(theta);
			
			translate.setTranslation(new Vector3f(this.getCamera().x, this.getCamera().y-factor , this.getCamera().z+factor ));
		}
		if(this.getCamera().y+(Math.abs(factor)) <= 0 && this.getCamera().z+(Math.abs(factor)) >= 0){
			theta = Math.atan(this.getCamera().x/this.getCamera().z);
			phi = Math.atan(this.getCamera().y/this.getCamera().z);
			
			translate.setTranslation(new Vector3f(this.getCamera().x, this.getCamera().y+factor , this.getCamera().z+factor ));
			this.setYCamera(this.getYCamera() + factor);
			this.setZCamera(this.getZCamera() + factor);
			
			phiRot.rotX(-phi);
			thetaRot.rotY(theta);
			
			translate.setTranslation(new Vector3f(this.getCamera().x, this.getCamera().y+factor , this.getCamera().z+factor ));
		}


		translate.mul(phiRot);
		translate.mul(thetaRot);
		
		this.transformGroup.setTransform(translate);
	}

	public Vector3f getCamera() {
		return camera;
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
