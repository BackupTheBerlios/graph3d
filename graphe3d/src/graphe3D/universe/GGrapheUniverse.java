package graphe3D.universe;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.VirtualUniverse;
import javax.media.j3d.Locale;

/*
 * create : GGrapheUniverse():GGrapheUniverse
 * create SceneGraphe():void
 * addGNode():void
 * addGLink():void
 * deleteGNode():void
 * deleteGLink():void
 * zoom():void
 * rotate():void
 * zoom(zoom:float):void
 * rotate(angle:float):void
 * collision():boolean
 * getCanvas():Canvas3D
 * addMouseListener():void
 * addKeyListener():void
 */
public class GGrapheUniverse extends VirtualUniverse{
	protected BranchGroup scene=null;
	protected Canvas3D canvas=null;

	/**
	 * create : GGrapheUniverse():GGrapheUniverse
	 */
	public GGrapheUniverse(){
		
	}
	/*
	 * create SceneGraphe():void
	 */
	public void SceneGraphe(){
		scene=new BranchGroup();
	}
	/*
	 * getCanvas():Canvas3D
	 */
	public Canvas3D getCanvas(){
		return canvas;
	}
	/*
	 * addGNode(TransformGroup node):void
	 */
	public void addGNode(TransformGroup node){
		scene.addChild(node);
	}
	/*
	 * addGLink(TransformGroup link):void
	 */
	public void addGLink(TransformGroup link){
		scene.addChild(link);
	}
	/*
	 * deleteGNode(TransformGroup node):void
	 */
	public void deleteGNode(GNodeView gnode){
		TransformGroup node=(TransformGroup) gnode;
		scene.removeChild(((TransformGroup) node));
	}
	/*
	 * deleteGLink(TransformGroup link):void
	 */
	public void deleteGLink(TransformGroup link){
		scene.removeChild(link);
	}
	/*
	 * zoom():void
	 */
	public void zoom(){
		
	}
	/*
	 * zoom():void
	 */
	public void zoom(float zoom){
		
	}
	/*
	 * rotate():void
	 */
	public void rotate(){
		
	}
	/*
	 * rotate():void
	 */
	public void rotate(float angle){
		
	}
	/*
	 * collision():boolean
	 */
	public boolean collision(){
		boolean flag=false;
		return flag;
	}
	/*
	 * addMouseListener():void
	 */
	public void addMouseListener(){
		
	}
	/*
	 * addKeyListener():void
	 */
	public void addKeyListener(){
		
	}

}
