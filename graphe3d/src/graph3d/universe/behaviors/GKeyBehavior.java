package graph3d.universe.behaviors;


import java.awt.AWTEvent;
import java.awt.event.KeyEvent;
import java.util.Enumeration;

import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.WakeupOnAWTEvent;
import javax.vecmath.Vector3f;

public class GKeyBehavior extends GOneActionBehavior {
		
	private WakeupOnAWTEvent keyEvent=new WakeupOnAWTEvent(KeyEvent.KEY_PRESSED);
	
	public GKeyBehavior(TransformGroup TG, Vector3f _camera, double angleX, double angleY) {
		this.setTransformGroup(TG);
		this.setCamera(_camera);
		this.setAngles(angleX,angleY);
	}
	
	public void initialize() {
		this.wakeupOn(this.keyEvent);
	}
		
	public void processStimulus(Enumeration criteria) {
		AWTEvent events[] = this.keyEvent.getAWTEvent();
		if (((KeyEvent)events[0]).getKeyCode()==KeyEvent.VK_LEFT) {
			this.getTransformGroup().setTransform(this.rotateX(0.1f));
		} else if (((KeyEvent)events[0]).getKeyCode()==KeyEvent.VK_RIGHT) {
			this.getTransformGroup().setTransform(this.rotateX(-0.1f));
		} else if (((KeyEvent)events[0]).getKeyCode()==KeyEvent.VK_UP) {
			this.getTransformGroup().setTransform(this.rotateY(-0.1f));
		}else if (((KeyEvent)events[0]).getKeyCode()==KeyEvent.VK_DOWN) {
			this.getTransformGroup().setTransform(this.rotateY(0.1f));
		}else if (((KeyEvent)events[0]).getKeyCode()==KeyEvent.VK_PAGE_DOWN) {
			this.getTransformGroup().setTransform(this.zoom(1.05f));
		}else if (((KeyEvent)events[0]).getKeyCode()==KeyEvent.VK_PAGE_UP) {
			this.getTransformGroup().setTransform(this.zoom(0.95f));
		}
		
		this.wakeupOn(this.keyEvent);
	}
}
