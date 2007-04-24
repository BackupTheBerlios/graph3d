package graph3d.universe.behaviors;


import java.awt.AWTEvent;
import java.awt.dnd.MouseDragGestureRecognizer;
import java.awt.event.MouseEvent;
import java.util.Enumeration;

import javax.media.j3d.TransformGroup;
import javax.media.j3d.WakeupOnAWTEvent;
import javax.vecmath.Vector3f;

public class GMouseBehavior extends GBehavior {

private WakeupOnAWTEvent mouseEvent=new WakeupOnAWTEvent(MouseEvent.MOUSE_DRAGGED );
	
	public GMouseBehavior(TransformGroup TG, Vector3f _camera) {
		this.setTransformGroup(TG);
		this.setCamera(_camera);
	}
	
	public void initialize() {
		this.wakeupOn(this.mouseEvent);
	}
		
	public void processStimulus(Enumeration criteria) {
		AWTEvent events[] = this.mouseEvent.getAWTEvent();
		//gestion du drag!!!!
		
		this.wakeupOn(this.mouseEvent);
	}
}
