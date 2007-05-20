package graph3d.universe.behaviors;

import graph3d.elements.GNode;
import graph3d.lists.GAttributesList;
import graph3d.universe.BasicFunctions;
import graph3d.universe.GGraphUniverse;

import java.awt.AWTEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.Hashtable;
import java.util.Iterator;

import javax.media.j3d.Canvas3D;
import javax.media.j3d.Transform3D;
import javax.swing.JButton;
import javax.vecmath.Matrix3d;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import com.sun.j3d.internal.J3dUtilsI18N;
import com.sun.j3d.utils.behaviors.vp.ViewPlatformAWTBehavior;
import com.sun.j3d.utils.universe.ViewingPlatform;

/**
 * This class define the interactions between mouse and keyboard and also the ButtonInteraction object.
 * This class contains a big part of the code which is write in OrbitBehavior.
 * It's not extent OrbitBehavior because few functions of OrbitBehavior are private and it's necessary to get acces of this functions.
 * @author Erwan Daubert
 *
 */
public class GBehaviors extends ViewPlatformAWTBehavior  implements ActionListener {

	private GGraphUniverse graphUniverse;
	
	private GAttributesList attributesList;

	private Transform3D longditudeTransform;

	private Transform3D latitudeTransform;

	private Transform3D rotateTransform;

	private Transform3D temp1;

	private Transform3D temp2;

	private Transform3D translation;

	private Vector3d transVector;

	private Vector3d distanceVector;

	private Vector3d centerVector;

	private Vector3d invertCenterVector;

	private double longditude;

	private double latitude;

	private double startDistanceFromCenter;

	private double distanceFromCenter;

	private Point3d rotationCenter;

	private Matrix3d rotMatrix;

	private Transform3D currentXfm;

	private int mouseX;

	private int mouseY;

	private double rotXFactor;

	private double rotYFactor;

	private double transXFactor;

	private double transYFactor;

	private double zoomFactor;

	private double xtrans;

	private double ytrans;

	private double ztrans;

	private boolean zoomEnabled;

	private boolean rotateEnabled;

	private boolean translateEnabled;

	private boolean reverseRotate;

	private boolean reverseTrans;

	private boolean reverseZoom;

	private boolean stopZoom;

	private boolean proportionalZoom;

	private double minRadius;

	private int leftButton;

	private int rightButton;

	private int middleButton;

	private float wheelZoomFactor;
	
	private double rotXMul;

	private double rotYMul;

	private double transXMul;

	private double transYMul;

	private double zoomMul;

	/**
	 * 
	 * @param canvas3d
	 * @param _graphUniverse
	 * @see com.sun.j3d.utils.behaviors.vp.OrbitBehavior in Java3d
	 */
	public GBehaviors(Canvas3D canvas3d, GGraphUniverse _graphUniverse) {
		super(canvas3d, ViewPlatformAWTBehavior.KEY_LISTENER | ViewPlatformAWTBehavior.MOUSE_LISTENER | ViewPlatformAWTBehavior.MOUSE_MOTION_LISTENER | ViewPlatformAWTBehavior.MOUSE_WHEEL_LISTENER);
		this.graphUniverse = _graphUniverse;
		this.longditudeTransform = new Transform3D();
		this.latitudeTransform = new Transform3D();
		this.rotateTransform = new Transform3D();
		this.temp1 = new Transform3D();
		this.temp2 = new Transform3D();
		this.translation = new Transform3D();
		this.transVector = new Vector3d();
		this.distanceVector = new Vector3d();
		this.centerVector = new Vector3d();
		this.invertCenterVector = new Vector3d();
		this.longditude = 0.0D;
		this.latitude = 0.0D;
		this.startDistanceFromCenter = 20D;
		this.distanceFromCenter = 20D;
		this.rotationCenter = new Point3d();
		this.rotMatrix = new Matrix3d();
		this.currentXfm = new Transform3D();
		this.mouseX = 0;
		this.mouseY = 0;
		this.rotXFactor = 1.0D;
		this.rotYFactor = 1.0D;
		this.transXFactor = 1.0D;
		this.transYFactor = 1.0D;
		this.zoomFactor = 10.0D;
		this.xtrans = 0.0D;
		this.ytrans = 0.0D;
		this.ztrans = 0.0D;
		this.zoomEnabled = true;
		this.rotateEnabled = true;
		this.translateEnabled = true;
		this.reverseRotate = false;
		this.reverseTrans = false;
		this.reverseZoom = false;
		this.stopZoom = false;
		this.proportionalZoom = false;
		this.minRadius = 0.0D;
		this.leftButton = 0;
		this.rightButton = 1;
		this.middleButton = 2;
		this.wheelZoomFactor = 50F;
		this.rotXMul = 0.01D * this.rotXFactor;
		this.rotYMul = 0.01D * this.rotYFactor;
		this.transXMul = 0.01D * this.transXFactor;
		this.transYMul = 0.01D * this.transYFactor;
		this.zoomMul = 0.01D * this.zoomFactor;
	}

	/**
	 * This function is used to call modification into the Canvas when mouse or keyboard is used into the Canvas.
	 */
	protected synchronized void processAWTEvents(AWTEvent aawtevent[]) {
		this.motion = false;
		if (this.attributesList == null || this.attributesList.getElements().isEmpty()) {
			float[] barycenter = BasicFunctions.calcBarycenter(this.graphUniverse.getGraph().getNodes());
			this.setRotationCenter(new Point3d(barycenter[0], barycenter[1], barycenter[2]));
		}
		for (int i = 0; i < aawtevent.length; i++) {
			if (aawtevent[i] instanceof MouseEvent) {
				processMouseEvent((MouseEvent) aawtevent[i]);
			} else if (aawtevent[i] instanceof KeyEvent) {
				processKeyEvent((KeyEvent) aawtevent[i]);
			}
		}

	}

	/**
	 * This function is used to rotate, translate or zoom when the mouse is used.
	 * @param mouseevent
	 * @see com.sun.j3d.utils.behaviors.vp.OrbitBehavior in Java3d
	 */
	protected void processMouseEvent(MouseEvent mouseevent) {
		if (mouseevent.getID() == 501) {
			mouseX = mouseevent.getX();
			mouseY = mouseevent.getY();
			motion = true;
		} else if (mouseevent.getID() == 506) {
			int i = mouseevent.getX() - mouseX;
			int k = mouseevent.getY() - mouseY;
			if (rotate(mouseevent)) {
				if (reverseRotate) {
					longditude -= (double) i * rotXMul;
					latitude -= (double) k * rotYMul;
				} else {
					longditude += (double) i * rotXMul;
					latitude += (double) k * rotYMul;
				}
			} else if (translate(mouseevent)) {
				if (reverseTrans) {
					xtrans -= (double) i * transXMul;
					ytrans += (double) k * transYMul;
				} else {
					xtrans += (double) i * transXMul;
					ytrans -= (double) k * transYMul;
				}
			} else if (zoom(mouseevent))
				doZoomOperations(k);
			mouseX = mouseevent.getX();
			mouseY = mouseevent.getY();
			motion = true;
		} else if (mouseevent.getID() != 502 && mouseevent.getID() == 507
				&& zoom(mouseevent) && (mouseevent instanceof MouseWheelEvent)) {
			int j = (int) ((float) ((MouseWheelEvent) mouseevent)
					.getWheelRotation() * wheelZoomFactor);
			doZoomOperations(j);
			motion = true;
		}
	}

	/**
	 * This function is one that I have write to rotate or zoom with keyboard actions.
	 * @param keyEvent the Event which launch the action
	 */
	protected void processKeyEvent(KeyEvent keyEvent) {
			if (keyEvent.getKeyCode() == KeyEvent.VK_LEFT) {
				longditude -= rotXMul * 2;
				motion = true;
			}else if (keyEvent.getKeyCode() == KeyEvent.VK_RIGHT) {
				longditude += rotXMul * 2;
				motion = true;
			} else if (keyEvent.getKeyCode() == KeyEvent.VK_UP) {
				latitude -= rotYMul * 2;
				motion = true;
			} else if (keyEvent.getKeyCode() == KeyEvent.VK_DOWN) {
				latitude += rotYMul * 2;
				motion = true;
			} else if (keyEvent.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
				doZoomOperations(1 * 2);
				motion = true;
			} else if (keyEvent.getKeyCode() == KeyEvent.VK_PAGE_UP) {
				doZoomOperations(-1 * 2);
				motion = true;
			}
	}

	private void doZoomOperations(int i) {
		if (proportionalZoom) {
			if (reverseZoom) {
				if (distanceFromCenter
						- (zoomMul * (double) i * distanceFromCenter) / 100D > minRadius)
					distanceFromCenter -= (zoomMul * (double) i * distanceFromCenter) / 100D;
				else
					distanceFromCenter = minRadius;
			} else if (distanceFromCenter
					+ (zoomMul * (double) i * distanceFromCenter) / 100D > minRadius)
				distanceFromCenter += (zoomMul * (double) i * distanceFromCenter) / 100D;
			else
				distanceFromCenter = minRadius;
		} else if (stopZoom) {
			if (reverseZoom) {
				if (distanceFromCenter - (double) i * zoomMul > minRadius)
					distanceFromCenter -= (double) i * zoomMul;
				else
					distanceFromCenter = minRadius;
			} else if (distanceFromCenter + (double) i * zoomMul > minRadius)
				distanceFromCenter += (double) i * zoomMul;
			else
				distanceFromCenter = minRadius;
		} else if (reverseZoom)
			distanceFromCenter -= (double) i * zoomMul;
		else
			distanceFromCenter += (double) i * zoomMul;
	}

	/**
	 * @see com.sun.j3d.utils.behaviors.vp.OrbitBehavior in Java3D
	 */
	public void setViewingPlatform(ViewingPlatform viewingplatform) {
		super.setViewingPlatform(viewingplatform);
		if (viewingplatform != null) {
			resetView();
			integrateTransforms();
		}
	}

	/**
	 * @see com.sun.j3d.utils.behaviors.vp.OrbitBehavior in Java3D
	 */
	private void resetView() {
		Vector3d vector3d = new Vector3d();
		targetTG.getTransform(targetTransform);
		targetTransform.get(rotMatrix, transVector);
		vector3d.sub(transVector, rotationCenter);
		distanceFromCenter = vector3d.length();
		startDistanceFromCenter = distanceFromCenter;
		targetTransform.get(rotMatrix);
		rotateTransform.set(rotMatrix);
		temp1.set(vector3d);
		rotateTransform.invert();
		rotateTransform.mul(temp1);
		rotateTransform.get(vector3d);
		xtrans = vector3d.x;
		ytrans = vector3d.y;
		ztrans = vector3d.z;
		rotateTransform.set(rotMatrix);
	}

	/**
	 * @see com.sun.j3d.utils.behaviors.vp.OrbitBehavior in Java3D
	 */
	protected synchronized void integrateTransforms() {
		targetTG.getTransform(currentXfm);
		if (!targetTransform.equals(currentXfm))
			resetView();
		longditudeTransform.rotY(longditude);
		latitudeTransform.rotX(latitude);
		rotateTransform.mul(rotateTransform, latitudeTransform);
		rotateTransform.mul(rotateTransform, longditudeTransform);
		distanceVector.z = distanceFromCenter - startDistanceFromCenter;
		temp1.set(distanceVector);
		temp1.mul(rotateTransform, temp1);
		transVector.x = rotationCenter.x + xtrans;
		transVector.y = rotationCenter.y + ytrans;
		transVector.z = rotationCenter.z + ztrans;
		translation.set(transVector);
		targetTransform.mul(temp1, translation);
		temp1.set(centerVector);
		temp1.mul(targetTransform);
		invertCenterVector.x = -centerVector.x;
		invertCenterVector.y = -centerVector.y;
		invertCenterVector.z = -centerVector.z;
		temp2.set(invertCenterVector);
		targetTransform.mul(temp1, temp2);
		targetTG.setTransform(targetTransform);
		longditude = 0.0D;
		latitude = 0.0D;
	}

	/**
	 * @see com.sun.j3d.utils.behaviors.vp.OrbitBehavior in Java3D
	 */
	public synchronized void setRotationCenter(Point3d point3d) {
		rotationCenter.x = point3d.x;
		rotationCenter.y = point3d.y;
		rotationCenter.z = point3d.z;
		centerVector.set(rotationCenter);
	}

	/**
	 * @see com.sun.j3d.utils.behaviors.vp.OrbitBehavior in Java3D
	 */
	public void RotationCenter(Object aobj[]) {
		if (aobj.length != 1 || !(aobj[0] instanceof Point3d)) {
			throw new IllegalArgumentException(
					"RotationCenter must be a single Point3d");
		} else {
			setRotationCenter((Point3d) aobj[0]);
			return;
		}
	}

	/**
	 * @see com.sun.j3d.utils.behaviors.vp.OrbitBehavior in Java3D
	 */
	public void getRotationCenter(Point3d point3d) {
		point3d.x = rotationCenter.x;
		point3d.y = rotationCenter.y;
		point3d.z = rotationCenter.z;
	}

	/**
	 * @see com.sun.j3d.utils.behaviors.vp.OrbitBehavior in Java3D
	 */
	public synchronized void setRotFactors(double d, double d1) {
		rotXFactor = d;
		rotYFactor = d1;
		rotXMul = 0.01D * d;
		rotYMul = 0.01D * d1;
	}

	/**
	 * @see com.sun.j3d.utils.behaviors.vp.OrbitBehavior in Java3D
	 */
	public void RotFactors(Object aobj[]) {
		if (aobj.length != 2 || !(aobj[0] instanceof Double)
				|| !(aobj[1] instanceof Double)) {
			throw new IllegalArgumentException("RotFactors must be two Doubles");
		} else {
			setRotFactors(((Double) aobj[0]).doubleValue(), ((Double) aobj[1])
					.doubleValue());
			return;
		}
	}

	/**
	 * @see com.sun.j3d.utils.behaviors.vp.OrbitBehavior in Java3D
	 */
	public synchronized void setRotXFactor(double d) {
		rotXFactor = d;
		rotXMul = 0.01D * d;
	}

	/**
	 * @see com.sun.j3d.utils.behaviors.vp.OrbitBehavior in Java3D
	 */
	public void RotXFactor(Object aobj[]) {
		if (aobj.length != 1 || !(aobj[0] instanceof Double)) {
			throw new IllegalArgumentException("RotXFactor must be a Double");
		} else {
			setRotXFactor(((Double) aobj[0]).doubleValue());
			return;
		}
	}

	/**
	 * @see com.sun.j3d.utils.behaviors.vp.OrbitBehavior in Java3D
	 */
	public synchronized void setRotYFactor(double d) {
		rotYFactor = d;
		rotYMul = 0.01D * d;
	}

	/**
	 * @see com.sun.j3d.utils.behaviors.vp.OrbitBehavior in Java3D
	 */
	public void RotYFactor(Object aobj[]) {
		if (aobj.length != 1 || !(aobj[0] instanceof Double)) {
			throw new IllegalArgumentException("RotYFactor must be a Double");
		} else {
			setRotYFactor(((Double) aobj[0]).doubleValue());
			return;
		}
	}

	/**
	 * @see com.sun.j3d.utils.behaviors.vp.OrbitBehavior in Java3D
	 */
	public synchronized void setTransFactors(double d, double d1) {
		transXFactor = d;
		transYFactor = d1;
		transXMul = 0.01D * d;
		transYMul = 0.01D * d1;
	}

	/**
	 * @see com.sun.j3d.utils.behaviors.vp.OrbitBehavior in Java3D
	 */
	public void TransFactors(Object aobj[]) {
		if (aobj.length != 2 || !(aobj[0] instanceof Double)
				|| !(aobj[1] instanceof Double)) {
			throw new IllegalArgumentException(
					"TransFactors must be two Doubles");
		} else {
			setTransFactors(((Double) aobj[0]).doubleValue(),
					((Double) aobj[1]).doubleValue());
			return;
		}
	}

	/**
	 * @see com.sun.j3d.utils.behaviors.vp.OrbitBehavior in Java3D
	 */
	public synchronized void setTransXFactor(double d) {
		transXFactor = d;
		transXMul = 0.01D * d;
	}

	/**
	 * @see com.sun.j3d.utils.behaviors.vp.OrbitBehavior in Java3D
	 */
	public void TransXFactor(Object aobj[]) {
		if (aobj.length != 1 || !(aobj[0] instanceof Double)) {
			throw new IllegalArgumentException("TransXFactor must be a Double");
		} else {
			setTransXFactor(((Double) aobj[0]).doubleValue());
			return;
		}
	}

	/**
	 * @see com.sun.j3d.utils.behaviors.vp.OrbitBehavior in Java3D
	 */
	public synchronized void setTransYFactor(double d) {
		transYFactor = d;
		transYMul = 0.01D * d;
	}

	/**
	 * @see com.sun.j3d.utils.behaviors.vp.OrbitBehavior in Java3D
	 */
	public void TransYFactor(Object aobj[]) {
		if (aobj.length != 1 || !(aobj[0] instanceof Double)) {
			throw new IllegalArgumentException("TransYFactor must be a Double");
		} else {
			setTransYFactor(((Double) aobj[0]).doubleValue());
			return;
		}
	}

	public synchronized void setZoomFactor(double d) {
		zoomFactor = d;
		if (proportionalZoom)
			zoomMul = 1.0D * d;
		else
			zoomMul = 0.01D * d;
	}

	/**
	 * @see com.sun.j3d.utils.behaviors.vp.OrbitBehavior in Java3D
	 */
	public void ZoomFactor(Object aobj[]) {
		if (aobj.length != 1 || !(aobj[0] instanceof Double)) {
			throw new IllegalArgumentException("ZoomFactor must be a Double");
		} else {
			setZoomFactor(((Double) aobj[0]).doubleValue());
			return;
		}
	}

	/**
	 * @see com.sun.j3d.utils.behaviors.vp.OrbitBehavior in Java3D
	 */
	public double getRotXFactor() {
		return rotXFactor;
	}

	/**
	 * @see com.sun.j3d.utils.behaviors.vp.OrbitBehavior in Java3D
	 */
	public double getRotYFactor() {
		return rotYFactor;
	}

	/**
	 * @see com.sun.j3d.utils.behaviors.vp.OrbitBehavior in Java3D
	 */
	public double getTransXFactor() {
		return transXFactor;
	}

	/**
	 * @see com.sun.j3d.utils.behaviors.vp.OrbitBehavior in Java3D
	 */
	public double getTransYFactor() {
		return transYFactor;
	}

	/**
	 * @see com.sun.j3d.utils.behaviors.vp.OrbitBehavior in Java3D
	 */
	public double getZoomFactor() {
		return zoomFactor;
	}

	/**
	 * @see com.sun.j3d.utils.behaviors.vp.OrbitBehavior in Java3D
	 */
	public synchronized void setRotateEnable(boolean flag) {
		rotateEnabled = flag;
	}

	/**
	 * @see com.sun.j3d.utils.behaviors.vp.OrbitBehavior in Java3D
	 */
	public void RotateEnable(Object aobj[]) {
		if (aobj.length != 1 || !(aobj[0] instanceof Boolean)) {
			throw new IllegalArgumentException("RotateEnable must be Boolean");
		} else {
			setRotateEnable(((Boolean) aobj[0]).booleanValue());
			return;
		}
	}

	/**
	 * @see com.sun.j3d.utils.behaviors.vp.OrbitBehavior in Java3D
	 */
	public synchronized void setZoomEnable(boolean flag) {
		zoomEnabled = flag;
	}

	/**
	 * @see com.sun.j3d.utils.behaviors.vp.OrbitBehavior in Java3D
	 */
	public void ZoomEnable(Object aobj[]) {
		if (aobj.length != 1 || !(aobj[0] instanceof Boolean)) {
			throw new IllegalArgumentException("ZoomEnable must be Boolean");
		} else {
			setZoomEnable(((Boolean) aobj[0]).booleanValue());
			return;
		}
	}

	/**
	 * @see com.sun.j3d.utils.behaviors.vp.OrbitBehavior in Java3D
	 */
	public synchronized void setTranslateEnable(boolean flag) {
		translateEnabled = flag;
	}

	/**
	 * @see com.sun.j3d.utils.behaviors.vp.OrbitBehavior in Java3D
	 */
	public void TranslateEnable(Object aobj[]) {
		if (aobj.length != 1 || !(aobj[0] instanceof Boolean)) {
			throw new IllegalArgumentException(
					"TranslateEnable must be Boolean");
		} else {
			setTranslateEnable(((Boolean) aobj[0]).booleanValue());
			return;
		}
	}

	/**
	 * @see com.sun.j3d.utils.behaviors.vp.OrbitBehavior in Java3D
	 */
	public boolean getRotateEnable() {
		return rotateEnabled;
	}

	/**
	 * @see com.sun.j3d.utils.behaviors.vp.OrbitBehavior in Java3D
	 */
	public boolean getZoomEnable() {
		return zoomEnabled;
	}

	/**
	 * @see com.sun.j3d.utils.behaviors.vp.OrbitBehavior in Java3D
	 */
	public boolean getTranslateEnable() {
		return translateEnabled;
	}

	/**
	 * @see com.sun.j3d.utils.behaviors.vp.OrbitBehavior in Java3D
	 */
	boolean rotate(MouseEvent mouseevent) {
		if (rotateEnabled) {
			if (leftButton == 0 && !mouseevent.isAltDown()
					&& !mouseevent.isMetaDown())
				return true;
			if (middleButton == 0 && mouseevent.isAltDown()
					&& !mouseevent.isMetaDown())
				return true;
			if (rightButton == 0 && !mouseevent.isAltDown()
					&& mouseevent.isMetaDown())
				return true;
		}
		return false;
	}

	/**
	 * @see com.sun.j3d.utils.behaviors.vp.OrbitBehavior in Java3D
	 */
	boolean zoom(MouseEvent mouseevent) {
		if (zoomEnabled) {
			if (mouseevent instanceof MouseWheelEvent)
				return true;
			if (leftButton == 2 && !mouseevent.isAltDown()
					&& !mouseevent.isMetaDown())
				return true;
			if (middleButton == 2 && mouseevent.isAltDown()
					&& !mouseevent.isMetaDown())
				return true;
			if (rightButton == 2 && !mouseevent.isAltDown()
					&& mouseevent.isMetaDown())
				return true;
		}
		return false;
	}

	/**
	 * @see com.sun.j3d.utils.behaviors.vp.OrbitBehavior in Java3D
	 */
	boolean translate(MouseEvent mouseevent) {
		if (translateEnabled) {
			if (leftButton == 1 && !mouseevent.isAltDown()
					&& !mouseevent.isMetaDown())
				return true;
			if (middleButton == 1 && mouseevent.isAltDown()
					&& !mouseevent.isMetaDown())
				return true;
			if (rightButton == 1 && !mouseevent.isAltDown()
					&& mouseevent.isMetaDown())
				return true;
		}
		return false;
	}
	
	/**
	 * @see com.sun.j3d.utils.behaviors.vp.OrbitBehavior in Java3D
	 */
	boolean zoom(KeyEvent keyEvent) {
		if (zoomEnabled) {
			if (keyEvent.getKeyCode() == KeyEvent.VK_PAGE_DOWN || keyEvent.getKeyCode() == KeyEvent.VK_PAGE_UP)
				return true;
		}
		return false;
	}

	/**
	 * @see com.sun.j3d.utils.behaviors.vp.OrbitBehavior in Java3D
	 */
	boolean translate(KeyEvent keyEvent) {
		if (translateEnabled) {
			if (leftButton == 1 && !keyEvent.isAltDown()
					&& !keyEvent.isMetaDown())
				return true;
			if (middleButton == 1 && keyEvent.isAltDown()
					&& !keyEvent.isMetaDown())
				return true;
			if (rightButton == 1 && !keyEvent.isAltDown()
					&& keyEvent.isMetaDown())
				return true;
		}
		return false;
	}

	/**
	 * @see com.sun.j3d.utils.behaviors.vp.OrbitBehavior in Java3D
	 */
	public synchronized void setMinRadius(double d) {
		if (d < 0.0D) {
			throw new IllegalArgumentException(J3dUtilsI18N
					.getString("OrbitBehavior1"));
		} else {
			minRadius = d;
			return;
		}
	}

	/**
	 * @see com.sun.j3d.utils.behaviors.vp.OrbitBehavior in Java3D
	 */
	public void MinRadius(Object aobj[]) {
		if (aobj.length != 1 || !(aobj[0] instanceof Double)) {
			throw new IllegalArgumentException("MinRadius must be a Double");
		} else {
			setMinRadius(((Double) aobj[0]).doubleValue());
			return;
		}
	}

	/**
	 * @see com.sun.j3d.utils.behaviors.vp.OrbitBehavior in Java3D
	 */
	public double getMinRadius() {
		return minRadius;
	}

	/**
	 * @see com.sun.j3d.utils.behaviors.vp.OrbitBehavior in Java3D
	 */
	public void setReverseTranslate(boolean flag) {
		reverseTrans = flag;
	}

	/**
	 * @see com.sun.j3d.utils.behaviors.vp.OrbitBehavior in Java3D
	 */
	public void ReverseTranslate(Object aobj[]) {
		if (aobj.length != 1 || !(aobj[0] instanceof Boolean)) {
			throw new IllegalArgumentException(
					"ReverseTranslate must be Boolean");
		} else {
			setReverseTranslate(((Boolean) aobj[0]).booleanValue());
			return;
		}
	}

	/**
	 * @see com.sun.j3d.utils.behaviors.vp.OrbitBehavior in Java3D
	 */
	public void setReverseRotate(boolean flag) {
		reverseRotate = flag;
	}

	/**
	 * @see com.sun.j3d.utils.behaviors.vp.OrbitBehavior in Java3D
	 */
	public void ReverseRotate(Object aobj[]) {
		if (aobj.length != 1 || !(aobj[0] instanceof Boolean)) {
			throw new IllegalArgumentException("ReverseRotate must be Boolean");
		} else {
			setReverseRotate(((Boolean) aobj[0]).booleanValue());
			return;
		}
	}

	/**
	 * @see com.sun.j3d.utils.behaviors.vp.OrbitBehavior in Java3D
	 */
	public void setReverseZoom(boolean flag) {
		reverseZoom = flag;
	}

	/**
	 * @see com.sun.j3d.utils.behaviors.vp.OrbitBehavior in Java3D
	 */
	public void ReverseZoom(Object aobj[]) {
		if (aobj.length != 1 || !(aobj[0] instanceof Boolean)) {
			throw new IllegalArgumentException("ReverseZoom must be Boolean");
		} else {
			setReverseZoom(((Boolean) aobj[0]).booleanValue());
			return;
		}
	}

	/**
	 * @see com.sun.j3d.utils.behaviors.vp.OrbitBehavior in Java3D
	 */
	public synchronized void setProportionalZoom(boolean flag) {
		proportionalZoom = flag;
		if (flag)
			zoomMul = 1.0D * zoomFactor;
		else
			zoomMul = 0.01D * zoomFactor;
	}

	/**
	 * @see com.sun.j3d.utils.behaviors.vp.OrbitBehavior in Java3D
	 */
	public void ProportionalZoom(Object aobj[]) {
		if (aobj.length != 1 || !(aobj[0] instanceof Boolean)) {
			throw new IllegalArgumentException(
					"ProportionalZoom must be Boolean");
		} else {
			setProportionalZoom(((Boolean) aobj[0]).booleanValue());
			return;
		}
	} 
	
	/**
	 * This function is used to execute actions generated by the PanelButtonInteraction object
	 * If you want usethis behavior with your owner panel, ou must define 6 buttons which "+", "<", ">", "-", "^", "v" and "Centrer la vue" as label.
	 */
	public void actionPerformed(ActionEvent e) {
		if (((JButton)e.getSource()).getText().equals("+")) {
			doZoomOperations(-1);
			motion = true;
		}
		if (((JButton)e.getSource()).getText().equals("-")) {
			doZoomOperations(1);
			motion = true;
		}
		if (((JButton)e.getSource()).getText().equals("^")) {
			latitude -= rotYMul;
			motion = true;
		}
		if (((JButton)e.getSource()).getText().equals("v")) {
			latitude += rotYMul;
			motion = true;
		}
		if (((JButton)e.getSource()).getText().equals("<")) {
			longditude -= rotXMul;
			motion = true;
		}
		if (((JButton)e.getSource()).getText().equals(">")) {
			longditude += rotXMul;
			motion = true;
		}
		targetTG = this.graphUniverse.getViewingPlatform().getViewPlatformTransform();
		this.integrateTransforms();
		
		if (((JButton)e.getSource()).getText().equals("Centrer la vue")) {
			float[] barycenter = new float[3];
			if (this.attributesList != null) {
				Hashtable<String, GNode> nodes = new Hashtable<String, GNode>();
				for (int i = 0; i < this.attributesList.getElements().size(); i++) {
					Object element = this.attributesList.getElements().get(i);
					if (element instanceof GNode) {
						nodes.put(((GNode)element).getName(), ((GNode)element));
					}
				}
				if (!nodes.isEmpty()) {
					BasicFunctions.createBestView(this.graphUniverse, nodes);
					barycenter = BasicFunctions.calcBarycenter(nodes);
					this.setRotationCenter(new Point3d(barycenter[0], barycenter[1], barycenter[2]));
				} else {
					BasicFunctions.createBestView(this.graphUniverse);
					barycenter = BasicFunctions.calcBarycenter(this.graphUniverse.getGraph().getNodes());
					this.setRotationCenter(new Point3d(barycenter[0], barycenter[1], barycenter[2]));
					
				}
			} else {
				BasicFunctions.createBestView(this.graphUniverse);
				barycenter = BasicFunctions.calcBarycenter(this.graphUniverse.getGraph().getNodes());
				this.setRotationCenter(new Point3d(barycenter[0], barycenter[1], barycenter[2]));
			}
		}
	}

	public GAttributesList getAttributesList() {
		return attributesList;
	}

	public void setAttributesList(GAttributesList attributesList) {
		this.attributesList = attributesList;
	}
}