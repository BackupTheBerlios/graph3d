package graph3d.universe.behaviors;

import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector3f;

public interface GBehavior {

	/**
	 * This fonction is used to move the camera's view to do a zoom
	 * @param distance type of float
	 */
	public Transform3D zoom(float distance);
	
	/**
	 * 
	 * @param factor
	 */
	public Transform3D rotateX(float factor);
	
	/**
	 * 
	 * @param factor
	 */
	public Transform3D rotateY(float factor);
	

}
