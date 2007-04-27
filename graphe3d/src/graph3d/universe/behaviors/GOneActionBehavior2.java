package graph3d.universe.behaviors;

import graph3d.universe.GView;

import javax.media.j3d.Behavior;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector3f;

/**
 * This abstract class define the function which necessary to the interaction between keyboard and the pad.
 * @author Erwan Daubert && Nicolas Magnin
 * @version 1.0
 *
 */
public abstract class GOneActionBehavior2 extends Behavior implements GBehavior {

	private GView gview;
	private TransformGroup transformGroup;
	private static double angleX,angleY;
	
	
	/**
	 * This fonction is used to move the camera's view to do a zoom
	 * @param distance type of float
	 */
	public Transform3D zoom(float distance){
		
		Transform3D zoom = new Transform3D();
		Transform3D angleXRot = new Transform3D();
		Transform3D angleYRot = new Transform3D();
		
		Vector3f camera = this.gview.getPositionToTheView();
		
		angleXRot.rotY(angleX);
		angleYRot.rotX(angleY);
		zoom.setTranslation(new Vector3f(camera.x*distance, camera.y*distance , camera.z*distance ));
		
		camera.x=camera.x*distance;
		camera.y=camera.y*distance;
		camera.z=camera.z*distance;
		
		zoom.mul(angleXRot);
		zoom.mul(angleYRot);
		
		return zoom;	
	}
	
	public Transform3D rotateX(float factor){
		
		Transform3D translate=new Transform3D();
		Transform3D angleXRot = new Transform3D();
		Transform3D angleYRot = new Transform3D();

		Vector3f camera = this.gview.getPositionToTheView();
		
		if(camera.x+(Math.abs(factor)) >= 0 && camera.z-(Math.abs(factor)) >= 0){
			if(factor<0){
				angleX = Math.atan(camera.x/camera.z);
			}else{
				angleX = -(Math.toRadians(270)+Math.atan(camera.z/camera.x));
			}
			
			translate.setTranslation(new Vector3f(camera.x+factor, camera.y , camera.z-factor ));
			camera.x += factor;
			camera.z -= factor;

			angleXRot.rotY(angleX);
			angleYRot.rotX(angleY);
		}
		if(camera.x-(Math.abs(factor)) >= 0 && camera.z-(Math.abs(factor)) <= 0){
			if(factor<0){
				angleX = Math.toRadians(90)+Math.atan(Math.abs(camera.z)/camera.x);
			}else{
				angleX = -(Math.toRadians(180)+Math.atan(camera.x/Math.abs(camera.z)));
			}
			
			translate.setTranslation(new Vector3f(camera.x-factor, camera.y , camera.z-factor ));
			camera.x -= factor;
			camera.z -= factor;

			angleXRot.rotY(angleX);
			angleYRot.rotX(angleY);
		}
		if(camera.x-(Math.abs(factor)) <= 0 && camera.z+(Math.abs(factor)) <= 0){
			if(factor<0){
				angleX = Math.toRadians(180)+Math.atan(Math.abs(camera.x)/Math.abs(camera.z));
			}else{
				angleX = -(Math.toRadians(90)+Math.atan(Math.abs(camera.z)/Math.abs(camera.x)));
			}
			
			translate.setTranslation(new Vector3f(camera.x-factor, camera.y , camera.z+factor ));
			camera.x -= factor;
			camera.z += factor;

			angleXRot.rotY(angleX);
			angleYRot.rotX(angleY);
			
		}
		if(camera.x+(Math.abs(factor)) <= 0 && camera.z+(Math.abs(factor)) >= 0){
			if(factor<0){
				angleX = Math.toRadians(270)+Math.atan(camera.z/Math.abs(camera.x));
			}else{
				angleX = -(Math.atan(Math.abs(camera.x)/camera.z));
			}
			
			translate.setTranslation(new Vector3f(camera.x+factor, camera.y , camera.z+factor ));
			camera.x += factor;
			camera.z += factor;

			angleXRot.rotY(angleX);
			angleYRot.rotX(angleY);
			
		}

		translate.mul(angleXRot);
		translate.mul(angleYRot);
		
		return translate;	
	}
	
	public Transform3D rotateY(float factor){
		
		Transform3D translate=new Transform3D();
		Transform3D angleYRot = new Transform3D();
		Transform3D angleXRot = new Transform3D();

		Vector3f camera = this.gview.getPositionToTheView();
		
		if(camera.y+(Math.abs(factor)) >= 0 && camera.z-(Math.abs(factor)) >= 0){
			if(factor<0){
				angleY = -(Math.atan(camera.y/camera.z));
			}else{
				angleY = -(-(Math.toRadians(270)+Math.atan(camera.z/camera.y)));
			}
			
			translate.setTranslation(new Vector3f(camera.x, camera.y+factor , camera.z-factor ));
			camera.y += factor;
			camera.z -= factor;
			
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
			camera.y -= factor;
			camera.z -= factor;
				
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
				camera.y -= factor;
				camera.z += factor;
				
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
					camera.y += factor;
					camera.z += factor;
					
					angleXRot.rotY(angleX);
					angleYRot.rotX(angleY);
						
				}

		translate.mul(angleXRot);
		translate.mul(angleYRot);
		
		return translate;
	}
	
	/**
	 * This function is used to set the angle X and Y which is use to create the rotation of the view
	 * @param _angleX
	 * @param _angleY
	 */
	public void setAngles(double _angleX,double _angleY){
		angleX=_angleX;
		angleY=_angleY;
	}

	/**
	 * The getter of transformGroup which is the transformGroup of the view.
	 * @return the transformGroup of the view on which you use the rotation or the zoom.
	 */
	protected TransformGroup getTransformGroup() {
		return this.transformGroup;
	}

	/**
	 * The setter of the transformGroup. This transformGroup must be the transformGroup of the view if you want rotate the view.
	 * @param transformGroup
	 */
	protected void setTransformGroup(TransformGroup transformGroup) {
		this.transformGroup = transformGroup;
	}

	/**
	 * The getter of the gview which define the view 
	 * @return the GView which is the view.
	 */
	public GView getGview() {
		return this.gview;
	}

	/**
	 * The setter of the GView. This GView must is the GView which you use to define the Canvas3D into your application.
	 * Else any rotation or zoom can be define
	 * @param gview the new GView
	 */
	public void setGview(GView gview) {
		this.gview = gview;
	}
}