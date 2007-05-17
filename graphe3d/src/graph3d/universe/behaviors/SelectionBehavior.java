package graph3d.universe.behaviors;

import graph3d.lists.GAttributesList;
import graph3d.universe.GLinkView;
import graph3d.universe.GNodeView;

import java.awt.AWTEvent;
import java.awt.event.MouseEvent;
import java.util.Enumeration;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Transform3D;
import javax.media.j3d.WakeupCriterion;
import javax.media.j3d.WakeupOnAWTEvent;

import com.sun.j3d.utils.behaviors.mouse.MouseBehavior;
import com.sun.j3d.utils.behaviors.mouse.MouseBehaviorCallback;

/**
 * This class define the behavior when you select an element into the 3D view
 * @author Erwan Daubert
 * @version 1.0
 *
 */
public class SelectionBehavior extends MouseBehavior {

    private MouseBehaviorCallback callback = null;
    private GAttributesList attributesList;
    private BranchGroup   branchGroup;
    
    /**
     * The constructor of this class
     * @param _attributesList the GAttributeList which you want update
     */
    public SelectionBehavior(GAttributesList _attributesList){
	super(0);
	this.attributesList = _attributesList;
    }

    public void initialize() {
	super.initialize();
    }

    public void processStimulus (Enumeration criteria) {
	WakeupCriterion wakeup;
	AWTEvent[] events;
 	MouseEvent evt;

	while (criteria.hasMoreElements()) {
	    wakeup = (WakeupCriterion) criteria.nextElement();

	    if (wakeup instanceof WakeupOnAWTEvent) {
		events = ((WakeupOnAWTEvent)wakeup).getAWTEvent();
		if (events.length > 0) {
		    evt = (MouseEvent) events[events.length-1];
		    doProcess(evt);
		}
	    }

	}
 	wakeupOn(this.mouseCriterion);
    }

    private void doProcess(MouseEvent evt) {
	int id;
	id = evt.getID();
	if (id == MouseEvent.MOUSE_PRESSED) {
		if (this.branchGroup != null && this.getTransformGroup() != null) {
			if (!evt.isControlDown()) {
				this.attributesList.removeAll();
				if (this.branchGroup instanceof GNodeView) {
					this.attributesList.add(((GNodeView)this.branchGroup).getNode());
				} else if (this.branchGroup instanceof GLinkView) {
					this.attributesList.add(((GLinkView)this.branchGroup).getLink());
				}
			} else {
				if (this.branchGroup instanceof GNodeView) {
					this.attributesList.add(((GNodeView)this.branchGroup).getNode());
				} else if (this.branchGroup instanceof GLinkView) {
					this.attributesList.add(((GLinkView)this.branchGroup).getLink());
				}
			}
		}
	}
	  this.setBranchGroup(null);
	  this.setTransformGroup(null);
    }

    /**
     * Users can overload this method  which is called every time
     * the Behavior updates the transform
     *
     * Default implementation does nothing
     */
    public void transformChanged( Transform3D transform ) {}

    /**
     * The transformChanged method in the callback class will
     * be called every time the transform is updated
     * @param callback 
     */
    public void setupCallback( MouseBehaviorCallback callback ) {
	this.callback = callback;
    }
    
    /**
     * The setter of the BranchGroup
     * @param _branchGroup the new BranchGroup
     */
    public void setBranchGroup(BranchGroup _branchGroup) {
    	this.branchGroup = _branchGroup;
    }
}

