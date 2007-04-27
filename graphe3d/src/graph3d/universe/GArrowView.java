package graph3d.universe;

import graph3d.elements.GLink;

import javax.media.j3d.Appearance;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.GeometryArray;
import javax.media.j3d.LineArray;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Matrix4f;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.geometry.Cone;

/**
 * This class create a GArrowView.
 */
public class GArrowView extends GLinkView {

	/**
	 * This constructor is used to create a GArrowView.
	 * 
	 * @param _link
	 *            of type GLink.
	 */
	public GArrowView(GLink _link) {
		this.setLink(_link);
		this.createLine();
		this.add();
	}

	/**
	 * This function is used to create a line (with an arrow) of type LineArray.
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
	
	public void update(){
		
		float [] tabnode1=null;
		tabnode1=this.getLink().getFirstNode().getCoordonates();
		float [] tabnode2=null;
		tabnode2=this.getLink().getSecondNode().getCoordonates();
		
		Vector3f node1=new Vector3f(tabnode1[0],tabnode1[1],tabnode1[2]);
		Vector3f node2=new Vector3f(tabnode2[0],tabnode2[1],tabnode2[2]);
		
		// color - appearance pour le cone
		Appearance appearance=new Appearance();
		ColoringAttributes color=new ColoringAttributes(this.getColor(),0);
		appearance.setColoringAttributes(color);
		
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
				
			    
			    /* Pb arrow mal fixee !
			     
			    Vector3f vectPoint1=new Vector3f(point1.x,point1.y,point1.z);
			    Vector3f vectPoint2=new Vector3f(point2.x,point2.y,point2.z);
			    Vector3f barycentre=this.getBarycentre(vectPoint1,vectPoint2);
			    
			    this.createArrow(barycentre,vectPoint1,vectPoint2,appearance);*/
		
		}else{
			
				this.getLine().setCoordinate(0,
					this.getLink().getFirstNode().getCoordonates());
				this.getLine().setCoordinate(1,
					this.getLink().getSecondNode().getCoordonates());
				
				this.getLine().setColor(0, this.getColor());
				this.getLine().setColor(1, this.getColor());
				
				Vector3f barycentre=this.getBarycentre(node1,node2);
				
				this.createArrow(barycentre,node1,node2,appearance);
		}
	}

	
	/**
	 * This function is used to create a cone to do an arrow.
	 * @param node1
	 * @param node2
	 * @param barycentre
	 * @param appearance
	 */
	public void createArrow(Vector3f barycentre,Vector3f node1,Vector3f node2,Appearance appearance){

		float x=barycentre.x;
		float y=barycentre.y;
		float z=barycentre.z;
		float x2=node2.x;
		float y2=node2.y;
	    float z2=node2.z;
	    
	    // Compute the length and direction of the line
	    float deltaX = x2 - x;
	    float deltaY = y2 - y;
	    float deltaZ = z2 - z;

	    float theta = -(float) Math.atan2(deltaZ, deltaX);
	    float phi = (float) Math.atan2(deltaY, deltaX);
	    if (deltaX < 0.0f) {
	      phi = (float) Math.PI - phi;
	    }

	    // Compute a matrix to rotate a cone to point in the line's
	    // direction, then place the cone at the line's endpoint.
	    Matrix4f mat = new Matrix4f();
	    Matrix4f mat2 = new Matrix4f();
	    mat.setIdentity();

	    // Move to the endpoint of the line
	    mat2.setIdentity();
	    mat2.setTranslation(new Vector3f(x2, y2, z2));
	    mat.mul(mat2);

	    // Spin around Y
	    mat2.setIdentity();
	    mat2.rotY(theta);
	    mat.mul(mat2);

	    // Tilt up or down around Z
	    mat2.setIdentity();
	    mat2.rotZ(phi);
	    mat.mul(mat2);

	    // Tilt cone to point right
	    mat2.setIdentity();
	    mat2.rotZ(-1.571f);
	    mat.mul(mat2);
		
		Transform3D translateCone=new Transform3D();
	    
		// test pour savoir quel node est placé plus haut que l'autre 
		// permet de connaitre la translation à appliquer pour placer le cone
		if(node1.length() > node2.length()){
			translateCone.setTranslation(new Vector3f(barycentre.x,barycentre.y,barycentre.z));
		}else{
			translateCone.setTranslation(new Vector3f(-barycentre.x,-barycentre.y,-barycentre.z));
		}

		// Creation du TransformGroup qui contiendra la cone
	    TransformGroup arrowCone = new TransformGroup();

	    Transform3D trans = new Transform3D(mat);
	    
	    // translation + rotation
	    translateCone.mul(trans);
	    
	    arrowCone.setTransform(translateCone);
	    
	    arrowCone.addChild(new Cone(0.1f,0.3f,appearance));
	    
	    addChild(arrowCone);
	   
	}

	
	/**
	 * This function is used to calculate the barycenter of two nodes.
	 * @param firstNode
	 * @param secondNode
	 * @return barycentre
	 */
	public Vector3f getBarycentre(Vector3f firstNode,Vector3f secondNode){
		
		float x=(firstNode.x+secondNode.x)/2;
		float y=(firstNode.y+secondNode.y)/2;
		float z=(firstNode.z+secondNode.z)/2;
		
		Vector3f barycentre=new Vector3f(x,y,z);
		
		return barycentre;
	}

}
