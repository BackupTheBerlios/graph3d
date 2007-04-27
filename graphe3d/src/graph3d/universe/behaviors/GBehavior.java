package graph3d.universe.behaviors;

import javax.media.j3d.Transform3D;

/**
 * This interface define the three functions which is used to rotate the view when you use the keyboard, the pad define into the package use and also the mouse.
 * @author Erwan Daubert && Nicolas Magnin
 * @version 1.0
 *
 */
public interface GBehavior {

	/**
	 * This fonction is used to move the camera's view to do a zoom
	 * @param distance type of float
	 * @return a transform3D which define the zoom
	 */
	public Transform3D zoom(float distance);
	
	/**
	 * This function is used to rotate the view on the X axis
	 * @param factor
	 * @return a transform3D which define the rotation
	 */
	public Transform3D rotateX(float factor);
	
	/**
	 * This function is used to rotate the view on the Y axis
	 * @param factor
	 * @return a transform3D which define the rotation
	 */
	public Transform3D rotateY(float factor);

}
