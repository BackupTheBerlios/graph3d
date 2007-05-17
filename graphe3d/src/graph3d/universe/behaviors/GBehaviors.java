package graph3d.universe.behaviors;

import graph3d.elements.GGraph;
import graph3d.elements.GNode;
import graph3d.universe.BasicFunctions;
import graph3d.universe.GGrapheUniverse;

import java.awt.AWTEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.util.Enumeration;

import javax.media.j3d.Canvas3D;
import javax.media.j3d.Transform3D;
import javax.swing.JButton;
import javax.vecmath.Matrix3d;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import com.sun.j3d.internal.J3dUtilsI18N;
import com.sun.j3d.utils.behaviors.vp.ViewPlatformAWTBehavior;
import com.sun.j3d.utils.universe.ViewingPlatform;

public class GBehaviors extends ViewPlatformAWTBehavior  implements ActionListener {

	private GGrapheUniverse graphUniverse;
	
	//private Transform3D velocityTransform;

	private Transform3D longditudeTransform;

	//private Transform3D rollTransform;

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

	//private double rollAngle;

	private double startDistanceFromCenter;

	private double distanceFromCenter;

	//private final double MAX_MOUSE_ANGLE;

	//private final double ZOOM_FACTOR = 1.0D;

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

	/*public static final int REVERSE_ROTATE = 16;

	public static final int REVERSE_TRANSLATE = 32;

	public static final int REVERSE_ZOOM = 64;

	public static final int REVERSE_ALL = 112;

	public static final int STOP_ZOOM = 256;

	public static final int DISABLE_ROTATE = 512;

	public static final int DISABLE_TRANSLATE = 1024;

	public static final int DISABLE_ZOOM = 2048;

	public static final int PROPORTIONAL_ZOOM = 4096;

	private static final int ROTATE = 0;

	private static final int TRANSLATE = 1;

	private static final int ZOOM = 2;

	private static final double NOMINAL_ZOOM_FACTOR = 0.01D;

	private static final double NOMINAL_PZOOM_FACTOR = 1D;

	private static final double NOMINAL_ROT_FACTOR = 0.01D;

	private static final double NOMINAL_TRANS_FACTOR = 0.01D;*/

	private double rotXMul;

	private double rotYMul;

	private double transXMul;

	private double transYMul;

	private double zoomMul;

	public GBehaviors(Canvas3D canvas3d, GGrapheUniverse _graphUniverse) {
		super(canvas3d, ViewPlatformAWTBehavior.KEY_LISTENER | ViewPlatformAWTBehavior.MOUSE_LISTENER | ViewPlatformAWTBehavior.MOUSE_MOTION_LISTENER | ViewPlatformAWTBehavior.MOUSE_WHEEL_LISTENER);
		this.graphUniverse = _graphUniverse;
		//velocityTransform = new Transform3D();
		longditudeTransform = new Transform3D();
		//rollTransform = new Transform3D();
		latitudeTransform = new Transform3D();
		rotateTransform = new Transform3D();
		temp1 = new Transform3D();
		temp2 = new Transform3D();
		translation = new Transform3D();
		transVector = new Vector3d();
		distanceVector = new Vector3d();
		centerVector = new Vector3d();
		invertCenterVector = new Vector3d();
		longditude = 0.0D;
		latitude = 0.0D;
		//rollAngle = 0.0D;
		startDistanceFromCenter = 20D;
		distanceFromCenter = 20D;
		//MAX_MOUSE_ANGLE = Math.toRadians(3D);
		rotationCenter = new Point3d();
		rotMatrix = new Matrix3d();
		currentXfm = new Transform3D();
		mouseX = 0;
		mouseY = 0;
		rotXFactor = 1.0D;
		rotYFactor = 1.0D;
		transXFactor = 1.0D;
		transYFactor = 1.0D;
		zoomFactor = 10.0D;
		xtrans = 0.0D;
		ytrans = 0.0D;
		ztrans = 0.0D;
		zoomEnabled = true;
		rotateEnabled = true;
		translateEnabled = true;
		reverseRotate = false;
		reverseTrans = false;
		reverseZoom = false;
		stopZoom = false;
		proportionalZoom = false;
		minRadius = 0.0D;
		leftButton = 0;
		rightButton = 1;
		middleButton = 2;
		wheelZoomFactor = 50F;
		rotXMul = 0.01D * rotXFactor;
		rotYMul = 0.01D * rotYFactor;
		transXMul = 0.01D * transXFactor;
		transYMul = 0.01D * transYFactor;
		zoomMul = 0.01D * zoomFactor;
		this.createBestView();
	}

	/*public GBehaviors(Canvas3D canvas3d) {
		this(canvas3d, 0);
	}

	public GBehaviors(Canvas3D canvas3d, int i) {
		super(canvas3d, 0xb | i);
		velocityTransform = new Transform3D();
		longditudeTransform = new Transform3D();
		rollTransform = new Transform3D();
		latitudeTransform = new Transform3D();
		rotateTransform = new Transform3D();
		temp1 = new Transform3D();
		temp2 = new Transform3D();
		translation = new Transform3D();
		transVector = new Vector3d();
		distanceVector = new Vector3d();
		centerVector = new Vector3d();
		invertCenterVector = new Vector3d();
		longditude = 0.0D;
		latitude = 0.0D;
		rollAngle = 0.0D;
		startDistanceFromCenter = 20D;
		distanceFromCenter = 20D;
		MAX_MOUSE_ANGLE = Math.toRadians(3D);
		rotationCenter = new Point3d();
		rotMatrix = new Matrix3d();
		currentXfm = new Transform3D();
		mouseX = 0;
		mouseY = 0;
		rotXFactor = 1.0D;
		rotYFactor = 1.0D;
		transXFactor = 1.0D;
		transYFactor = 1.0D;
		zoomFactor = 1.0D;
		xtrans = 0.0D;
		ytrans = 0.0D;
		ztrans = 0.0D;
		zoomEnabled = true;
		rotateEnabled = true;
		translateEnabled = true;
		reverseRotate = false;
		reverseTrans = false;
		reverseZoom = false;
		stopZoom = false;
		proportionalZoom = false;
		minRadius = 0.0D;
		leftButton = 0;
		rightButton = 1;
		middleButton = 2;
		wheelZoomFactor = 50F;
		rotXMul = 0.01D * rotXFactor;
		rotYMul = 0.01D * rotYFactor;
		transXMul = 0.01D * transXFactor;
		transYMul = 0.01D * transYFactor;
		zoomMul = 0.01D * zoomFactor;
		if ((i & 0x200) != 0)
			rotateEnabled = false;
		if ((i & 0x800) != 0)
			zoomEnabled = false;
		if ((i & 0x400) != 0)
			translateEnabled = false;
		if ((i & 0x20) != 0)
			reverseTrans = true;
		if ((i & 0x10) != 0)
			reverseRotate = true;
		if ((i & 0x40) != 0)
			reverseZoom = true;
		if ((i & 0x100) != 0)
			stopZoom = true;
		if ((i & 0x1000) != 0) {
			proportionalZoom = true;
			zoomMul = 1.0D * zoomFactor;
		}
	}*/

	protected synchronized void processAWTEvents(AWTEvent aawtevent[]) {
		this.motion = false;
		for (int i = 0; i < aawtevent.length; i++) {
			if (aawtevent[i] instanceof MouseEvent) {
				processMouseEvent((MouseEvent) aawtevent[i]);
			} else if (aawtevent[i] instanceof KeyEvent) {
				processKeyEvent((KeyEvent) aawtevent[i]);
			}
		}

	}

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

	public void setViewingPlatform(ViewingPlatform viewingplatform) {
		super.setViewingPlatform(viewingplatform);
		if (viewingplatform != null) {
			resetView();
			integrateTransforms();
		}
	}

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
		//this.createBestView();
	}

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

	public synchronized void setRotationCenter(Point3d point3d) {
		rotationCenter.x = point3d.x;
		rotationCenter.y = point3d.y;
		rotationCenter.z = point3d.z;
		centerVector.set(rotationCenter);
	}

	public void RotationCenter(Object aobj[]) {
		if (aobj.length != 1 || !(aobj[0] instanceof Point3d)) {
			throw new IllegalArgumentException(
					"RotationCenter must be a single Point3d");
		} else {
			setRotationCenter((Point3d) aobj[0]);
			return;
		}
	}

	public void getRotationCenter(Point3d point3d) {
		point3d.x = rotationCenter.x;
		point3d.y = rotationCenter.y;
		point3d.z = rotationCenter.z;
	}

	public synchronized void setRotFactors(double d, double d1) {
		rotXFactor = d;
		rotYFactor = d1;
		rotXMul = 0.01D * d;
		rotYMul = 0.01D * d1;
	}

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

	public synchronized void setRotXFactor(double d) {
		rotXFactor = d;
		rotXMul = 0.01D * d;
	}

	public void RotXFactor(Object aobj[]) {
		if (aobj.length != 1 || !(aobj[0] instanceof Double)) {
			throw new IllegalArgumentException("RotXFactor must be a Double");
		} else {
			setRotXFactor(((Double) aobj[0]).doubleValue());
			return;
		}
	}

	public synchronized void setRotYFactor(double d) {
		rotYFactor = d;
		rotYMul = 0.01D * d;
	}

	public void RotYFactor(Object aobj[]) {
		if (aobj.length != 1 || !(aobj[0] instanceof Double)) {
			throw new IllegalArgumentException("RotYFactor must be a Double");
		} else {
			setRotYFactor(((Double) aobj[0]).doubleValue());
			return;
		}
	}

	public synchronized void setTransFactors(double d, double d1) {
		transXFactor = d;
		transYFactor = d1;
		transXMul = 0.01D * d;
		transYMul = 0.01D * d1;
	}

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

	public synchronized void setTransXFactor(double d) {
		transXFactor = d;
		transXMul = 0.01D * d;
	}

	public void TransXFactor(Object aobj[]) {
		if (aobj.length != 1 || !(aobj[0] instanceof Double)) {
			throw new IllegalArgumentException("TransXFactor must be a Double");
		} else {
			setTransXFactor(((Double) aobj[0]).doubleValue());
			return;
		}
	}

	public synchronized void setTransYFactor(double d) {
		transYFactor = d;
		transYMul = 0.01D * d;
	}

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

	public void ZoomFactor(Object aobj[]) {
		if (aobj.length != 1 || !(aobj[0] instanceof Double)) {
			throw new IllegalArgumentException("ZoomFactor must be a Double");
		} else {
			setZoomFactor(((Double) aobj[0]).doubleValue());
			return;
		}
	}

	public double getRotXFactor() {
		return rotXFactor;
	}

	public double getRotYFactor() {
		return rotYFactor;
	}

	public double getTransXFactor() {
		return transXFactor;
	}

	public double getTransYFactor() {
		return transYFactor;
	}

	public double getZoomFactor() {
		return zoomFactor;
	}

	public synchronized void setRotateEnable(boolean flag) {
		rotateEnabled = flag;
	}

	public void RotateEnable(Object aobj[]) {
		if (aobj.length != 1 || !(aobj[0] instanceof Boolean)) {
			throw new IllegalArgumentException("RotateEnable must be Boolean");
		} else {
			setRotateEnable(((Boolean) aobj[0]).booleanValue());
			return;
		}
	}

	public synchronized void setZoomEnable(boolean flag) {
		zoomEnabled = flag;
	}

	public void ZoomEnable(Object aobj[]) {
		if (aobj.length != 1 || !(aobj[0] instanceof Boolean)) {
			throw new IllegalArgumentException("ZoomEnable must be Boolean");
		} else {
			setZoomEnable(((Boolean) aobj[0]).booleanValue());
			return;
		}
	}

	public synchronized void setTranslateEnable(boolean flag) {
		translateEnabled = flag;
	}

	public void TranslateEnable(Object aobj[]) {
		if (aobj.length != 1 || !(aobj[0] instanceof Boolean)) {
			throw new IllegalArgumentException(
					"TranslateEnable must be Boolean");
		} else {
			setTranslateEnable(((Boolean) aobj[0]).booleanValue());
			return;
		}
	}

	public boolean getRotateEnable() {
		return rotateEnabled;
	}

	public boolean getZoomEnable() {
		return zoomEnabled;
	}

	public boolean getTranslateEnable() {
		return translateEnabled;
	}

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
	
	boolean zoom(KeyEvent keyEvent) {
		if (zoomEnabled) {
			if (keyEvent.getKeyCode() == KeyEvent.VK_PAGE_DOWN || keyEvent.getKeyCode() == KeyEvent.VK_PAGE_UP)
				return true;
		}
		return false;
	}

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

	public synchronized void setMinRadius(double d) {
		if (d < 0.0D) {
			throw new IllegalArgumentException(J3dUtilsI18N
					.getString("OrbitBehavior1"));
		} else {
			minRadius = d;
			return;
		}
	}

	public void MinRadius(Object aobj[]) {
		if (aobj.length != 1 || !(aobj[0] instanceof Double)) {
			throw new IllegalArgumentException("MinRadius must be a Double");
		} else {
			setMinRadius(((Double) aobj[0]).doubleValue());
			return;
		}
	}

	public double getMinRadius() {
		return minRadius;
	}

	public void setReverseTranslate(boolean flag) {
		reverseTrans = flag;
	}

	public void ReverseTranslate(Object aobj[]) {
		if (aobj.length != 1 || !(aobj[0] instanceof Boolean)) {
			throw new IllegalArgumentException(
					"ReverseTranslate must be Boolean");
		} else {
			setReverseTranslate(((Boolean) aobj[0]).booleanValue());
			return;
		}
	}

	public void setReverseRotate(boolean flag) {
		reverseRotate = flag;
	}

	public void ReverseRotate(Object aobj[]) {
		if (aobj.length != 1 || !(aobj[0] instanceof Boolean)) {
			throw new IllegalArgumentException("ReverseRotate must be Boolean");
		} else {
			setReverseRotate(((Boolean) aobj[0]).booleanValue());
			return;
		}
	}

	public void setReverseZoom(boolean flag) {
		reverseZoom = flag;
	}

	public void ReverseZoom(Object aobj[]) {
		if (aobj.length != 1 || !(aobj[0] instanceof Boolean)) {
			throw new IllegalArgumentException("ReverseZoom must be Boolean");
		} else {
			setReverseZoom(((Boolean) aobj[0]).booleanValue());
			return;
		}
	}

	public synchronized void setProportionalZoom(boolean flag) {
		proportionalZoom = flag;
		if (flag)
			zoomMul = 1.0D * zoomFactor;
		else
			zoomMul = 0.01D * zoomFactor;
	}

	public void ProportionalZoom(Object aobj[]) {
		if (aobj.length != 1 || !(aobj[0] instanceof Boolean)) {
			throw new IllegalArgumentException(
					"ProportionalZoom must be Boolean");
		} else {
			setProportionalZoom(((Boolean) aobj[0]).booleanValue());
			return;
		}
	} 
	
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
			this.createBestView();
		}
	}
	
	private void createBestView() {
		
		float[] eyePosition = new float[3];
		float fieldOfView = (float)this.graphUniverse.getViewingPlatform().getViewers()[0].getView().getFieldOfView();
		
		float minX = 0;
		float maxX = 0;
		float minY = 0;
		float maxY = 0;
		float minZ = 0;
		float maxZ = 0;
		
		Enumeration<String> keys = this.graphUniverse.getGraph().getNodes().keys();
		boolean first = true;
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			GNode node = this.graphUniverse.getGraph().getNode(key);
			if (first) { // if it's the first time which the part is executed.
				minX = node.getCoordonnateX();
				maxX = node.getCoordonnateX();
				minY = node.getCoordonnateY();
				maxY = node.getCoordonnateY();
				minZ = node.getCoordonnateZ();
				maxZ = node.getCoordonnateZ();
				first = false;
			} else if (minX > node.getCoordonnateX()) {
				minX = node.getCoordonnateX();
			} else if (maxX < node.getCoordonnateX()) {
				maxX = node.getCoordonnateX();
			}
			if (minY > node.getCoordonnateY()) {
				minY = node.getCoordonnateY();
			} else if (maxY < node.getCoordonnateY()) {
				maxY = node.getCoordonnateY();
			}
			if (minZ > node.getCoordonnateZ()) {
				minZ = node.getCoordonnateZ();
			} else if (maxZ < node.getCoordonnateZ()) {
				maxZ = node.getCoordonnateZ();
			}
		}
		
		//construction des points extrèmes qui sont le plus proche de la caméra
		//si ces points passent dans la vue les autres points existant aussi.
		float[] xyZ = new float [] {minX, minY, maxZ};
		float[] xYZ = new float [] {minX, maxY, maxZ};
		//float[] XYZ = new float [] {maxX, maxY, maxZ}; ce point n'est pas utilsé d'où le commentaire
		float[] XyZ = new float [] {maxX, minY, maxZ};
		
		
		//calcul du barycentre de ces points pour connaitre X et Y que l'on recherche pour la caméra
		float[] clippingBarycenter = new float[] {(minX + maxX) / 2, (minY + maxY) / 2, maxZ};
		
		//calcul de la distance nécessaire pour voir les 4 points
		//cette distance correspond à la distance entre le barycentre précédemment calculé et le point où doit se situer la caméra.
		// en effet :
		//
		// *    E    D     *
		//	*     C       *
		//	 *           *
		//    * A  M  B *
		//	   *       *
		//	    *     *
		//       *   *
		//        * *
		//		   C
		//
		// si A et B sont contenu dans le champ de vision alors obligatoirement, 
		//les points possédant des coordonnées qui ne sont pas supérieurs seront aussi présent dans le champ de vision
		
		//la base correspond à la longueur entre le barycentre et l'extrémité du champ de vision.
		// cette extrémité se trouve sur l'un des 4 côté que forme les 4 points précédemment calculés.
		
		//sachant que les 4 points qui ont été calculés représente le sommet d'un rectangle
		//la base du triangle n'est pas obligatoirement égale entre tous les côtés.
		//C'est pourquoi il faut calculer deux bases et prendre la plus longue.
		
		//première base
		float[] base1 = new float[3] ;
		//calcul de X de la base
		base1[0] = xyZ[0] + ((xYZ[0] - xyZ[0]) / 2);
		//calcul de Y de la base
		base1[1] = xyZ[1] + ((xYZ[1] - xyZ[1]) / 2);
		//calcul de Z de la base
		base1[2] = xyZ[2] + ((xYZ[2] - xyZ[2]) / 2);
		
		//second base
		float[] base2 = new float[3] ;
		//calcul de X de la base
		base2[0] = xyZ[0] + ((XyZ[0] - xyZ[0]) / 2);
		//calcul de Y de la base
		base2[1] = xyZ[1] + ((XyZ[1] - xyZ[1]) / 2);
		//calcul de Z de la base
		base2[2] = xyZ[2] + ((XyZ[2] - xyZ[2]) / 2);
		
		//calcul de la longueur de la plus longue base
		float lengthBetween = 0;
		if (BasicFunctions.getLengthBetween(clippingBarycenter, base1) > BasicFunctions.getLengthBetween(clippingBarycenter, base2)) {
			lengthBetween = BasicFunctions.getLengthBetween(clippingBarycenter, base1);
		} else {
			lengthBetween = BasicFunctions.getLengthBetween(clippingBarycenter, base2);
		}
		
		float length = (float)(lengthBetween / Math.tan(fieldOfView/2));
		
		
		eyePosition[0] = clippingBarycenter[0];
		eyePosition[1] = clippingBarycenter[1];
		eyePosition[2] = clippingBarycenter[2] + length + 2;// ajout d'une marge par rapport au rayon des spheres
		
		//définition de la vue
		Transform3D eye = new Transform3D();
		eye.setTranslation(new Vector3f(eyePosition));
		this.graphUniverse.getViewingPlatform().getViewPlatformTransform().setTransform(eye);
		

	}
}