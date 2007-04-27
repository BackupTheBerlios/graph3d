package graph3d.universe;

import graph3d.elements.GLink;

import javax.media.j3d.GeometryArray;
import javax.media.j3d.LineArray;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

/**
 * This class create a GBridgeView.
 */
public class GBridgeView extends GLinkView {

	/**
	 * This constructor is used to create a GBridgeView.
	 * 
	 * @param _link
	 *            of type GLink.
	 */
	public GBridgeView(GLink _link) {
		this.setLink(_link);
		this.createLine();
		this.add();

	}

	/**
	 * This function is used to create a line of type LineArray.
	 */
	private void createLine() {
		
		if(this.getLink().getFirstNode().getCoordonates() == this.getLink().getSecondNode().getCoordonates()){

			this.setLine(new LineArray(6, GeometryArray.COORDINATES
					| GeometryArray.COLOR_3));
			this.update();
			
		}else{
			
			this.setLine(new LineArray(2, GeometryArray.COORDINATES
					| GeometryArray.COLOR_3));
			this.update();		
		}
	}

	public void update() {
		
		float [] tabnode1=null;
		tabnode1=this.getLink().getFirstNode().getCoordonates();
		
		Vector3f node1=new Vector3f(tabnode1[0],tabnode1[1],tabnode1[2]);
		
		if(this.getLink().getFirstNode().getCoordonates() == this.getLink().getSecondNode().getCoordonates()){
			
				Point3f point1 = new Point3f (node1.x+1f,node1.y,node1.z+1);
				Point3f point2 = new Point3f (node1.x,node1.y-1f,node1.z-1);

				this.getLine().setCoordinate(0,
					this.getLink().getFirstNode().getCoordonates());
				this.getLine().setCoordinate(1,point1);
				this.getLine().setCoordinate(2,point1);
				this.getLine().setCoordinate(3,point2);
				this.getLine().setCoordinate(4,point2);				
				this.getLine().setCoordinate(5,
						this.getLink().getFirstNode().getCoordonates());
				
				this.getLine().setColor(0, this.getColor());
				this.getLine().setColor(1, this.getColor());
				this.getLine().setColor(2, this.getColor());
				this.getLine().setColor(3, this.getColor());				
				this.getLine().setColor(4, this.getColor());
				this.getLine().setColor(5, this.getColor());
				
		}else{
			
			
			this.getLine().setCoordinate(0,
					this.getLink().getFirstNode().getCoordonates());
			this.getLine().setCoordinate(1,
					this.getLink().getSecondNode().getCoordonates());
			
			this.getLine().setColor(0, this.getColor());
			this.getLine().setColor(1, this.getColor());
		}

	}

}
