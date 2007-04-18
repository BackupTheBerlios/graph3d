package graph3d.universe;

import graph3d.elements.GGraph;
import graph3d.elements.GLink;
import graph3d.elements.GNode;


import java.util.Enumeration;
import java.util.Hashtable;

import java.awt.Color;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.Locale;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.VirtualUniverse;
import javax.vecmath.Vector3f;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingBox;
import javax.media.j3d.BoundingSphere;
import javax.vecmath.Point3d;

import com.sun.j3d.utils.behaviors.keyboard.KeyNavigatorBehavior;
import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.behaviors.mouse.MouseTranslate;
import com.sun.j3d.utils.behaviors.mouse.MouseZoom;

/**
 * This class define a GGrapheUniverse.
 */
public class GGrapheUniverse extends VirtualUniverse{
	
	private Locale locale;
	private GGraph graph;
	private GView view;
	private BranchGroup sceneCompiled;//peut-être pas nécessaire si possibilité de modifier la scene même si elle est compilée
	private Hashtable<String, TransformGroup> ComponentsView;
	
	private BranchGroup scene;

	/**
	 * This constructor is used to create a GGrapheUniverse.
	 * @param _graph of type GGraph.
	 */
	public GGrapheUniverse(GGraph _graph){
		this.locale = new Locale(this);
		this.graph = _graph;
		this.ComponentsView = new Hashtable<String, TransformGroup>();
		this.createView();
		this.createScene();
	}
	
	/**
	 * This function is used to create the scene.
	 */
	private void createScene() {
		this.scene = new BranchGroup();
		
		Hashtable<String, GNode> nodes = this.graph.getNodes();
		Enumeration<String> keys = nodes.keys();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			this.addGNode(this.graph.getNode(key));
		}
		Hashtable<String, GLink> links = this.graph.getLinks();
		keys = links.keys();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			this.addGLink(links.get(key));
		}
	}
	/**
	 * This function is used to create the view.
	 */
	private void createView() {
		this.view = new GView();
		this.createBestView();
	}
	
	private void createBestView() {		
		float[] bestPointToSee = new float[3];
		float fieldOfView = this.view.getFieldOfView();
	
		
		float min=0, max = 0, minZ = 0, maxZ = 0;
		
		Enumeration<String> keys = this.graph.getNodes().keys();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			GNode node = this.graph.getNode(key);
			
			if (min > node.getCoordonnateX()) {
				min = node.getCoordonnateX();
			} else if (max < node.getCoordonnateX()) {
				max = node.getCoordonnateX();
			}
			if (min > node.getCoordonnateY()) {
				min = node.getCoordonnateY();
			} else if (max < node.getCoordonnateY()) {
				max = node.getCoordonnateY();
			}
			if (minZ > node.getCoordonnateZ()) {
				minZ = node.getCoordonnateZ();
			} else if (maxZ < node.getCoordonnateZ()) {
				maxZ = node.getCoordonnateZ();
			}
		}
		
		//construction des points extrèmes qui sont le plus proche de la caméra
		//si ces points passent dans la vue les autres points existant aussi.
		float[] xyZ = new float [] {min, min, maxZ};
		float[] xYZ = new float [] {min, max, maxZ};
		//float[] XYZ = new float [] {maxX, maxX, maxZ};
		//float[] XyZ = new float [] {maxX, minX, maxZ};
		//les deux derniers points ne sont pas utiles d'où le commentaire
		
		//calcul du barycentre de ces points pour connaitre X et Y que l'on recherche pour la caméra
		float[] barycenter = new float[] {(min + max) / 2, (min + max) / 2, maxZ};
		
		//calcul de la distance nécessaire pour voir les 4 points
		//cette distance correspond à la distance entre le barycentre précédemment calculé et le point où doit se situer la caméra.
		// en effet :
		//
		// *    E    D     *
		//	*     C       *
		//	 *           *
		//    * A     B *
		//	   *       *
		//	    *     *
		//       *   *
		//        * *
		//		   *
		//
		// si A et B sont contenu dans le champ de vision alors obligatoirement, 
		//les points possédant des coordonnées qui ne sont pas supérieurs seront aussi présent dans le champ de vision
		
		//la base correspond à la longueur entre le barycentre et l'extrémité du champ de vision.
		// cette extrémité se trouve sur l'un des 4 côté que forme les 4 points précédemment calculés.
		float[] base = new float[3] ;
		//calcul de X de la base
		base[0] = xyZ[0] + ((xYZ[0] - xyZ[0]) / 2);
		//calcul de Y de la base
		base[1] = xyZ[1] + ((xYZ[1] - xyZ[1]) / 2);
		//calcul de Z de la base
		base[2] = xyZ[2] + ((xYZ[2] - xyZ[2]) / 2);
		
		//calcul de la distance
		float lengthBetween = this.getLengthBetween(barycenter, base);
		
		float length = (float)(lengthBetween / Math.tan(fieldOfView/ 2));
		
		bestPointToSee[0] = barycenter[0];
		bestPointToSee[1] = barycenter[1];
		bestPointToSee[2] = barycenter[2] + length;
		
		//définition de la vue
		System.out.println(bestPointToSee[0]);
		System.out.println(bestPointToSee[1]);
		System.out.println(bestPointToSee[2]);
		this.view.putOnBestPointToSee(bestPointToSee);
	}
	
	private float getLengthBetween(float[] one, float[] second) {
		return (float) Math.sqrt(Math.pow(one[0] - second[0], 2) + Math.pow(one[1] - second[1], 2) + Math.pow(one[2] - second[2], 2));
	}
	
	/**
	 * The getter of the Canvas3D which containts the scene compiled.
	 * @return a Canvas3D component.
	 */
	public Canvas3D getCanvas() {
		this.sceneCompiled = this.scene;
		
		this.sceneCompiled.compile();
		
		this.locale.addBranchGraph(this.view);
		this.locale.addBranchGraph(this.sceneCompiled);
		
		return this.view.getCanvas();
	}
	
	/**
	 * This function is used to add a GNode to the scene.
	 * @param node of type GNode.
	 */
	public void addGNode(GNode node){
		GNodeView nodeView = new GNodeView(node);
		this.scene.addChild(nodeView);
		this.ComponentsView.put(node.getName(), nodeView);
	}

	/**
	 * This function is used to add a GLink (arrow or bridge) to the scene.
	 * @param link of type GLink.
	 */
	public void addGLink(GLink link){
		GLinkView linkView;
		if (link.isType()) {
			linkView = new GArrowView(link);
		} else {
			linkView = new GBridgeView(link);
		}
		this.scene.addChild(linkView);
		this.ComponentsView.put(link.getName(), linkView);
	}
	
	/**
	 * This function is used to delete a GNode to the scene.
	 * @param node of type GNode.
	 */
	public void deleteGNode(GNode node){
		GNodeView nodeView = new GNodeView(node);
		scene.removeChild(nodeView);
		this.ComponentsView.remove(nodeView);
	}
	
	/**
	 * This function is used to delete a GLink (arrow or bridge) to the scene.
	 * @param link of type GLink.
	 */
	public void deleteGLink(GLink link){
		GLinkView linkView;
		if (link.isType()) {
			linkView = new GArrowView(link);
		} else {
			linkView = new GBridgeView(link);
		}
		scene.removeChild(linkView);
		this.ComponentsView.remove(linkView);
	}
	
	/**
	 * 
	 */
	public void zoom(){
		
	}
	
	/**
	 * 
	 */
	public void zoom(float zoom){
		
	}
	
	/**
	 * 
	 */
	public void rotate(){
		
	}
	
	/**
	 * 
	 */
	public void rotate(float angle){
		
	}
	
	/**
	 * 
	 */
	public boolean collision(){
		boolean flag=false;
		return flag;
	}
	
	/**
	 * This function is used to add the mouse's implements to the scene.
	 * for rotate add MouseRotate.
	 * for translate add MouseTranslate.
	 * for zoom add MouseZoom.
	 */
	public void addMouseListener(){
		
		TransformGroup mouseTransform = new TransformGroup();
		
	    // Le groupe de transformation sera modifie par le comportement de la
	    // souris
	    mouseTransform.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
	    mouseTransform.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
	
	    // Creation comportement rotation a la souris
	    MouseRotate rotate = new MouseRotate(mouseTransform);
	    rotate.setSchedulingBounds(new BoundingSphere());
	    scene.addChild(rotate);
	
	    // Creation comportement deplacement a la souris
	    MouseTranslate translate = new MouseTranslate(mouseTransform);
	    translate.setSchedulingBounds(new BoundingSphere());
	    scene.addChild(translate);
	
	    // Creation comportement zoom a la souris
	    MouseZoom zoom = new MouseZoom(mouseTransform);
	    zoom.setSchedulingBounds(new BoundingSphere());
	    scene.addChild(zoom);
	}
	
	/**
	 * This function is used to add the keyboard's implements to the scene.
	 * add KeyNavigatorBehavior.
	 */
	public void addKeyListener(){
		
		TransformGroup keyTransform = new TransformGroup();
		
	    // Le groupe de transformation sera modifie par le comportement de la
	    // souris
		keyTransform.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		keyTransform.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		
		// On associe l'objet TransformGroup tg au clavier
	    KeyNavigatorBehavior keyNavigatorBehavior = new KeyNavigatorBehavior(keyTransform);

	    // Champ d'action du clavier
	    keyNavigatorBehavior.setSchedulingBounds(new BoundingSphere(new Point3d(), 1000));

	    // Ajout du comportement du clavier a l'objet parent de la scene 3D
	    scene.addChild(keyNavigatorBehavior);
	}
	
	/**
	 * This function is used to add a background color to the scene.
	 * @param color of type Color.
	 */
	public void addBackground(Color color){
	    Background background = new Background(color.getRed(),color.getGreen(),color.getBlue());
	    background.setApplicationBounds(new BoundingBox());
	    scene.addChild(background);
	}
}
