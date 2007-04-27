package graph3d.universe.behaviors;


import graph3d.universe.GView;

import java.awt.AWTEvent;
import java.awt.event.KeyEvent;
import java.util.Enumeration;

import javax.media.j3d.WakeupOnAWTEvent;

/**
 * This class define the behavior which is used to the keyboard.
 * With the keyboard, you can rotate the view and zoom
 * @author Erwan Daubert
 * @version 1.0
 *
 */
public class GKeyBehavior extends GOneActionBehavior {
		
	private WakeupOnAWTEvent keyEvent=new WakeupOnAWTEvent(KeyEvent.KEY_PRESSED);
	
	/**
	 * The constructor of this class
	 * @param _view the view on which you work
	 */
	public GKeyBehavior(GView _view) {
		this.setGview(_view);
	}
	
	public void initialize() {
		this.wakeupOn(this.keyEvent);
	}
		
	public void processStimulus(Enumeration criteria) {
		AWTEvent events[] = this.keyEvent.getAWTEvent();
		if (((KeyEvent)events[0]).getKeyCode()==KeyEvent.VK_LEFT) {
			this.getGview().getTransformGroup().setTransform(this.rotateX(0.1f));
		} else if (((KeyEvent)events[0]).getKeyCode()==KeyEvent.VK_RIGHT) {
			this.getGview().getTransformGroup().setTransform(this.rotateX(-0.1f));
		} else if (((KeyEvent)events[0]).getKeyCode()==KeyEvent.VK_UP) {
			this.getGview().getTransformGroup().setTransform(this.rotateY(-0.1f));
		}else if (((KeyEvent)events[0]).getKeyCode()==KeyEvent.VK_DOWN) {
			this.getGview().getTransformGroup().setTransform(this.rotateY(0.1f));
		}else if (((KeyEvent)events[0]).getKeyCode()==KeyEvent.VK_PAGE_DOWN) {
			this.getGview().getTransformGroup().setTransform(this.zoom(1.05f));
		}else if (((KeyEvent)events[0]).getKeyCode()==KeyEvent.VK_PAGE_UP) {
			this.getGview().getTransformGroup().setTransform(this.zoom(0.95f));
		}
		this.wakeupOn(this.keyEvent);
	}
}
