package graph3d.universe.behaviors;

import graph3d.lists.GAttributesList;

import javax.media.j3d.*;
import com.sun.j3d.utils.picking.behaviors.*;
import com.sun.j3d.utils.picking.*;
import com.sun.j3d.utils.behaviors.mouse.*;

/**
 * This class define the behavior when you select an element into the 3D view.
 * This class allow you to select a specific element into the view
 * @author Erwan Daubert
 * @version 1.0
 *
 */
public class PickSelectionBehavior extends PickMouseBehavior implements MouseBehaviorCallback {
  private SelectionBehavior selection;
  private PickingCallback callback = null;
  private BranchGroup currentBg;
  private TransformGroup currentTg;

  /**
   * The constructor of this class
   * @param root the BranchGroup which define all the scene
   * @param canvas the Canvas3D on which you click to select an element
   * @param bounds the Bounds where this behavior is useful
   * @param _attributesList the GAttributesList that you can be update when you select an element
   */
  public PickSelectionBehavior(BranchGroup root, Canvas3D canvas, Bounds bounds, GAttributesList _attributesList){
    super(canvas, root, bounds);
    this.selection = new SelectionBehavior(_attributesList);
    this.selection.setTransformGroup(this.currGrp);
    this.currGrp.addChild(this.selection);
    this.selection.setSchedulingBounds(bounds);
    this.setSchedulingBounds(bounds);
  }

  public void updateScene(int xpos, int ypos){
    BranchGroup bg;
    TransformGroup tg;

    if (!this.mevent.isAltDown() && !this.mevent.isMetaDown()){
      this.pickCanvas.setShapeLocation(xpos, ypos);
      PickResult pr = this.pickCanvas.pickClosest();
      if ((pr != null)) {
    	  if (pr.getNode(PickResult.SHAPE3D).getParent().getParent() instanceof TransformGroup) {
    		  bg = (BranchGroup)pr.getNode(PickResult.SHAPE3D).getParent().getParent().getParent();
    		  tg = (TransformGroup)pr.getNode(PickResult.SHAPE3D).getParent().getParent();
    		  if (bg != null && (tg.getCapability(TransformGroup.ALLOW_TRANSFORM_READ)) && (tg.getCapability(TransformGroup.ALLOW_TRANSFORM_WRITE))) {
    			  this.currentBg = bg;
    			  this.currentTg = tg;
            	  this.selection.setBranchGroup(this.currentBg);
            	  this.selection.setTransformGroup(this.currentTg);
            	  this.selection.wakeup();
    		  }
    	  }else {
    		  bg = (BranchGroup)pr.getNode(PickResult.SHAPE3D).getParent().getParent();
			  tg = (TransformGroup)pr.getNode(PickResult.SHAPE3D).getParent();
    		  if (bg != null && (tg.getCapability(TransformGroup.ALLOW_TRANSFORM_READ)) && (tg.getCapability(TransformGroup.ALLOW_TRANSFORM_WRITE))) {
            	  this.currentBg = bg;
            	  this.currentTg = tg;
            	  this.selection.setBranchGroup(this.currentBg);
            	  this.selection.setTransformGroup(this.currentTg);
            	  this.selection.wakeup();
    		  }
    	  }
      } else if (this.callback!=null) {
    	  this.callback.transformChanged( PickingCallback.NO_PICK, null );
      }
    }
}

  public void transformChanged( int type, Transform3D transform ) {
	  this.callback.transformChanged( PickingCallback.TRANSLATE, this.currentTg );
  }

  /**
 * @param callback
 */
public void setupCallback( PickingCallback callback ) {
      this.callback = callback;
      if (callback==null)
    	  this.selection.setupCallback( null );
      else
    	  this.selection.setupCallback( this );
  }

}

