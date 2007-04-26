package graph3d.universe.behaviors;

import graph3d.lists.GAttributesList;
import graph3d.universe.GLinkView;
import graph3d.universe.GNodeView;

import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.util.Enumeration;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.WakeupCriterion;
import javax.media.j3d.WakeupOnAWTEvent;
import javax.media.j3d.WakeupOnBehaviorPost;

import com.sun.j3d.utils.behaviors.mouse.MouseBehavior;
import com.sun.j3d.utils.behaviors.mouse.MouseBehaviorCallback;

/**
 * MouseTranslate is a Java3D behavior object that lets users control the
 * translation (X, Y) of an object via a mouse drag motion with the third
 * mouse button (alt-click on PC). See MouseRotate for similar usage info.
 */

public class SelectionBehavior extends MouseBehavior {

    double x_factor = .02;
    double y_factor = .02;

    private MouseBehaviorCallback callback = null;
    private GAttributesList attributesList;
    private BranchGroup   branchGroup;

    /**
     * Creates a mouse translate behavior given the transform group.
     * @param transformGroup The transformGroup to operate on.
     */
    public SelectionBehavior(TransformGroup transformGroup, GAttributesList _attributesList) {
	super(transformGroup);
	this.attributesList = _attributesList;
    }

    /**
     * Creates a default translate behavior.
     */
    public SelectionBehavior(GAttributesList _attributesList){
	super(0);
	this.attributesList = _attributesList;
    }

    /**
     * Creates a translate behavior.
     * Note that this behavior still needs a transform
     * group to work on (use setTransformGroup(tg)) and
     * the transform group must add this behavior.
     * @param flags
     */
    public SelectionBehavior(int flags, GAttributesList _attributesList) {
	super(flags);
	this.attributesList = _attributesList;
    }

    /**
     * Creates a translate behavior that uses AWT listeners and behavior
     * posts rather than WakeupOnAWTEvent.  The behavior is added to the
     * specified Component. A null component can be passed to specify
     * the behavior should use listeners.  Components can then be added
     * to the behavior with the addListener(Component c) method.
     * @param c The Component to add the MouseListener
     * and MouseMotionListener to.
     * @since Java 3D 1.2.1
     */
    public SelectionBehavior(Component c, GAttributesList _attributesList) {
	super(c, 0);
	this.attributesList = _attributesList;
    }

    /**
     * Creates a translate behavior that uses AWT listeners and behavior
     * posts rather than WakeupOnAWTEvent.  The behaviors is added to
     * the specified Component and works on the given TransformGroup.
     * A null component can be passed to specify the behavior should use
     * listeners.  Components can then be added to the behavior with the
     * addListener(Component c) method.
     * @param c The Component to add the MouseListener and
     * MouseMotionListener to.
     * @param transformGroup The TransformGroup to operate on.
     * @since Java 3D 1.2.1
     */
    public SelectionBehavior(Component c, TransformGroup transformGroup, GAttributesList _attributesList) {
	super(c, transformGroup);
	this.attributesList = _attributesList;
    }

    /**
     * Creates a translate behavior that uses AWT listeners and behavior
     * posts rather than WakeupOnAWTEvent.  The behavior is added to the
     * specified Component.  A null component can be passed to specify
     * the behavior should use listeners.  Components can then be added to
     * the behavior with the addListener(Component c) method.
     * Note that this behavior still needs a transform
     * group to work on (use setTransformGroup(tg)) and the transform
     * group must add this behavior.
     * @param flags interesting flags (wakeup conditions).
     * @since Java 3D 1.2.1
     */
    public SelectionBehavior(Component c, int flags, GAttributesList _attributesList) {
	super(c, flags);
	this.attributesList = _attributesList;
    }

    public void initialize() {
	super.initialize();
	if ((flags & INVERT_INPUT) == INVERT_INPUT) {
	    invert = true;
	    x_factor *= -1;
	    y_factor *= -1;
	}
    }

    /**
     * Return the x-axis movement multipler.
     **/
    public double getXFactor() {
	return x_factor;
    }

    /**
     * Return the y-axis movement multipler.
     **/
    public double getYFactor() {
	return y_factor;
    }

    /**
     * Set the x-axis amd y-axis movement multipler with factor.
     **/
    public void setFactor( double factor) {
	x_factor = y_factor = factor;
    }

    /**
     * Set the x-axis amd y-axis movement multipler with xFactor and yFactor
     * respectively.
     **/
    public void setFactor( double xFactor, double yFactor) {
	x_factor = xFactor;
	y_factor = yFactor;
    }

    public void processStimulus (Enumeration criteria) {
	WakeupCriterion wakeup;
	AWTEvent[] events;
 	MouseEvent evt;
// 	int id;
// 	int dx, dy;

	while (criteria.hasMoreElements()) {
	    wakeup = (WakeupCriterion) criteria.nextElement();

	    if (wakeup instanceof WakeupOnAWTEvent) {
		events = ((WakeupOnAWTEvent)wakeup).getAWTEvent();
		if (events.length > 0) {
		    evt = (MouseEvent) events[events.length-1];
		    doProcess(evt);
		}
	    }/*

	    else if (wakeup instanceof WakeupOnBehaviorPost) {
		while (true) {
		    // access to the queue must be synchronized
		    synchronized (mouseq) {
			if (mouseq.isEmpty()) break;
			evt = (MouseEvent)mouseq.remove(0);
			// consolodate MOUSE_DRAG events
			while ((evt.getID() == MouseEvent.MOUSE_DRAGGED) &&
			       !mouseq.isEmpty() &&
			       (((MouseEvent)mouseq.get(0)).getID() ==
				MouseEvent.MOUSE_DRAGGED)) {
			    evt = (MouseEvent)mouseq.remove(0);
			}
		    }
		    //doProcess(evt);
		}
	    }*/

	}
 	wakeupOn(mouseCriterion);
    }

    void doProcess(MouseEvent evt) {
	int id;
	//processMouseEvent(evt);
	id = evt.getID();
	if (id == MouseEvent.MOUSE_PRESSED) {
		if (!evt.isControlDown()) {
			this.attributesList.removeAll();
			if (this.branchGroup instanceof GNodeView) {
				System.out.println("node " + ((GNodeView)this.branchGroup).getNode().getName());
				this.attributesList.add(((GNodeView)this.branchGroup).getNode());
			} else if (this.branchGroup instanceof GLinkView) {
				System.out.println("link " + ((GLinkView)this.branchGroup).getLink().getName());
				this.attributesList.add(((GLinkView)this.branchGroup).getLink());
			}
		} else {
			if (this.branchGroup instanceof GNodeView) {
				System.out.println("node " + ((GNodeView)this.branchGroup).getNode().getName());
				this.attributesList.add(((GNodeView)this.branchGroup).getNode());
			} else if (this.branchGroup instanceof GLinkView) {
				System.out.println("link " + ((GLinkView)this.branchGroup).getLink().getName());
				this.attributesList.add(((GLinkView)this.branchGroup).getLink());
			}
		}
	}
    }

    /**
     * Users can overload this method  which is called every time
     * the Behavior updates the transform
     *
     * Default implementation does nothing
     */
    public void transformChanged( Transform3D transform ) {
    }

    /**
     * The transformChanged method in the callback class will
     * be called every time the transform is updated
     */
    public void setupCallback( MouseBehaviorCallback callback ) {
	this.callback = callback;
    }
    
    public void setBranchGroup(BranchGroup _branchGroup) {
    	this.branchGroup = _branchGroup;
    }
}

