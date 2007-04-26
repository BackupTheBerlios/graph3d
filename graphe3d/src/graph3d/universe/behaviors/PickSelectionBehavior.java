package graph3d.universe.behaviors;

import graph3d.lists.GAttributesList;

import javax.media.j3d.*;
import com.sun.j3d.utils.picking.behaviors.*;
import com.sun.j3d.utils.picking.*;
import com.sun.j3d.utils.behaviors.mouse.*;

/**
 * A mouse behavior that allows user to pick and translate scene graph objects.
 * Common usage: 1. Create your scene graph. 2. Create this behavior with
 * the root and canvas. See PickRotateBehavior for more details.
 */

public class PickSelectionBehavior extends PickMouseBehavior implements MouseBehaviorCallback {
  private SelectionBehavior selection;
  private PickingCallback callback = null;
  private BranchGroup currentBg;
  private TransformGroup currentTg;

  /**
   * Creates a pick/translate behavior that waits for user mouse events for
   * the scene graph.
   * @param root   Root of your scene graph.
   * @param canvas Java 3D drawing canvas.
   * @param bounds Bounds of your scene.
 * @param _attributesList 
   **/

  public PickSelectionBehavior(BranchGroup root, Canvas3D canvas, Bounds bounds, GAttributesList _attributesList){
    super(canvas, root, bounds);
    this.selection = new SelectionBehavior(_attributesList);
    this.selection.setTransformGroup(this.currGrp);
    this.selection.setFactor(0.008);
    this.currGrp.addChild(this.selection);
    this.selection.setSchedulingBounds(bounds);
    this.setSchedulingBounds(bounds);
  }


  /**
   * Update the scene to manipulate any nodes. This is not meant to be
   * called by users. Behavior automatically calls this. You can call
   * this only if you know what you are doing.
   *
   * @param xpos Current mouse X pos.
   * @param ypos Current mouse Y pos.
   **/
  public void updateScene(int xpos, int ypos){
    BranchGroup bg;
    TransformGroup tg;

    if (!mevent.isAltDown() && !mevent.isMetaDown()){
      pickCanvas.setShapeLocation(xpos, ypos);
      PickResult pr = pickCanvas.pickClosest();
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

  /**
    * Callback method from MouseTranslate
    * This is used when the Picking callback is enabled
    */
  public void transformChanged( int type, Transform3D transform ) {
	  this.callback.transformChanged( PickingCallback.TRANSLATE, this.currentTg );
  }

  /**
    * Register the class @param callback to be called each
    * time the picked object moves
    */
  public void setupCallback( PickingCallback callback ) {
      this.callback = callback;
      if (callback==null)
    	  this.selection.setupCallback( null );
      else
    	  this.selection.setupCallback( this );
  }

}

