package graph3d.universe.behaviors;


import java.awt.AWTEvent;
import java.awt.event.KeyEvent;
import java.util.Enumeration;

import javax.media.j3d.TransformGroup;
import javax.media.j3d.WakeupOnAWTEvent;
import javax.vecmath.Vector3f;

public class GKeyBehavior extends GBehavior {
		
	private WakeupOnAWTEvent keyEvent=new WakeupOnAWTEvent(KeyEvent.KEY_PRESSED);
	
	public GKeyBehavior(TransformGroup TG, Vector3f _camera) {
		this.setTransformGroup(TG);
		this.setCamera(_camera);
	}
	
	public void initialize() {
		this.wakeupOn(this.keyEvent);
	}
		
	public void processStimulus(Enumeration criteria) {
		AWTEvent events[] = this.keyEvent.getAWTEvent();
		if (((KeyEvent)events[0]).getKeyCode()==KeyEvent.VK_LEFT) {
			this.rotateX(0.1f);
		} else if (((KeyEvent)events[0]).getKeyCode()==KeyEvent.VK_RIGHT) {
			this.rotateX(-0.1f);
		} else if (((KeyEvent)events[0]).getKeyCode()==KeyEvent.VK_UP) {
			this.rotateY(-0.1f);
		}
		else if (((KeyEvent)events[0]).getKeyCode()==KeyEvent.VK_DOWN) {
			this.rotateY(0.1f);
		}
		
		this.wakeupOn(this.keyEvent);
	}
}
